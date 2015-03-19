<%@ include file="/common/jspHeader.jsp" %>
<html>
<head>
	<title></title>
</head>
<body>
<table class="data">
	<tr>
		<td colspan="3" align="right">
			<kyll:paginated paginated="${requestScope.dataset.paginated}" url="/busi/metdl/category/list.ctrl"/>
		</td>
	</tr>
	<tr class="header">
		<td><fmt:message key="label_no_"/></td>
		<td><fmt:message key="met_category_name"/></td>
		<td><fmt:message key="label_operation"/></td>
	</tr>
	<c:forEach items="${requestScope.dataset.dataList}" var="category" varStatus="row">
		<tr class="<c:out value="${row.index % 2 == 0 ? 'even' : 'odd'}"/>" onmouseout="onMouseOutForTable(this);" onmouseover="onMouseOverForTable(this);">
			<td class="no"><c:out value="${requestScope.dataset.paginated.startRecord + row.count}"/></td>
			<td class="text"><c:out value="${category.name}"/></td>
			<td class="action">
				<a href="<c:url value="/busi/metdl/category/sortToUp.ctrl?metCategory.id=${category.id}&paginated.startRecord=${requestScope.dataset.paginated.startRecord}&paginated.maxRecord=${requestScope.dataset.paginated.maxRecord}&paginated.duePage=${requestScope.dataset.paginated.duePage}"/>"><img src="<c:url value="/theme/default/image/action/up.png"/>" alt="<fmt:message key="label_up"/>"/></a>
				<a href="<c:url value="/busi/metdl/category/sortByDown.ctrl?metCategory.id=${category.id}&paginated.startRecord=${requestScope.dataset.paginated.startRecord}&paginated.maxRecord=${requestScope.dataset.paginated.maxRecord}&paginated.duePage=${requestScope.dataset.paginated.duePage}"/>"><img src="<c:url value="/theme/default/image/action/down.png"/>" alt="<fmt:message key="label_down"/>"/></a>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="3" align="right">
			<kyll:paginated paginated="${requestScope.dataset.paginated}" url="/busi/metdl/category/list.ctrl"/>
		</td>
	</tr>
</table>
</body>
</html>
