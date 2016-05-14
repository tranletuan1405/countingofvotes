/**
 * 
 */
$(document).ready(function(){
	
	var candidate_table;
	initCandidateTable();
});


/* Init Candidate Table */
function initCandidateTable(){
	candidate_table = $('#candidate-table').DataTable({
		destroy : true,
		ajax : "statistics_detail/candidate_table",
		rowId : "id",
		columns : [ {
			data : "id",
			orderable : false,
			searchable : false,
		}, {
			data : "name",
		}, {
			data : "dateOfBirth",
			visible : false,
		}, {
			data : "gender",
			visible : false,
		}, {
			data : "position",
			visible : false,
		}, {
			data : "totalVote",
		}, {
			data : "rate",
		}],
		columnDefs : [{
			render : function(data, type, row){
				return data.toFixed(2);
			},
			targets : 6
		}],
		"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "Tất cả"]],
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