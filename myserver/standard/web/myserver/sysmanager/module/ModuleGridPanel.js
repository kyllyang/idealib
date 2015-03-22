Ext.define('SysManager.module.ModuleGridPanel', {
	extend: 'Base.ux.GridPanel',

	itemId: 'moduleGridPanel',

	initComponent: function() {
		Ext.apply(this, {
			url: ctx + '/sysmanager/module/list.ctrl',
			sortProperty: 'sort',
			sortDirection: 'asc',
			idProperty: 'id',
			columns: [{
				dataIndex: 'id',
				hidden: true
			}, {
				text: '模块名称',
				dataIndex: 'name'
			}, {
				text: '类型',
				dataIndex: 'typeText'
			}, {
				text: '功能类型',
				dataIndex: 'funcTypeText'
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
					xtype: 'hidden',
					name: 'parentId'
				}, {
					xtype: 'textfield',
					fieldLabel: '模块名称',
					name: 'name'
				}, Ext.create('Base.ux.DictComboBox', {
					invokeCode: 'sysmanager_module_type',
					initAutoLoad: false,
					fieldLabel: '类型',
					labelAlign: 'right',
					labelSeparator: '：',
					name: 'type'
				}), Ext.create('Base.ux.DictComboBox', {
					invokeCode: 'sysmanager_module_funcType',
					initAutoLoad: false,
					fieldLabel: '功能类型',
					labelAlign: 'right',
					labelSeparator: '：',
					name: 'funcType'
				}), {
					xtype: 'button',
					text: '查询',
					width: 80,
					margin: '0 0 0 10',
					handler: function() {
						this.queryData(this.getComponent('queryForm').getForm().findField('parentId').getValue());
					},
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
						enter: function() {
							this.queryData(this.getComponent('queryForm').getForm().findField('parentId').getValue());
						},
						scope: this
					});
				},
				scope: this
			}
		});
		this.callParent();

		this.on('itemdblclick', function(grid, record, item, index, e, eOpts){
			this.viewEventHandler();
		});

		this.store.on('beforeload', function(store, operation, eOpts) {
			var form = this.getComponent('queryForm').getForm();

			this.store.proxy.actionMethods = {read: 'POST'};
			Ext.apply(this.store.proxy.extraParams, {
				'qc.parentId': form.findField('parentId').getValue(),
				'qc.name': form.findField('name').getValue(),
				'qc.type': form.findField('type').getValue(),
				'qc.funcType': form.findField('funcType').getValue()
			});
		}, this);
	},
	doAddEvent: function() {
		var record = myServer.getMainContent().getComponent('moduleTreePanel').getSelectedRecord();
		if (record) {
			if ('2' == record.get('type')) {
				Ext.Msg.alert("系统提示", "功能下不能添加模块！");
			} else {
				Ext.create('SysManager.module.ModuleFormWindow', {parentEntity: record}).show();
			}
		} else {
			Ext.Msg.alert("系统提示", "请首先选择一个上级模块！");
		}
	},
	doEditEvent: function(id) {
		var record = myServer.getMainContent().getComponent('moduleTreePanel').getSelectedRecord();
		Ext.create('SysManager.module.ModuleFormWindow', {entityId: id, parentEntity: record}).show();
	},
	doViewEvent: function(id) {
		var record = myServer.getMainContent().getComponent('moduleTreePanel').getSelectedRecord();
		Ext.create('SysManager.module.ModuleFormWindow', {entityId: id, parentEntity: record, readOnlyForm: true}).show();
	},
	doDeleteEvent: function(ids) {
		var record = myServer.getMainContent().getComponent('moduleTreePanel').getSelectedRecord();
		Ext.Ajax.request({
			url: ctx + '/sysmanager/module/delete.ctrl',
			params: {
				ids: ids
			},
			success: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据删除成功！");
				this.queryData(record.get('id'));
				myServer.getMainContent().getComponent('moduleTreePanel').loadTreeNode(record.get('id'));
			},
			failure: function(response, opts) {
				Ext.Msg.alert("系统提示", "数据删除失败！");
			},
			scope: this
		});
	},
	queryData: function(parentId) {
		var form = this.getComponent('queryForm').getForm();
		form.findField('parentId').setValue(parentId);

		this.getStore().load();
	},
	clear: function() {
		var form = this.getComponent('queryForm').getForm();
		form.findField('name').setValue('');
		form.findField('type').setValue('');
		form.findField('funcType').setValue('');
		this.queryData(form.findField('parentId').getValue());
	}
});
