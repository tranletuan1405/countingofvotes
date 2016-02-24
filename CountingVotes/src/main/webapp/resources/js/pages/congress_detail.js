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
		var cw = $('#img-avatar').width();
		var ch = (cw * 4 / 3) + 'px';
		console.log(ch);
		$('#img-avatar').css('height', ch);
	});
}

function loadUnits(){
	var unit_table = $('#unit-table').DataTable({
		destroy : true,
		ajax : "units",
		"lengthMenu": [[10, 15, 20, -1], [10, 15, 20, "Tất cả"]],
		rowId : "id",
		columns: [
			{ data : "name" },
			{ data : "code" },
			{ data : "shortName" },
			
			{ data : "numOfDBDN" , searchable : false},
			{ data : "numOfDBCD" , searchable : false},
			{ data : "numOfDBBC" , searchable : false},
			{ data : "numOfDBDK" , searchable : false},
			{ data : "total" , searchable : false},
			{ data : "id" , searchable : false}
		],
		"columnDefs" : [{
			"render" : function(data, type, row) {
				return "<button type='button' class='btn btn-default'>Chi tiết</button>";

			},
			"targets" : 8
		}]
	});
}

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
		   { data : "note" },
		   
		   { data : "codeImage" },
		   { data : "codeContent", "visible": false },
		   { data : "id", searchable : false }
		 ],
		 
		 "columnDefs": [{
		    "render" : function(data, type, row) {
		       return "<img class='img-thumbnail' src='../img/" + data + "' height='50' width='50'>";
		     },
		     "targets" : 9
		 }, {
			"render" : function(data, type, row) {
				return "<button type='button' class='btn btn-default' data-toggle='modal' data-target='#modal-delegate'>Chi tiết</button>";

			},
			"targets" : 11
		 }],
		 "initComplete": function(settings, json) {
			 loadEnterEvent();
		 }
		
		
	});
};