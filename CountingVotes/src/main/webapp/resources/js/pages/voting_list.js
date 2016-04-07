/**
 * 
 */

$(document).ready(function(){
	
	var voting_table;
	loadVotings();
	initTableEvents();
	initCreateModal();
});

//=========VOTING===========
function loadVotings() {
	
	voting_table = $('#voting-table').DataTable({
		destroy : true,
		ajax : "table",
		rowId : "id",
		columns : [{
			data : "name",
		}, {
			data : "version",
		}, {
			data : "startTime",
		}, {
			data : "endTime",
		}, {
			data : "numOfCandidates",
		}, {
			data : "numOfBallots",
			visible : false,
			searchable : false,
			orderable : false,
		}, {
			data : "id",
			orderable : false,
			searchable : false,
		}, {
			data : "id",
			visible : false,
		},],
		"columnDefs" : [ {
			"render" : function(data, type, row) {
				return "<a class='btn btn-default no-radius' href='../voting_detail/" + data + "'>Chi tiết</a>";
			},				
			"targets" : 6,		
			},],
		"order": [[ 7, "desc" ]],
		
		dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>"
			+ "<'row'<'col-sm-12'tr>>"
			+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",

		buttons : [ {
			text : "Thêm",
			className : "btn-primary btn-voting-table no-radius",
			action : function(){
				showModal('#modal-create-voting');
			},
		}, {
			text : "Sửa",
			className : "btn-warning btn-voting-table btn-edit disabled no-radius",
			action : function(){
				showModal('#modal-create-voting');
			}
		}, {
			text : "Xóa",
			className : "btn-danger btn-voting-table btn-edit disabled no-radius",
			action : function (){
				
			}
		}],
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "Tất cả" ] ],
		select : "single",
	});	
}

function initTableEvents() {
	voting_table.on( 'select', function ( e, dt, type, indexes ) {
	    if ( type == 'row' ) {
	       $('.btn-edit').removeClass('disabled');
	    }
	});
	
	voting_table.on( 'deselect', function ( e, dt, type, indexes ) {
	    if ( type == 'row' ) {
	       $('.btn-edit').addClass('disabled');
	    }
	});
}

//==========CREATE MODAL================
function initCreateModal() {
	$('#modal-create-voting').on('shown.bs.modal', function() {
		$(this).find('[autofocus]').focus();
	});
		
	$('#btn-submit-voting').click(function() {
		$('#btn-submit-real').click();

	});
}

