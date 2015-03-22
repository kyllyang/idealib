Ext.define('Base.HeaderContainer', {
	extend: 'Ext.container.Container',

	layout: {
		type: 'vbox',
		pack: 'start',
		align: 'stretch'
	},

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('Base.BannerContainer'));
		this.add(Ext.create('Base.MenuToolbar'));
	}
});
