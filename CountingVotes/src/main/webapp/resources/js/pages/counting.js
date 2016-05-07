/**
 * 
 */

$(document).ready(function(){
	var candidate_table;
	loadCandidateTable();
	initControl();
});

//====CANDIDATE TABLE====
function loadCandidateTable(){
	candidate_table = $('#candidate-table').DataTable({
		destroy : true,
		paging : false,
		searching : false,
		ajax : "counting/ballot_canidates",
		rowId : "id",
		order : [[ 1, 'asc' ]],
		columns : [ {
			data : "id",
			orderable : false,
			searchable : false,
		}, {
			data : "name",
		}, {
			data : "dateOfBirth",
			orderable : false,
			visible : false,
		}, {
			data : "gender",
			orderable : false,
			visible : false,
		}, {
			data : "unitName",
			orderable : false,
			visible : false,
		}, {
			data : "position",
			orderable : false,
			visible : false,
		}, {
			data : "id",
			orderable : false,
			searchable : false,
		}, {
			data : "countingCode",
			orderable : false,
			searchable : false,
			visible : false
		}],
		
		columnDefs : [{
			render : function(data, type, row){
				return "<input type='checkbox' />";
			},
			targets : 6,
		}],
	
		select : false,
		"initComplete": function(settings, json) {
			
			$('a.toggle-vis').on( 'click', function (e) {
		        e.preventDefault();
		 
		        // Get the column API object
		        var column = candidate_table.column( $(this).attr('data-column') );
		 
		        // Toggle the visibility
		        column.visible( ! column.visible() );
		    } );
		}
	});
	
	candidate_table.on( 'order.dt search.dt', function () {
		candidate_table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        });
    }).draw();
	
	$('#candidate-table tbody').on('click', 'tr', function (event) {
		var data = candidate_table.row( this ).data();
		var rowId = candidate_table.row( this ).id();
	    var checkbox = $("#" + rowId + ">td>input[type='checkbox']");

	    if(checkbox.is(':checked')){ //Đã chọn (sau response sẽ bỏ trạng thái chọn)'
	    	deselectCandidate(data['countingCode']);
	    }else { //Chưa chọn (sau response sẽ chọn)
	    	selectCandidate(data['countingCode']);
	    }
	});
}

function selectCandidate(val){
	$.ajax({
		url : "counting/select_candidate",
		type : "POST",
		data : {encode : val},
		success : function(response) {
			if(response > 0){
				$('#error-msg').addClass("hidden");
				$('#' + response).addClass("success");
				$('#' + response + ">td>input[type='checkbox']").prop('checked', true);
			} else {
				$('#error-msg').removeClass("hidden");
			}
		},
		error : function (){
			$('#error-msg').removeClass("hidden");
		},
	
	});
}

function deselectCandidate(val){
	$.ajax({
		url : "counting/deselect_candidate",
		type : "POST",
		data : {encode : val},
		success : function(response) {
			if(response > 0){
				$('#error-msg').addClass("hidden");
				$('#' + response).removeClass("success");
				$('#' + response + ">td>input[type='checkbox']").prop('checked', false);
			} else {
				$('#error-msg').removeClass("hidden");
			}
		},
		error : function (){
			$('#error-msg').removeClass("hidden");
		},
	
	});
}

/* INPUT ENCODE */
function initControl(){
	
	//Input code
	$('#input-encode').keypress(function(event) {
		var code = event.keyCode || event.which;
		if (code == 13) {
			event.preventDefault();
			$('#error-msg').addClass("hidden");
			var val = $(this).val();
			selectCandidate(val);
			$(this).select();
		}
	});
	
	$('#input-encode').focus(function(){
		$('error-msg').addClass("hidden");
		$(this).select();
	});
	
	//Checkbox
	$('#checkbox-use-code').change(function() {
		if (this.checked) {
			$('#btn-save-ballot').attr('disabled', 'disabled');
		} else {
			$('#btn-save-ballot').removeAttr('disabled');
		}

	});
	
	//Btn refresh
	$('#btn-refresh').click(function(){
		var input = $('#input-encode').val('');
		input.focus();
		$('#candidate-table>tbody>tr').removeClass('success');
	});
	
	//Btn save ballot
	$('#btn-save-ballot').click(function(){
		
	});
}