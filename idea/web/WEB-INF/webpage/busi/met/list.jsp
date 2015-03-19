<%@ include file="/common/jspHeader.jsp" %>
<html>
<head>
	<title></title>
	<script type="text/javascript">
		function onClickHandlerForSync(urlStr) {
			location.href = '<c:url value="/busi/metdl/sync.ctrl"/>?urlStr=' + urlStr;
		}

		function onClickHandlerForSearch() {
			document.getElementById("searchForm").submit();
		}

		function onClickHandlerForClear() {
			var form = document.getElementById("searchForm");
			form.elements["met.metCategory.id"].value = "";
			form.elements["met.name"].value = "";
			form.elements["met.url"].value = "";
			form.elements["met.status"].value = "";
		}

		function appendUrlParam(a) {
			var pv = "";
			var form = document.getElementById("searchForm");
			pv += "met.metCategory.id=" + form.elements["met.metCategory.id"].value;
			pv += "&met.name=" + form.elements["met.name"].value;
			pv += "&met.url=" + form.elements["met.url"].value;
			var array = getElementArrayByName("met.searchStatus");
			for (var i = 0; i < array.length; i++) {
				if (array[i].checked) {
					pv += "&met.searchStatus[" + i + "]=" + array[i].value;
				}
			}
			document.getElementById('paginatedParams').value = pv;
		}

		function appendSearchUrlParam(a) {
			var pv = "";
			var form = document.getElementById("searchForm");
			pv += "&searchMet.metCategory.id=" + form.elements["met.metCategory.id"].value;
			pv += "&searchMet.name=" + form.elements["met.name"].value;
			pv += "&searchMet.url=" + form.elements["met.url"].value;
			var array = getElementArrayByName("met.searchStatus");
			for (var i = 0; i < array.length; i++) {
				if (array[i].checked) {
					pv += "&searchMet.searchStatus[" + i + "]=" + array[i].value;
				}
			}
			a.href += pv
		}

		function showImg(event, url, flag) {
			var e = event;
			setTimeout(function () {
				var position = getMouseCoordinate(e);

				var div = document.getElementById("imgDiv");
				div.style.left = position.x - contentX;
				div.style.top = position.y - contentY;
				div.style.display = flag ? "block" : "none";

				var img = document.getElementById("viewImg");

				img.src = url;
			}, flag ? 1000 : 0);
		}
	</script>
</head>
<body>
<div style="text-align: left;">
	<input type="button" value="<fmt:message key="label_sync"/> pic-video" onclick="onClickHandlerForSync('picvideo');" onmouseout="onMouseOutForButton(this);" onmouseover="onMouseOverForButton(this);" onmousedown="onMouseDownForButton(this);" onmouseup="onMouseUpForButton(this);"/>
	<input type="button" value="<fmt:message key="label_sync"/> Blog - xpic" onclick="onClickHandlerForSync('blogxpic');" onmouseout="onMouseOutForButton(this);" onmouseover="onMouseOverForButton(this);" onmousedown="onMouseDownForButton(this);" onmouseup="onMouseUpForButton(this);"/>
	<input type="button" value="<fmt:message key="label_sync"/> yaoblue" onclick="onClickHandlerForSync('yaoblue');" onmouseout="onMouseOutForButton(this);" onmouseover="onMouseOverForButton(this);" onmousedown="onMouseDownForButton(this);" onmouseup="onMouseUpForButton(this);"/>
</div>
<table class="data">
	<tr>
		<td colspan="11" align="right">
			<kyll:paginated paginated="${requestScope.dataset.paginated}" url="/busi/metdl/list.ctrl" onBeforeHref="appendUrlParam();"/>
		</td>
	</tr>
	<tr class="header">
		<td><fmt:message key="label_no_"/></td>
		<td><fmt:message key="met_previe"/></td>
		<td><fmt:message key="met_updatetime"/></td>
		<td><fmt:message key="met_uploadtime"/></td>
		<td><fmt:message key="met_category"/></td>
		<td><fmt:message key="met_name"/></td>
		<td><fmt:message key="met_url"/></td>
		<td><fmt:message key="met_status"/></td>
		<td colspan="3"><fmt:message key="label_operation"/></td>
	</tr>
	<form id="searchForm" action="<c:url value="/busi/metdl/search.ctrl"/>" method="post">
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>
				<select name="met.metCategory.id" style="width: 100%;">
					<option value=""><fmt:message key="label_all"/></option>
					<c:forEach items="${requestScope.metCategoryList}" var="metCategory">
						<option value="<c:out value="${metCategory.id}"/>" <c:if test="${requestScope.met.metCategory.id == metCategory.id}">selected="selected"</c:if>><c:out value="${metCategory.name}"/></option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="text" name="met.name" value="<c:out value="${requestScope.met.name}"/>" style="width: 100%;"/>
			</td>
			<td>
				<input type="text" name="met.url" value="<c:out value="${requestScope.met.url}"/>" style="width: 100%;"/>
			</td>
			<td style="word-break:keep-all; white-space:nowrap;">
				<input type="checkbox" id="met.searchStatus[0]" name="met.searchStatus[0]" value="0" <c:if test="${requestScope.met.searchStatus[0] == 0}">checked="checked"</c:if>/><fmt:message key="met_status_0"/><br/>
				<input type="checkbox" id="met.searchStatus[1]" name="met.searchStatus[1]" value="1" <c:if test="${requestScope.met.searchStatus[1] == 1}">checked="checked"</c:if>/><fmt:message key="met_status_1"/><br/>
				<input type="checkbox" id="met.searchStatus[2]" name="met.searchStatus[2]" value="2" <c:if test="${requestScope.met.searchStatus[2] == 2}">checked="checked"</c:if>/><fmt:message key="met_status_2"/><br/>
				<input type="checkbox" id="met.searchStatus[3]" name="met.searchStatus[3]" value="3" <c:if test="${requestScope.met.searchStatus[3] == 3}">checked="checked"</c:if>/><fmt:message key="met_status_3"/><br/>
				<input type="checkbox" id="met.searchStatus[4]" name="met.searchStatus[4]" value="4" <c:if test="${requestScope.met.searchStatus[4] == 4}">checked="checked"</c:if>/><fmt:message key="met_status_4"/>
			</td>
			<td class="action">
				<a href="javascript: onClickHandlerForSearch();"><img src="<c:url value="/theme/default/image/action/search.png"/>" alt="<fmt:message key="label_search"/>"/></a>
			</td>
			<td class="action">
				<a href="javascript: onClickHandlerForClear();"><img src="<c:url value="/theme/default/image/action/clear.png"/>" alt="<fmt:message key="label_clear"/>"/></a>
			</td>
			<td>&nbsp;</td>
		</tr>
	</form>
	<c:forEach items="${requestScope.dataset.dataList}" var="met" varStatus="outrow">
		<c:forEach items="${met.metUrlList}" var="address" varStatus="inrow">
			<c:choose>
				<c:when test="${inrow.index > 0}">
					<tr class="<c:out value="${outrow.index % 2 == 0 ? 'even' : 'odd'}"/>" onmouseout="onMouseOutForTable(this);" onmouseover="onMouseOverForTable(this);">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="text">
							<a href="<c:out value="${address.url}"/>"><c:out value="${address.url}"/></a>
						</td>
						<td>&nbsp;</td>
						<td class="action">
							<a href="<c:url value="/busi/metdl/url/view.ctrl?metUrl.url=${address.url}"/>"><img src="<c:url value="/theme/default/image/action/download.png"/>" alt="<fmt:message key="label_downlaod"/>"/></a>
						</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="<c:out value="${outrow.index % 2 == 0 ? 'even' : 'odd'}"/>" onmouseout="onMouseOutForTable(this);" onmouseover="onMouseOverForTable(this);">
						<td class="no"><c:out value="${requestScope.dataset.paginated.startRecord + outrow.count}"/></td>
						<td class="image" onmouseover="showImg(event, '<c:url value="/busi/metdl/image.ctrl?id=${met.metImage.id}"/>', true);">
							<img src="<c:url value="/busi/metdl/image.ctrl?id=${met.metImage.id}"/>" alt="preview" width="50px" height="50px"/>
						</td>
						<td class="datetime"><fmt:formatDate value="${met.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="datetime"><fmt:formatDate value="${met.uploadTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="text"><c:out value="${met.metCategory.name}"/></td>
						<td class="text"><c:out value="${met.name}"/></td>
						<td class="text">
							<a href="<c:out value="${address.url}"/>"><c:out value="${address.url}"/></a>
						</td>
						<td class="text"><fmt:message key="met_status_${met.status}"/></td>
						<td class="action">
							<a href="<c:url value="/busi/metdl/url/view.ctrl?metUrl.url=${address.url}"/>"><img src="<c:url value="/theme/default/image/action/download.png"/>" alt="<fmt:message key="label_downlaod"/>"/></a>
						</td>
						<td class="action">
							<a href="<c:url value="/busi/metdl/modify.ctrl?met.id=${met.id}&met.status=2&paginated.startRecord=${requestScope.dataset.paginated.startRecord}&paginated.maxRecord=${requestScope.dataset.paginated.maxRecord}&paginated.duePage=${requestScope.dataset.paginated.duePage}"/>" onclick="appendSearchUrlParam(this);"><img src="<c:url value="/theme/default/image/action/done.png"/>" alt="<fmt:message key="label_done"/>"/></a>
						</td>
						<td class="action">
							<a href="<c:url value="/busi/metdl/modify.ctrl?met.id=${met.id}&met.status=3&paginated.startRecord=${requestScope.dataset.paginated.startRecord}&paginated.maxRecord=${requestScope.dataset.paginated.maxRecord}&paginated.duePage=${requestScope.dataset.paginated.duePage}"/>" onclick="appendSearchUrlParam(this);"><img src="<c:url value="/theme/default/image/action/delete.png"/>" alt="<fmt:message key="label_delete"/>"/></a>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:forEach>
	<tr>
		<td colspan="11" align="right">
			<kyll:paginated paginated="${requestScope.dataset.paginated}" url="/busi/metdl/list.ctrl" onBeforeHref="appendUrlParam();"/>
		</td>
	</tr>
</table>
<div id="imgDiv" style="position: absolute; z-index: 1; display: none;" onclick="showImg('<c:url value="/theme/default/image/empty.png"/>', false);">
	<img id="viewImg" src="<c:url value="/theme/default/image/empty.png"/>" alt=""/>
</div>
</body>
</html>
