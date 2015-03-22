Ext.define('SysManager.user.UserGridPanel', {
	extend: 'Base.ux.GridPanel',

	itemId: 'userGridPanel',

	initComponent: function() {
		Ext.apply(this, {
			url: ctx + '/sysmanager/user/list.ctrl',
			sortProperty: 'sort',
			sortDirection: 'asc',
			idProperty: 'id',
			columns: [{
				dataIndex: 'id',
				hidden: true
			}, {
				text: '用户名称',
				dataIndex: 'username'
			}, {
				text: '密码',
				dataIndex: 'password'
			}, {
				text: '电子邮箱',
				dataIndex: 'email'
			}, {
				text: '冻结',
				dataIndex: 'freeze',
				renderer : function(value, metaData, record, rowIndex, colIndex, store, view) {
					if ('1' == value) {
						return "<img src=\"" + ctx + "/resource/image/icon/freeze.png\" border=\"0\" title=\"冻结\"/>"
					}
				}
			}],
			dockedItems: [Ext.create('Ext.form.Panel', {
				itemId: 'queryForm',
				dock: 'top',
				autoHeight: true,
				bodyPadding: 5,
				layout: 'hbox',
				defaults: {
					labelAlign: 'right',
					labelWidth: 80,
					labelSeparator: '：',
					width: 250
				},
				items: [{
					xtype: 'textfield',
					fieldLabel: '用户名称',
					name: 'username'
				}, {
					xtype: 'textfield',
					fieldLabel: '电子邮箱',
					name: 'email'
				}, {
					xtype: 'button',
					text: '查询',
					width: 80,
					margin: '0 0 0 10',
					handler: this.queryData,
					scope: this
				}, {
					xtype: 'button',
					text: '清空',
					width: 80,
					margin: '0 0 0 10',
					handler: this.clear,
					scope: this
				}]
			})],
			listeners: {
				afterRender: function(form, eOpts){
					this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {
						enter: this.queryData,
						scope: this
					});
				},
				scope: this
			}
		});
		this.callParent();

		this.on('itemclick', function(grid, record, item, index, e, eOpts){
			myServer.getMainContent().getComponent('roleTreePanel').loadTree(record.get('id'));
		});

		this.on('itemdblclick', function(grid, record, item, index, e, eOpts){
			this.viewEventHandler();
		});

		this.store.on('beforeload', function(store, operation, eOpts) {
			var form = this.getComponent('queryForm').getForm();

			this.store.proxy.actionMethods = {read: 'POST'};
			Ext.apply(this.store.proxy.extraParams, {
				'qc.username': form.findField('username').getValue(),
				'qc.email': form.findField('email').getValue()
			});
		}, this);

		this.queryData();
	},
	doAddEvent: function() {
		Ext.create('SysManager.user.UserFormWindow').show();
	},
	doEditEvent: function(id) {
		Ext.create('SysManager.user.UserFormWindow', {entityId: id}).show();
	},
	doViewEvent: function(id) {
		Ext.create('SysManager.user.UserFormWindow', {entityId: id, readOnlyForm: true}).show();
	},
	doDeleteEvent: function(ids) {
		Ext.Ajax.request({
			url: ctx + '/sysmanager/user/delete.ctrl',
			params: {
				ids: ids
			},
			success: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据删除成功！");
				this.queryData();
			},
			failure: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据删除失败！");
			},
			scope: this
		});
	},
	getSelectedRecord: function() {
		return this.getSelectionModel().getLastSelected();
	},
	queryData: function() {
		this.getStore().load();
	},
	clear: function() {
		var form = this.getComponent('queryForm').getForm();
		form.findField('username').setValue('');
		form.findField('email').setValue('');
		this.queryData();
	}
});
