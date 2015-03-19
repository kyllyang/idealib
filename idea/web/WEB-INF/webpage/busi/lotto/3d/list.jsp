<%@ include file="/common/jspHeader.jsp" %>
<html>
<head>
	<title></title>
	<script type="text/javascript">
		function onClickHandlerForSync() {
			location.href = '<c:url value="/busi/lotto/3d/sync.ctrl"/>?urlStr=http://tubiao.zhcw.com/tubiao/3d/3dInc/3dZongHeZouShiTujsAsckj_year=' + new Date().getFullYear() + ".html";
		}
	</script>
</head>
<body>
<div style="text-align: left;">
	<input type="button" value="<fmt:message key="label_sync"/>" onclick="onClickHandlerForSync();" onmouseout="onMouseOutForButton(this);" onmouseover="onMouseOverForButton(this);" onmousedown="onMouseDownForButton(this);" onmouseup="onMouseUpForButton(this);"/>
</div>
<table class="data">
	<tr>
		<td colspan="6" align="right">
			<kyll:paginated paginated="${requestScope.dataset.paginated}" url="/busi/lotto/3d/list.ctrl"/>
		</td>
	</tr>
	<tr class="header">
		<td><fmt:message key="label_no_"/></td>
		<td><fmt:message key="lotto_3d_date"/></td>
		<td><fmt:message key="lotto_3d_term"/></td>
		<td colspan="3"><fmt:message key="lotto_3d_digital"/></td>
	</tr>
	<c:forEach items="${requestScope.dataset.dataList}" var="digitalTree" varStatus="row">
		<tr class="<c:out value="${row.index % 2 == 0 ? 'even' : 'odd'}"/>" onmouseout="onMouseOutForTable(this);" onmouseover="onMouseOverForTable(this);">
			<td class="no"><c:out value="${requestScope.dataset.paginated.startRecord + row.count}"/></td>
			<td class="datetime"><fmt:formatDate value="${digitalTree.date}" pattern="yyyy-MM-dd"/></td>
			<td class="text"><c:out value="${digitalTree.term}"/></td>
			<td class="number"><c:out value="${digitalTree.digital0}"/></td>
			<td class="number"><c:out value="${digitalTree.digital1}"/></td>
			<td class="number"><c:out value="${digitalTree.digital2}"/></td>
		</tr>
	</c:forEach>
	<tr>
		<td colspan="6" align="right">
			<kyll:paginated paginated="${requestScope.dataset.paginated}" url="/busi/lotto/3d/list.ctrl"/>
		</td>
	</tr>
</table>
</body>
</html>
