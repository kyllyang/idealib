Ext.define('Meaord.restaurant.RestaurantFormWindow', {
	extend: 'Ext.window.Window',

	entityId: null,
	readOnlyForm: false,

	title: '餐厅信息',
	width: 600,
	height: 240,
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
				{name: 'name'},
				{name: 'description'},
				{name: 'linkman'},
				{name: 'phone1'},
				{name: 'phone2'},
				{name: 'sort'}
			]
		});

		var nameText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>餐厅名称',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'name',
			maxLength: 50,
			allowBlank: false,
			readOnly: this.readOnlyForm
		});
		var descriptionTextArea = Ext.create('Ext.form.field.TextArea', {
			fieldLabel: '简介',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'description',
			maxLength: 100,
			readOnly: this.readOnlyForm
		});
		var linkmanText = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>联系人',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'linkman',
			maxLength: 50,
			allowBlank: false,
			readOnly: this.readOnlyForm
		});
		var phone1Text = Ext.create('Ext.form.field.Text', {
			fieldLabel: '<span style="color: #FF0000;">*</span>联系电话1',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'phone1',
			maxLength: 50,
			regex: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
			regexText: '不是有效的电话号码',
			readOnly: this.readOnlyForm
		});
		var phone2Text = Ext.create('Ext.form.field.Text', {
			fieldLabel: '联系电话2',
			labelAlign: 'right',
			labelSeparator: '：',
			name: 'phone2',
			maxLength: 50,
			regex: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/,
			regexText: '不是有效的电话号码',
			readOnly: this.readOnlyForm
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
			}, nameText, descriptionTextArea, linkmanText, {
				xtype: 'container',
				layout: 'column',
				items: [
					Ext.apply(phone1Text, {columnWidth: 0.5}),
					Ext.apply(phone2Text, {columnWidth: 0.5})
				]
			}, sortNumber],
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
				url: ctx + '/meaord/restaurant/input.ctrl',
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
				url: ctx + '/meaord/restaurant/save.ctrl',
				waitMsg: '正在保存数据，请稍候...',
				success: function(form, action) {
					Ext.Msg.alert('系统提示', '数据保存成功！');
					this.closeForm();
					myServer.getMainContent().queryData();
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
