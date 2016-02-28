/**
 * 
 */

$(document).ready(function() {

	var delegate_table;
	var unit_table;
	loadDelegates();
	loadUnits();
	onShowDelegateModal();

});


function onShowDelegateModal() {
	
	$('#modal-delegate').on('shown.bs.modal', function(){

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

/*======================UNIT=======================*/
function loadUnits(){
	var unit_table = $('#unit-table').DataTable({
		destroy : true,
		ajax : "units",
		"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "Tất cả"]],
		rowId : "id",
		columns: [
		    { data : "code" },
			{ data : "name" },
			{ data : "shortName" },
			{ data : "numOfDBDN" , searchable : false},
			{ data : "numOfDBCD" , searchable : false},
			{ data : "numOfDBBC" , searchable : false},
			{ data : "numOfDBDK" , searchable : false},
			{ data : "total" , searchable : false},
			{ data : "id" , searchable : false},
			{ data : "id" , searchable : false}
		],
		"columnDefs" : [{
			"render" : function(data, type, row) {
				return "<button type='button' class='btn btn-default' onClick='loadDetailUnit(" + data +")'>Chi tiết</button>";

			},
			"targets" : 8, "orderable": false
		}, {
			"render" : function(data, type, row) {
				return "<button type='button' class='btn btn-danger'>Xóa</button>";
			},
			"targets" : 9, "orderable": false
		}
		
		],
		
		dom : 	"<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>" +
	 			"<'row'<'col-sm-12'tr>>" +
	 			"<'row'<'col-sm-5'i><'col-sm-7'p>>",
	 	buttons: [
	 	        { text : "Thêm",  className: 'btn-primary', action : function(){}},
	 	],
	});
}

function loadDetailUnit(id){
	$.ajax({
		url : 'unit/' + id,
		success : function (data) {
			$('#detail-unit').html(data);
			$('#modal-unit').modal('show');
		},
		
		error : function() {
			console.log('load unit error');
		}
	});
}

/*=====================DELEGATE========================*/

function loadDelegates(){
	var delegate_table = $('#delegate-table').DataTable({
		destroy : true,
		ajax : "delegates",
		"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "Tất cả"]],
		rowId : "id",
		columns: [
		   { data : "ordinal" },
		   { data : "name" },
		   { data : "placeOfBirth" },
		   { data : "dateOfBirth" },
		   { data : "gender" },
		   { data : "unitName" },
		   { data : "position" },
		   { data : "typeName" },

		   { data : "codeImage" },
		   { data : "codeContent", "visible": false },
		   { data : "id", searchable : false, "ordering" : false },
		   { data : "id", searchable : false, "ordering" : false }
		 ],
		 
		 "columnDefs": [{
		    "render" : function(data, type, row) {
		       return "<img class='img-thumbnail' src='../img/" + data + "' height='50' width='50'>";
		     },
		     "targets" : 8, "orderable": false
		 }, {
			"render" : function(data, type, row) {
				return "<button type='button' class='btn btn-default' onClick='loadDetailDelegate(" + data + ")'>Chi tiết</button>";

			},
			"targets" : 10, "orderable": false
		 }, {
				"render" : function(data, type, row) {
					return "<button type='button' class='btn btn-danger'>Xóa</button>";

			},
			"targets" : 11, "orderable": false
		 }
		 ],
		 dom : 	"<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>" +
		 		"<'row'<'col-sm-12'tr>>" +
		 		"<'row'<'col-sm-5'i><'col-sm-7'p>>",
		 buttons: [
		    { text : "Thêm", className: "btn-primary", action : function(){}},
		 
		 ],
		 "initComplete": function(settings, json) {
			 loadEnterEvent();
		 }	
	});
};

function loadDetailDelegate(id) {
	$.ajax({
		url : 'delegate/' + id,
		success : function(data){
			$('#detail-delegate').html(data);
			$('#modal-delegate').modal('show');
		},
		
		error : function() {
			console.log("load delegate error");
		}
	});
}
