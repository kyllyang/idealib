var myServer = {
	userId: null,
	username: null,
	width: 0,
	height: 0,
	viewport: null,
	menuTreePanel: null,
	mainContainer: null,
	mainContent: null,

	init: function() {
		this.width = document.body.clientWidth;
		this.height = document.body.clientHeight;
	},
	getMenuTreePanel: function() {
		return this.menuTreePanel ? this.menuTreePanel : this.viewport.getComponent("menuTreePanel");
	},
	getMainContainer: function() {
		return this.mainContainer ? this.mainContainer : this.viewport.getComponent("mainContainer");
	},
	setMenuTreeContent: function(moduleId) {
		this.getMenuTreePanel().loadTree(moduleId);
	},
	setMainContent: function(component) {
		var mc = this.getMainContainer();
		if (mc) {
			mc.removeAll();
			mc.add(component);
			this.mainContent = component;
		}
	},
	getMainContent: function() {
		return this.mainContent;
	},
	allProps: function(obj) {
		var props = "";
		for (var p in obj) {
			if (typeof(obj[p]) == "function") {
				props += p + "()" + ";\n";
			} else {
				props += p + "=" + obj[p] + ";\n";
			}
		}
		alert(props);
	}
};
