/**
 * 
 */

$(document).ready(function() {

	var delegate_table;
	var unit_table;
	var cur_checked_row_id;
	

	loadDelegates();
	loadUnits();
	onShowDelegateModal();
	updateCongressInfo();
	
});

function onShowDelegateModal() {

	$('#modal-delegate').on('shown.bs.modal', function() {

		makeRect('#panel-avatar', 1 / 1.6);
		makeRect('#panel-other-info', 1);

		var height_main_info = parseInt($('#panel-avatar').height());
		var height_other_info = parseInt($('#panel-other-info').height());

		$('#img-avatar').css('width', (height_main_info - 20) + "px");
		$('#img-avatar').css('height', (height_main_info - 20) + "px");

		$('#panel-avatar').css('height', height_main_info + "px");
		$('#panel-other-info').css('height', height_other_info + "px");
		$('#panel-main-info').css('height', height_main_info + "px");
		$('#panel-achievement').css('height', height_other_info + "px");

		$('#img-avatar').click(function() {
			$('#file-avatar').click();
		});
	});
}

/* ======================CONGRESS======================= */
function updateCongressInfo() {

	var pre_congress_value;
	var congress_msg_id = 0;
	$('.congress-input').focus(function() {
		pre_congress_value = $(this).val();
	});
	
	$('.congress-input').focusout(function(){
		var cur_val = $(this).val();
		if(pre_congress_value != null && (cur_val == "" || cur_val == null)){
			$(this).val(pre_congress_value);
		}
	});

	$('.edit-congress').click(function() {
		var target = $(this).attr('data-target');
		var input = $(target);
		var field = input.attr('name');
		var value = input.val();

		$.ajax({
			url : "update_congress/" + field,
			type : "POST",
			data : {
				'value' : value
			},
			success : function(response) {
				if (response != null && response != "") {
					input.val(response);
					pre_congress_value = null;
					
					var msg_id = showMessage('congress-msg', 'success', 'Cập nhật thành công', ++congress_msg_id);
					setTimeout(function(){
						if(msg_id == congress_msg_id){
							$('#congress-msg').addClass('hidden');
						}
					}, 3000);
				} else if (pre_congress_value != null && pre_congress_value != ""){
					input.val(pre_congress_value);
					
				}
			},
			error : function() {
				var msg_id = showMessage('congress-msg', 'danger', 'Cập nhật thất bại', ++congress_msg_id);
				setTimeout(function(){
					if(msg_id == congress_msg_id){
						$('#congress-msg').addClass('hidden');
					}
				}, 3000);
			}
		});

	});

}

/* ======================UNIT======================= */
function loadUnits() {
	var unit_table = $('#unit-table')
			.DataTable(
					{
						destroy : true,
						ajax : "units",
						"lengthMenu" : [ [ 10, 15, 20, -1 ],
								[ 10, 15, 20, "Tất cả" ] ],
						rowId : "id",
						columns : [ {
							data : "code"
						}, {
							data : "name"
						}, {
							data : "shortName"
						}, {
							data : "numOfDBDN",
							searchable : false
						}, {
							data : "numOfDBCD",
							searchable : false
						}, {
							data : "numOfDBBC",
							searchable : false
						}, {
							data : "numOfDBDK",
							searchable : false
						}, {
							data : "total",
							searchable : false
						}, {
							data : "id",
							searchable : false
						}, {
							data : "id",
							searchable : false
						} ],
						"columnDefs" : [
								{
									"render" : function(data, type, row) {
										return "<button type='button' class='btn btn-default no-radius' onClick='loadDetailUnit("
												+ data + ")'>Chi tiết</button>";

									},
									"targets" : 8,
									"orderable" : false
								},
								{
									"render" : function(data, type, row) {
										return "<button type='button' class='btn btn-danger no-radius'>Xóa</button>";
									},
									"targets" : 9,
									"orderable" : false
								}

						],

						dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>"
								+ "<'row'<'col-sm-12'tr>>"
								+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",
						buttons : [ {
							text : "Thêm",
							className : 'btn-primary no-radius standard-color',
							action : function() {
							}
						}, ],
					});
}

function loadDetailUnit(id) {
	$.ajax({
		url : 'unit/' + id,
		success : function(data) {
			$('#detail-unit').html(data);
			$('#modal-unit').modal('show');
		},

		error : function() {
			console.log('load unit error');
		}
	});
}

/* =====================DELEGATE======================== */

function loadDelegates() {
	delegate_table = $('#delegate-table')
			.DataTable(
					{
						destroy : true,
						ajax : "delegates",
						"lengthMenu" : [ [ 5, 10, 15, -1 ],
								[ 5, 10, 15, "Tất cả" ] ],
						rowId : "id",
						columns : [ {
							data : "ordinal"
						}, {
							data : "name"
						}, {
							data : "dateOfBirth"
						}, {
							data : "gender"
						}, {
							data : "unitName",
							visible : false
						}, {
							data : "position",
							visible : false
						}, {
							data : "typeName",
							visible : false
						}, {
							data : "arivalTime"
						},

						/* { data : "codeImage" }, */

						{
							data : "attended",
							searchable : false
						}, {
							data : "id",
							searchable : false,
							"orderable" : false
						}, {
							data : "id",
							searchable : false,
							"orderable" : false
						}, {
							data : "imagePath",
							searchable : false,
							"orderable" : false,
							"visible" : false
						}, {
							data : "codeContent",
							"visible" : false
						}, ],

						"columnDefs" : [
								{
									"render" : function(data, type, row) {
										var id = row['id'];
										var checked = Boolean(row['attended']) ? "checked"
												: "";
										return "<input type='checkbox' class='form-control' id='attended-"
												+ id
												+ "' onchange='updateAttendedDelegate("
												+ id
												+ ")' style='width : 100%; margin : auto;' "
												+ checked + "/>";
									},
									"targets" : 8
								},
								{
									"render" : function(data, type, row) {
										return "<button type='button' class='btn btn-default no-radius' onClick='loadDetailDelegate("
												+ data + ")'>Chi tiết</button>";

									},
									"targets" : 9
								},

								{
									"render" : function(data, type, row) {
										return "<button type='button' class='btn btn-danger no-radius'>Xóa</button>";

									},
									"targets" : 10
								} ],
						dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>"
								+ "<'row'<'col-sm-12'tr>>"
								+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",
						buttons : [ {
							text : "Thêm",
							className : "btn-primary no-radius standard-color",
							action : function() {
							}
						},

						],

						select : 'single',
						
					});

	

	delegate_table.on('init.dt', function() {
		loadEnterEvent();
		
	});
	
	delegate_table.on('draw.dt', function(){
		var rows = delegate_table.rows({
			page : 'current'
		}).data();
		var numOfRow = rows.count();

		if (numOfRow == 1) {
			fillDelegateBanner(rows);
			$('#tabs-banner a[href="#tab-delegate-banner"]').tab('show');
			$('#tab-image-banner').addClass('hidden');
			$('#tab-delegate-banner').removeClass('hidden');
		} 
		
		if (numOfRow != 1){
			$('#tabs-banner a[href="#tab-image-banner"]').tab('show');
			$('#tab-image-banner').removeClass('hidden');
			$('#tab-delegate-banner').addClass('hidden');
		}
	});

	delegate_table.on('select', function(e, dt, type, indexes) {
		if (indexes.length > 0) {
			cur_checked_row_id = indexes[0];
		}
	});

	delegate_table.on('deselect', function(e, dt, type, indexes) {
		if (indexes.length > 0) {
			cur_checked_row_id = indexes[0];
		}
	});
};

function updateAttendedDelegate(id) {
	var cur_checked = cur_checked_row_id;
	var value = $('#attended-' + id).is(':checked');
	$.ajax({
		url : "update_attended/" + id,
		type : "POST",
		data : {
			'attended' : value
		},
		success : function(response) {
			var arivalTime = response["arivalTime"];
			var cell = delegate_table.cell(cur_checked, 7).data(arivalTime);
		},
		error : function() {
			console.log('Update attended fail');
		}
	});

	focusSearchInput();
}

function loadDetailDelegate(id) {
	$.ajax({
		url : 'delegate/' + id,
		success : function(data) {
			$('#detail-delegate').html(data);
			$('#modal-delegate').modal('show');
		},

		error : function() {
			console.log("load delegate error");
		}
	});
}

function fillDelegateBanner(data) {
	var imagePath = data.pluck('imagePath')[0];

	if (imagePath == null) {
		imagePath = '../images/page/default-avatar.png';
	}
	$('#banner-imagePath').attr("src", imagePath);
	$('#banner-ordinal').html(data.pluck('ordinal')[0]);
	$('#banner-name').html(data.pluck('name')[0]);
	$('#banner-gender').html(data.pluck('gender')[0]);
	$('#banner-dateOfBirth').html(data.pluck('dateOfBirth')[0]);
	$('#banner-unitName').html(data.pluck('unitName')[0]);
	$('#banner-position').html(data.pluck('position')[0]);
	$('#banner-typeName').html(data.pluck('typeName')[0]);
	$('#banner-arrivalTime').html(data.pluck('arrivalTime')[0]);
}