<%@ include file="/common/jspHeader.jsp" %>
<c:forEach items="${requestScope.promptList}" var="prompt">
	<div class="<c:out value="${prompt.level}"/>">
		<img src="<c:url value="/theme/default/image/prompt/${prompt.level}"/>.png" alt="<fmt:message key="prompt_${prompt.level}"/>"/>
		<fmt:message key="${prompt.content}">
			<c:forTokens items="${prompt.paramValues}" delims="," var="paramValue">
				<fmt:param value="${paramValue}"/>
			</c:forTokens>
		</fmt:message>
	</div>
</c:forEach>
