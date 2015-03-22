<%--
  User: Kyll
  Date: 2014-10-23 8:48
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>我的服务器</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/resource/component/ext-4.2.1.883/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/resource/css/data-view.css"/>
	<script type="text/javascript" src="${ctx}/resource/component/ext-4.2.1.883/ext-all.js"></script>
	<script type="text/javascript" src="${ctx}/resource/component/ext-4.2.1.883/locale/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resource/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/myserver/index.js"></script>
	<script type="text/javascript">
		var ctx = '${ctx}';
		myServer.userId = '${sessionScope.sessionVo.userId}';
		myServer.username = '${sessionScope.sessionVo.username}';

		Ext.Loader.setConfig({
			enabled: true,
			paths: {
				Base: ctx + '/myserver/base',
				SysManager: ctx + '/myserver/sysmanager',
				Meaord: ctx + '/myserver/meaord'
			}
		});

		Ext.require(['*']);

		Ext.EventManager.on(Ext.isIE ? document : window, 'keydown', function(e, t) {
			if (e.getKey() == e.BACKSPACE &&(t.disabled || t.readOnly)) {
				e.stopEvent();
			}
		});
	</script>
</head>
<body>

</body>
</html>
