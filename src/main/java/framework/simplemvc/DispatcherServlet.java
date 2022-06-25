package framework.simplemvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private String scanPackageTxt;// "com,net"
    private String viewPathTxt; //   "/page"
    private String suffixTxt;  //     "jsp"
    private Map<String, Object[]> map = new HashMap<>();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        Object[] classAndMethod = map.get(requestURI);
        if(classAndMethod!=null){
            Class clazz= (Class) classAndMethod[0];
            Method method = (Method) classAndMethod[1];
            try {
                //new  UserAction();
               Object obj =  clazz.newInstance();
               Object viewTxt = method.invoke(obj, request, response);
               if(viewTxt!=null){
                   String view = ((String) viewTxt).trim();
                   if(view.startsWith("s:")){
                       //s:index  --->  index
                       view = view.substring(view.indexOf(":") + 1);
                       sendRedirect(view,response);
                   }else{
//                       默认情况下是转发
                       forward(view,request,response);
                   }
               }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            throw new SimpleMVCException("找不到对应的路径:"+requestURI);
        }
    }

    @Override
    public void init() throws ServletException {
        //读取配置参数 scan-package , view-path ,suffix
        this.readInitparam();
        this.scanPackage(scanPackageTxt);
    }

    private void scanPackage(String packages) {
        try {
            String[] packs = packages.split(",");
            for (int i = 0; i < packs.length; i++) {
                String eachPack = packs[i].trim();// "com.my"  ----> "com/my"
                String eachPackPath = "/" + eachPack.replace('.', '/') + "/";
                //记住这行:获取类路径的真实路径
                String classDirPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                File file = new File(classDirPath + eachPackPath);//
                if (file.exists()) {
                    String[] classSimpleNames = file.list();
                    for (String simpleName : classSimpleNames) {
                        // UserAction.class
                        String className = eachPack + "." + simpleName.substring(0, simpleName.lastIndexOf("."));
                        Class cla = Class.forName(className);
                        String nameSpace = "";
                        Annotation annotation4Class = cla.getAnnotation(RequestPath.class);
                        if (annotation4Class != null) {
                            RequestPath rp = (RequestPath) annotation4Class;
                            nameSpace = rp.value();
                        }

                        Method[] methods = cla.getMethods();
                        for (Method method : methods) {
                            RequestPath annotation4Method = method.getAnnotation(RequestPath.class);
                            if (annotation4Method != null) {
                                String path = annotation4Method.value();
                                String uri = getServletContext().getContextPath()+nameSpace+path;
                                if(map.get(uri)!=null){
                                    throw new  SimpleMVCException("路径重复:"+uri);
                                }else{
                                    map.put(uri,new Object[]{cla,method});
                                }
                            }
                        }

                    }
                } else {
                    throw new SimpleMVCException("包不存在:" + eachPack);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获得视图字符串之后，根据配置参数 跳转 到对应的页面！
     */
    private void forward(String view, HttpServletRequest request, HttpServletResponse response) {
        try {
            String path = viewPathTxt + "/" + view + "." + suffixTxt;
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            throw new SimpleMVCException(e);
        }
    }

    /**
     * 重定向:
     * @param view
     * @param response
     */
    private void sendRedirect(String view,  HttpServletResponse response) {
        try {
            String path = viewPathTxt + "/" + view + "." + suffixTxt;
            response.sendRedirect(this.getServletContext().getContextPath()+path);
        } catch (Exception e) {
            throw new SimpleMVCException(e);
        }
    }


    /**
     * 读取 前端控制器的配置参数: scan-package , view-path ,suffix
     */

    private void readInitparam() {
        scanPackageTxt = this.getServletConfig().getInitParameter("scan-package");
        if (scanPackageTxt == null) {
            throw new SimpleMVCException("DispatcherServlet 配置缺少参数:" + "scan-package");
        }
        viewPathTxt = this.getServletConfig().getInitParameter("view-path");
        if (viewPathTxt == null) {
            throw new SimpleMVCException("DispatcherServlet 配置缺少参数:" + "view-path");
        }
        suffixTxt = this.getServletConfig().getInitParameter("suffix");
        if (suffixTxt == null) {
            throw new SimpleMVCException("DispatcherServlet 配置缺少参数:" + "suffix");
        }
        scanPackageTxt = scanPackageTxt.trim();
        viewPathTxt = viewPathTxt.trim();
        suffixTxt = suffixTxt.trim();
    }
}
