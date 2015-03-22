Ext.define('SysManager.dict.DictContainer', {
	extend: 'Ext.container.Container',

	layout: 'border',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('SysManager.dict.DictTreePanel', {
			region: 'west',
			split: true,
			collapsible: true,
			width: (myServer.width - myServer.getMenuTreePanel().getWidth()) / 3
		}));
		this.add(Ext.create('SysManager.dict.DictItemGridPanel', {
			region: 'center'
		}));
	}
});
