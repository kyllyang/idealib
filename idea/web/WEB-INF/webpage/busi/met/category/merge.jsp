<%@ include file="/common/jspHeader.jsp" %>
<html>
<head>
	<title></title>
</head>
<body>
<form action="<c:url value="/busi/metdl/category/merge/save.ctrl"/>" method="post">
	<table class="data" style="width: 50%; display: inline-table;">
		<tr class="header">
			<td></td>
			<td><fmt:message key="label_no_"/></td>
			<td><fmt:message key="met_category_name"/></td>
		</tr>
		<c:forEach items="${requestScope.categoryList}" var="category" varStatus="row">
			<tr class="<c:out value="${row.index % 2 == 0 ? 'even' : 'odd'}"/>" onmouseout="onMouseOutForTable(this);" onmouseover="onMouseOverForTable(this);">
				<td class="widget"><input type="checkbox" name="categorys" value="<c:out value="${category.id}"/>"/></td>
				<td class="no"><c:out value="${requestScope.dataset.paginated.startRecord + row.count}"/></td>
				<td class="text"><c:out value="${category.name}"/></td>
			</tr>
		</c:forEach>
	</table>
	<table class="form" style="width: 49%; display: inline-table;">
		<tr class="item">
			<td class="label"><fmt:message key="met_category_merge_name"/></td>
			<td class="input"><input type="text" name="name" style="width: 100%;"/></td>
		</tr>
		<tr class="item">
			<td class="submit" colspan="2">
				<input type="submit" value="<fmt:message key="label_save"/>" onmouseout="onMouseOutForButton(this);" onmouseover="onMouseOverForButton(this);" onmousedown="onMouseDownForButton(this);" onmouseup="onMouseUpForButton(this);"/>
				<input type="reset" value="<fmt:message key="label_reset"/>" onmouseout="onMouseOutForButton(this);" onmouseover="onMouseOverForButton(this);" onmousedown="onMouseDownForButton(this);" onmouseup="onMouseUpForButton(this);"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
