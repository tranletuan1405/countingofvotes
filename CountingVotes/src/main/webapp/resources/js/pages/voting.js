/**
 * 
 */

$(document).ready(function(){
	
	var voting_table;
	loadVotings();
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
		},],
		
		dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>"
			+ "<'row'<'col-sm-12'tr>>"
			+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",

		buttons : [ {
			text : "Thêm",
			className : "btn-primary btn-voting-table",
			action : function(){
				showModal('#modal-create-voting');
			},
		}, {
			text : "Sửa",
			className : "btn-warning btn-voting-table btn-edit disabled",
			action : function(){
				showModal('#modal-create-voting');
			}
		}, {
			text : "Xóa",
			className : "btn-danger btn-voting-table btn-edit disabled",
			action : function (){
				
			}
		}],
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "Tất cả" ] ],
		select : "single",
	});	
	
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
		var form = $('#form-create-voting');

		$.ajax({
			url : "create_voting",
			type : "POST",
			data : form.serialize(),
			success : function(msg) {
				if (msg == 'success') {
					$('#modal-create-voting').modal('hide');
					console.log('oke');
				} else {
					$('#modal-create-voting').modal('hide');
				}
			},
			error : function(msg) {

			}

		});
	});
}

