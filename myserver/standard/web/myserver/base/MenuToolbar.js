Ext.define('Base.MenuToolbar', {
	extend: 'Ext.toolbar.Toolbar',

	initComponent: function() {
		this.callParent();

		this.add(Ext.create('Ext.toolbar.Fill'));
		this.add(Ext.create('Ext.form.field.Display', {
			fieldLabel: '当前登录用户',
			labelAlign: 'right',
			labelSeparator: '：',
			value: myServer.username
		}));
		this.add(Ext.create('Ext.button.Button', {
			text: '退出系统',
			icon: ctx + '/resource/image/icon/exit.png',
			scope: this,
			handler: function() {
				Ext.MessageBox.confirm("系统提示", "确定要退出系统吗？", function (btn) {
					if ('yes' == btn) {
						Ext.Ajax.request({
							url: ctx + '/logout.ctrl',
							success: function(response, opts) {
								window.location = ctx + '/login.jsp';
							},
							failure: function(response, opts) {
								Ext.Msg.alert("系统提示", "退出系统失败！");
							},
							scope: this
						});
					}
				});
			}
		}));

		Ext.Ajax.request({
			url: ctx + '/sysmanager/module/topMenu.ctrl',
			success: function(response, opts) {
				var datas = Ext.decode(response.responseText);
				for (var i = 0; i < datas.length; i++) {
					this.insert(i, Ext.create('Ext.button.Button', {
						text: datas[i].name,
						icon: ctx + '/resource/image/icon/module.png',
						scope: this,
						moduleId: datas[i].id,
						handler: function(btn) {
							myServer.setMenuTreeContent(btn.moduleId);
						}
					}));
				}
			},
			failure: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据加载失败！");
			},
			scope: this
		});
	}
});
