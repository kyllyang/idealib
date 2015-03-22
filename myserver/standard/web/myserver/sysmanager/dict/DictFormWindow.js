Ext.define('SysManager.dict.DictFormWindow', {
	extend: 'Ext.window.Window',

	entityId: null,

	title: '数据字典信息',
	width: 600,
	height: 180,
	border: false,
	modal: true,
	resizable: false,
	layout: 'fit',

	initComponent: function() {
		this.callParent();

		Ext.define('FormModel', {
			extend: 'Ext.data.Model',
			fields: [
				{name: 'id'},
				{name: 'parentId'},
				{name: 'parentName'},
				{name: 'invokeCode'},
				{name: 'name'},
				{name: 'sort'}
			]
		});

		var record = myServer.getMainContent().getComponent('dictTreePanel').getSelectedRecord();

		var parentNameText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '上级数据字典',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'parentName',
			value: record.get('id') ? record.get('name') : null,
			maxLength: 50,
			disabled: true
		});
		var invokeCodeText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>调用编码',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'invokeCode',
			maxLength: 100,
			allowBlank: false
		});
		var nameText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>名称',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'name',
			maxLength: 100,
			allowBlank: false
		});
		var sortNumber = Ext.create('Ext.form.field.Number', {
			fieldLabel: '排序',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'sort',
			value: 1,
			minValue: 1,
			allowDecimals: false,
			readOnly: this.readOnlyForm
		});

		this.add(Ext.create('Ext.form.Panel', {
			itemId: 'editForm',
			frame: true,
			autoHeight: true,
			autoScroll: true,
			buttonAlign: 'center',
			reader: Ext.create('Ext.data.JsonReader', {
				model: 'FormModel'
			}),
			layout: 'form',
			items: [{
				xtype: 'hidden',
				name: 'id'
			}, {
				xtype: 'hidden',
				name: 'parentId',
				value: record.get('id') ? record.get('id') : null
			},
				parentNameText,
				invokeCodeText,
				nameText,
				sortNumber
			],
			buttons:[{
				xtype: 'button',
				text: '保存',
				handler: this.saveForm,
				scope:this,
				hidden: this.readOnlyForm
			}, {
				xtype: 'button',
				text: '关闭',
				handler: this.closeForm,
				scope:this
			}]
		}));

		if (this.entityId) {
			this.getComponent('editForm').getForm().load({
				url: ctx + '/sysmanager/dict/input.ctrl',
				params: {
					id: this.entityId
				},
				waitMsg: '正在载入数据...',
				success: function(form, action) {
				},
				failure: function() {
					Ext.Msg.alert('系统提示', '无法加载信息！');
				},
				scope: this
			});
		}
	},
	saveForm: function() {
		var form = this.getComponent('editForm').getForm();
		if (form.isValid()) {
			form.submit({
				url: ctx + '/sysmanager/dict/save.ctrl',
				waitMsg: '正在保存数据，请稍候...',
				success: function(form, action) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
					this.closeForm();

					var record = myServer.getMainContent().getComponent('dictTreePanel').getSelectedRecord();
					myServer.getMainContent().getComponent('dictTreePanel').loadTreeNode(record.parentNode && record.parentNode.get('id') ? record.parentNode.get('id') : null);
				},
				failure: function(form, action) {
					Ext.Msg.alert('系统提示', '无法保存数据！');
				},
				scope: this
			});
		}
	},
	closeForm: function() {
		this.close();
	}
});
