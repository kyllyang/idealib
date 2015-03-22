var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
	clicksToEdit: 1
});

Ext.define('SysManager.role.RoleGridPanel', {
	extend: 'Base.ux.GridPanel',

	itemId: 'roleGridPanel',

	initComponent: function() {
		Ext.apply(this, {
			url: ctx + '/sysmanager/role/list.ctrl',
			sortProperty: 'sort',
			sortDirection: 'asc',
			enableEditBtn: false,
			enableViewBtn: false,
			enableColumnEditBtn: false,
			enableColumnViewBtn: false,
			enableColumnDelBtn: false,
			idProperty: 'id',
			columns: [{
				dataIndex: 'id',
				hidden: true
			}, {
				text: '角色名称',
				dataIndex: 'name',
				field: {
					maxLength: 50,
					allowBlank: false
				},
				flex: 1
			}, {
				text: '描述',
				dataIndex: 'description',
				field: {
					maxLength: 100
				},
				flex: 1
			}, {
				text: '排序',
				dataIndex: 'sort',
				field: {
					xtype: 'numberfield',
					minValue: 1,
					allowDecimals: false,
					allowBlank: false
				}
			}],
			tbar: [{
				text: '保存',
				icon: ctx + '/resource/image/icon/save.png',
				handler: this.saveGrid,
				scope: this
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
					fieldLabel: '角色名称',
					name: 'name'
				}, {
					xtype: 'textfield',
					fieldLabel: '描述',
					name: 'description'
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
			},
			plugins: [cellEditing]
		});
		this.callParent();

		this.on('itemclick', function(grid, record, item, index, e, eOpts){
			myServer.getMainContent().getComponent('moduleTreePanel').loadTree(record.get('id'));
		});

		this.store.on('beforeload', function(store, operation, eOpts) {
			var form = this.getComponent('queryForm').getForm();

			this.store.proxy.actionMethods = {read: 'POST'};
			Ext.apply(this.store.proxy.extraParams, {
				'qc.name': form.findField('name').getValue(),
				'qc.description': form.findField('description').getValue()
			});
		}, this);

		this.queryData();
	},
	doAddEvent: function() {
		var record = Ext.ModelManager.create({
			name: '角色名称',
			description: '',
			sort: 1
		}, Ext.getClassName(this.getStore().model));
		this.getStore().insert(0, record);
		cellEditing.startEditByPosition({row: 0, column: 0});
	},
	doDeleteEvent: function(ids) {
		Ext.Ajax.request({
			url: ctx + '/sysmanager/role/delete.ctrl',
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
	saveGrid: function() {
		var records = this.getStore().getModifiedRecords();
		var datas = [];
		for (var i = 0; i < records.length; i++) {
			datas.push({
				id: records[i].get('id'),
				name: records[i].get('name'),
				description: records[i].get('description'),
				sort: records[i].get('sort')
			});
		}

		if(datas.length > 0) {
			Ext.Ajax.request({
				url: ctx + '/sysmanager/role/save.ctrl',
				params: {
					roleJson: Ext.encode(datas)
				},
				success: function(response, opts) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
					this.queryData();
				},
				failure: function(response, opts) {
					Ext.Msg.alert('系统提示', '无法保存数据！');
				},
				scope: this
			});
		}
	},
	getSelectedRecord: function() {
		return this.getSelectionModel().getLastSelected();
	},
	queryData: function() {
		this.getStore().load();
	},
	clear: function() {
		var form = this.getComponent('queryForm').getForm();
		form.findField('name').setValue('');
		form.findField('description').setValue('');
		this.queryData();
	}
});
