/**
 * 
 */

$(document).ready(function(){
	var candidate_table;
	loadCandidateTable();
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
		/*"columnDefs": [{
			"render" : function(data, type, row) {
				return "<img class='img-responsive code-pattern' src='../img/barcode/" + data + "'style='width : 50px; height : 50px;'/>";
			},
			"targets" : 6
		},],*/
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