Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();

	myServer.init();

	myServer.viewport = Ext.create('Ext.container.Viewport', {
		renderTo: Ext.getBody(),
		layout: 'border',
		items: [
			Ext.create('Base.HeaderContainer', {region: 'north'}),
			Ext.create('Base.MenuTreePanel', {region: 'west', split: true, collapsible: true, width: myServer.width * 0.2}),
			Ext.create('Base.MainContainer', {region: 'center'}),
			Ext.create('Base.CopyRightContainer', {region: 'south'})
		]
	});
});
