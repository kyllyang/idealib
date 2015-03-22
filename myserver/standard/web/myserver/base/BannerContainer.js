Ext.define('Base.BannerContainer', {
	extend: 'Ext.container.Container',

	html: '<img src="' + ctx + '/resource/image/banner.png"/>',

	initComponent: function() {
		this.callParent();
	}
});
