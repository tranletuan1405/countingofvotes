/**
 * 
 */
$(document).ready(function(){
	
	var candidate_table;
	var candidate_chart;
	
	initCandidateTable();
	intiChart();
});


/* Init Candidate Table */
function initCandidateTable(){
	candidate_table = $('#candidate-table').DataTable({
		destroy : true,
		ajax : "statistics_detail/candidate_table",
		rowId : "id",
		order: [[ 5, "desc" ]],
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
			
			
			intiChart();
		}
	});
	
	candidate_table.on( 'order.dt search.dt', function () {
		candidate_table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        });
    }).draw();
}


/*===========CHART CONTAINER ================*/
function intiChart(){
	//var chart_data = candidate_table.
	$.ajax({
		url : "statistics/chart_data",
		type : "GET",
		success : function (chart_data){
			var chart_title = $('#voting-name').html();
			candidate_chart = new CanvasJS.Chart('chart-candidate-container', {
				title : { text : "Thống kê " + chart_title },
				data : [ {
					type : 'column',
					dataPoints : chart_data['data'],
				} ],
			});
			candidate_chart.render();
			
			$('.canvasjs-chart-credit').remove();
		},
		error : function (){
			console.log('load chart data failed');
		}
	});
}
