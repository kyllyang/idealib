<%--
  User: Kyll
  Date: 2014-11-07 14:41
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>我的服务器</title>
	<script type="text/javascript">
		function onloadHandler() {
			document.getElementById("username").focus();
		}
	</script>
</head>
<body onload="onloadHandler();">
<form id="loginForm" action="${ctx}/login.ctrl" method="post">
	<fieldset style="width: 280px; margin-left: auto; margin-right: auto; margin-top: 20%; text-align: center;">
		<legend>登录</legend>
		<ul style="list-style-type: none; text-align: right;">
			<li>用户名称<input type="text" id="username" name="username"></li>
			<li>密码<input type="password" name="password"></li>
			<li><input type="submit" value="登录"/></li>
		</ul>
	</fieldset>
</form>
</body>
</html>
