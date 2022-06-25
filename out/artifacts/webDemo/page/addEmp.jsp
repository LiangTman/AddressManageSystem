<%--
  Created by IntelliJ IDEA.
  User: ylighgh
  Date: 2022/6/24
  Time: 下午3:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加一位员工</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/emp/add.do">
    名字: <input type="text" name="name" value="员工111"> <br><br>
    薪水: <input type="text" name="sal" value="999.99"> <br><br>
    入职时间: <input type="text" name="hireDate" value="2019-1-1"> <br><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
