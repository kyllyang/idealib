<%@ include file="/common/jspHeader.jsp" %>
<html>
<head>
	<title><fmt:message key="webapp_title"/></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/theme/default/css/main.css"/>"/>
	<script type="text/javascript" language="JavaScript" src="<c:url value="/theme/default/js/util.js"/>"></script>
	<decorator:head/>
</head>
<body <decorator:getProperty property="body.class" writeEntireProperty="true"/> <decorator:getProperty property="body.style" writeEntireProperty="true"/> <decorator:getProperty property="body.onload" writeEntireProperty="true"/>>
	<div class="banner">
		<img src="<c:url value="/theme/default/image/banner.png"/>" alt="banner"/>
	</div>
	<div class="menu">
		<c:import url="/common/menu.ctrl" charEncoding="UTF-8"/>
	</div>
	<div class="content">
		<decorator:body/>
	</div>
	<div class="copyright">
		<fmt:message key="webapp_copyright"/>
	</div>
</body>
</html>
