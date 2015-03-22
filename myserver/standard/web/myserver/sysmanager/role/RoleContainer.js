Ext.define('SysManager.role.RoleContainer', {
	extend: 'Ext.container.Container',

	layout: 'border',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('SysManager.role.RoleGridPanel', {
			region: 'center'
		}));
		this.add(Ext.create('SysManager.role.ModuleTreePanel', {
			region: 'east',
			split: true,
			collapsible: true,
			width: (myServer.width - myServer.getMenuTreePanel().getWidth()) / 3
		}));
	}
});
