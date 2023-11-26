<%--
  Created by IntelliJ IDEA.
  User: 李涛
  Date: 2022/3/18
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <td>详情</td>
        <td>文件名</td>
    </tr>
    <jsp:useBean id="multiFileDomain" scope="request" type="org.springframework.core.io.DescriptiveResource"/>
    <c:forEach items =" ${ multiFileDomain.description}"
               var="description" varStatus="loop">
        <tr>
            <td>${description}</td>
            <td>${ multiFileDomain.myfile[loop.count-1].originalFilename}</td>
        </tr>
    </c:forEach>

</table>
</body>
</html>
