Ext.define('SysManager.user.UserContainer', {
	extend: 'Ext.container.Container',

	layout: 'border',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('SysManager.user.UserGridPanel', {
			region: 'center'
		}));
		this.add(Ext.create('SysManager.user.RoleTreePanel', {
			region: 'east',
			split: true,
			collapsible: true,
			width: (myServer.width - myServer.getMenuTreePanel().getWidth()) / 3
		}));
	}
});
