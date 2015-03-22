Ext.define('Base.ux.GridPanel', {
	extend: 'Ext.grid.Panel',

	url: null,
	sortProperty: null,
	sortDirection: null,
	enableCheckbox: true,
	enableRowNumber: true,
	enablePaging: true,
	enableAddBtn: true,
	enableEditBtn: true,
	enableViewBtn: true,
	enableDelBtn: true,
	enableColumnEditBtn: true,
	enableColumnViewBtn: true,
	enableColumnDelBtn: true,
	selectMode: 'MULTI',// SINGLE, SIMPLE, MULTI
	idProperty: null,
	pageSize: 20,
	loadMask: true,
	columns: [],

	initComponent: function() {
		var fields = [];
		this.initModelFields(fields, this.columns);

		var modelName = 'GridModel_' + Ext.create('Ext.data.UuidGenerator').generate();
		Ext.define(modelName, {
			extend: 'Ext.data.Model',
			fields: fields,
			idProperty: this.idProperty
		});

		this.store = Ext.create('Ext.data.Store', {
			pageSize: this.pageSize,
			model: modelName,
			remoteSort: true,
			proxy: {
				type: 'ajax',
				url: this.url,
				reader: {
					type: 'json',
					root: 'dataList',
					totalProperty: 'paginated.totalRecord'
				}
			},
			sorters: [{
				property: this.sortProperty,
				direction: this.sortDirection
			}]
		});

		if (this.enableCheckbox) {
			this.selModel = Ext.create('Ext.selection.CheckboxModel', {mode: this.selectMode});
		}

		if (this.enableRowNumber) {
			this.columns = [{
				xtype: 'rownumberer',
				resizable: true,
				width: 40,
				sortable: false
			}].concat(this.columns);
		}

		if (this.enablePaging) {
			this.bbar = Ext.create('Ext.toolbar.Paging', {
				store: this.store,
				displayInfo: true
			});
		}

		var textBtns = [];
		if (this.enableAddBtn) {
			textBtns.push({
				xtype: 'button',
				text: '新增',
				icon: ctx + '/resource/image/icon/add.png',
				handler: this.addEventHandler,
				scope: this
			});
		}
		if (this.enableEditBtn) {
			textBtns.push({
				xtype: 'button',
				text: '编辑',
				icon: ctx + '/resource/image/icon/edit.png',
				handler: this.editEventHandler,
				scope: this
			});
		}
		if (this.enableViewBtn) {
			textBtns.push({
				xtype: 'button',
				text: '查看',
				icon: ctx + '/resource/image/icon/view.png',
				handler: this.viewEventHandler,
				scope: this
			});
		}
		if (this.enableDelBtn) {
			textBtns.push({
				xtype: 'button',
				text: '删除',
				icon: ctx + '/resource/image/icon/delete.png',
				handler: this.delEventHandler,
				scope: this
			});
		}

		if (textBtns.length > 0) {
			if (this.tbar && this.tbar.length > 0) {
				this.tbar = textBtns.concat(this.tbar);
			} else {
				this.tbar = textBtns;
			}

		}

		var iconBtns = [];
		if (this.enableColumnEditBtn) {
			iconBtns.push({
				icon: ctx + '/resource/image/icon/edit.png',
				tooltip: '编辑',
				handler: this.editEventHandler,
				scope: this
			});
		}
		if (this.enableColumnViewBtn) {
			iconBtns.push({
				icon: ctx + '/resource/image/icon/view.png',
				tooltip: '查看',
				handler: this.viewEventHandler,
				scope: this
			});
		}
		if (this.enableColumnDelBtn) {
			iconBtns.push({
				icon: ctx + '/resource/image/icon/delete.png',
				tooltip: '删除',
				handler: this.delEventHandler,
				scope: this
			});
		}

		if (iconBtns.length > 0) {
			this.columns.push({
				xtype: 'actioncolumn',
				stopSelection: false,
				items: iconBtns
			});
		}

		this.callParent();
	},
	initModelFields: function(fields, columns) {
		var attributes = ['type', 'convert', 'dateFormat', 'defaultValue', 'mapping', 'persist', 'serialize', 'sortDir', 'sortType', 'useNull'];
		for (var i = 0; i < columns.length; i++) {
			var field = {};
			var column = columns[i];
			if (column.dataIndex) {
				field.name = column.dataIndex;
				Ext.Array.each(attributes, function(attribute) {
					if (column[attribute] != undefined) {
						field[attribute] = column[attribute];
					}
				});
				fields.push(field);
			}
			if (column.columns) {
				this.initModelFields(fields, column.columns);
			}
		}
	},
	getSelectedId: function() {
		var records = this.getSelectionModel().getSelection();
		return records.length > 0 ? records[0].get(this.idProperty) : null;
	},
	getSelectedIds: function() {
		var ids = [];
		var records = this.getSelectionModel().getSelection();
		for(var i = 0; i < records.length; i++){
			ids.push(records[i].get(this.idProperty));
		}
		return ids;
	},
	getSelectedRecord: function() {
		var records = this.getSelectionModel().getSelection();
		return records.length > 0 ? records[0] : null;
	},
	getSelectedRecords: function() {
		return this.getSelectionModel().getSelection();
	},
	addEventHandler: function() {
		if (this.doAddEvent) {
			this.doAddEvent();
		}
	},
	editEventHandler: function() {
		if (this.doEditEvent) {
			var ids = this.getSelectedIds();
			if (ids.length == 1) {
				this.doEditEvent(ids[0]);
			} else {
				Ext.Msg.alert("系统提示", "请选择一条数据！");
			}
		}
	},
	viewEventHandler: function() {
		if (this.doViewEvent) {
			var ids = this.getSelectedIds();
			if (ids.length == 1) {
				this.doViewEvent(ids[0]);
			} else {
				Ext.Msg.alert("系统提示", "请选择一条数据！");
			}
		}
	},
	delEventHandler: function() {
		if (this.doDeleteEvent) {
			var ids = this.getSelectedIds();
			if (ids.length > 0) {
				Ext.MessageBox.confirm("系统提示", "确定要删除数据吗？", function (btn) {
					if ('yes' == btn) {
						this.doDeleteEvent(ids);
					}
				}, this);
			} else {
				Ext.Msg.alert("系统提示", "请选择一条数据！");
			}
		}
	}
});

