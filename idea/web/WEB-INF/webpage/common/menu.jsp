<%@ include file="/common/jspHeader.jsp" %>
<div class="menubar">
	<div id="menu1" class="menusel">
		<h2><a href="<c:url value="/"/>"><fmt:message key="menu_home"/></a></h2>
	</div>
	<div id="menu2" class="menusel">
		<h2><a href="javascript: return false;"><fmt:message key="menu_met"/></a></h2>
		<div class="position">
			<ul class="clearfix typeul">
				<li><a href="<c:url value="/busi/metdl/category/list.ctrl"/>"><fmt:message key="menu_met_category"/></a>
					<ul>
						<li class="fli"><a href="<c:url value="/busi/metdl/category/merge/list.ctrl"/>"><fmt:message key="menu_met_category_merge"/></a></li>
					</ul>
				</li>
				<li><a href="<c:url value="/busi/metdl/list.ctrl"/>"><fmt:message key="menu_met_resource"/></a></li>
			</ul>
		</div>
	</div>
	<div id="menu3" class="menusel">
		<h2><a href="javascript: return false;"><fmt:message key="menu_lotto"/></a></h2>
		<div class="position">
			<ul class="clearfix typeul">
				<li><a href="javascript: return false;"><fmt:message key="menu_lotto_dc"/></a>
					<ul>
						<li class="fli"><a href="<c:url value="/busi/lotto/dc/list.ctrl"/>"><fmt:message key="menu_lotto_dc_record"/></a></li>
						<li class="lli"><a href="<c:url value="/busi/lotto/dc/chart.ctrl"/>"><fmt:message key="menu_lotto_dc_chart"/></a></li>
						<li class="lli"><a href="<c:url value="/busi/lotto/dc/select.ctrl"/>"><fmt:message key="menu_lotto_dc_random"/></a></li>
					</ul>
				</li>
				<li><a href="javascript: return false;"><fmt:message key="menu_lotto_3d"/></a>
					<ul>
						<li class="fli"><a href="<c:url value="/busi/lotto/3d/list.ctrl"/>"><fmt:message key="menu_lotto_3d_record"/></a></li>
						<li class="lli"><a href="<c:url value="/busi/lotto/3d/chart.ctrl"/>"><fmt:message key="menu_lotto_3d_chart"/></a></li>
						<li class="lli"><a href="<c:url value="/busi/lotto/3d/select.ctrl"/>"><fmt:message key="menu_lotto_3d_random"/></a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	for (var x = 1; x <= 3; x++) {
		var menuid = document.getElementById("menu" + x);
		menuid.num = x;
		type();
	}
	function type() {
		var menuh2 = menuid.getElementsByTagName("h2");
		var menuul = menuid.getElementsByTagName("ul");
		if (!menuul[0]) {
			return;
		}
		var menuli = menuul[0].getElementsByTagName("li");
		menuh2[0].onmouseover = show;
		menuh2[0].onmouseout = unshow;
		menuul[0].onmouseover = show;
		menuul[0].onmouseout = unshow;
		function show() {
			menuul[0].className = "clearfix typeul block"
		}

		function unshow() {
			menuul[0].className = "typeul"
		}

		for (var i = 0; i < menuli.length; i++) {
			menuli[i].num = i;
			var liul = menuli[i].getElementsByTagName("ul")[0];
			if (liul) {
				typeshow()
			}
		}
		function typeshow() {
			menuli[i].onmouseover = showul;
			menuli[i].onmouseout = unshowul;
		}

		function showul() {
			menuli[this.num].getElementsByTagName("ul")[0].className = "block";
		}

		function unshowul() {
			menuli[this.num].getElementsByTagName("ul")[0].className = "";
		}
	}
</script>
