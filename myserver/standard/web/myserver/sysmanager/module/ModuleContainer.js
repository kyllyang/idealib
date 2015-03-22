Ext.define('SysManager.module.ModuleContainer', {
	extend: 'Ext.container.Container',

	layout: 'border',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('SysManager.module.ModuleTreePanel', {
			region: 'west',
			split: true,
			collapsible: true,
			width: (myServer.width - myServer.getMenuTreePanel().getWidth()) / 3
		}));
		this.add(Ext.create('SysManager.module.ModuleGridPanel', {
			region: 'center'
		}));
	}
});
