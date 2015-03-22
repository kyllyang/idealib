Ext.define('SysManager.org.OrgContainer', {
	extend: 'Ext.container.Container',

	layout: 'border',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('SysManager.org.OrgTreePanel', {
			region: 'west',
			split: true,
			collapsible: true,
			width: (myServer.width - myServer.getMenuTreePanel().getWidth()) / 3
		}));
		this.add(Ext.create('SysManager.org.OrgTabPanel', {
			region: 'center'
		}));
	}
});
