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
		}, {
			data : "gender",
			orderable : false,
		}, {
			data : "unitName",
			orderable : false,
		}, {
			data : "position",
			orderable : false,
		},],
	
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
}

/* INPUT ENCODE */
function initControl(){
	$('#input-encode').keypress(function(event) {
		var code = event.keyCode || event.which;
		if (code == 13) {
			event.preventDefault();
			$('#error-msg').addClass("hidden");
			var val = $(this).val();
			$.ajax({
				url : "counting/check_code",
				type : "POST",
				data : {encode : val},
				success : function(response) {
					if(response > 0){
						$('#error-msg').removeClass("hidden");
						$('#' + response).addClass("success");
					} else if(response > 0) {
						$('#error-msg').addClass("hidden");
					}
					
					console.log(response);
				},
				error : function (){
					$('#error-msg').removeClass("hidden");
				},
			
			});
			
			$(this).select();
		}
	});
	
	$('#input-encode').focus(function(){
		$('error-msg').addClass("hidden");
		$(this).select();
	});
}