<%--
  Created by IntelliJ IDEA.
  User: 李涛
  Date: 2022/3/18
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath} /multifile" method="post" enctype="multipart/form-data">
    选择文件1：<input type="file" name="myfile"><br>
    描述文件1：<input type="text" name="description"><br>
    选择文件2：<input type="file" name="myfile"><br>
    描述文件2：<input type="text" name="description"><br>
    选择文件3：<input type="file" name="myfile"><br>
    描述文件3：<input type="text" name="description"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
