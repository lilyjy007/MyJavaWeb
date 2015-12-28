<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! int count = 0; %>
      <% count++; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>visitors' information</title>
</head>
<body bgcolor="#E3E3E3">
 <h3 align="center">Hello,"${username}",您是第${count}个访问者，在您前面的访问者是：</h3>
 <hr align="center" size="2" width="500">
<table width="50%" border="1" align="center">
<th>用户信息</th><th>访问时间</th>
<c:forEach items="${model2}" var="node">  
<tr><td style="text-align:center"><c:out value="${node.key}"></c:out></td>  
 <td style="text-align:center"><c:out value="${node.value}"></c:out></td></tr> 
</c:forEach>
</table>
</body>
</html>