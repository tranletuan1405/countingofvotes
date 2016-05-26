/**
 * 
 */

$(document).ready(function(){
	
	var statistics_table;
	initStatisticsTable();
	
});

/* ========== STATISTICS LIST============*/
function initStatisticsTable(){
	
	statistics_table = $('#statistics-table').DataTable({
		destroy : true,
		ajax : "statistics_table",
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
			data : "numOfBallots"
		}, {
			data : "countedBallots"
		},{
			data : "id",
			orderable : false,
			searchable : false,
		},],
		"columnDefs" : [ {
			"render" : function(data, type, row) {
				return "<a class='btn btn-default no-radius' href='../statistics_detail/" + data + "'>Chi tiết</a>";
			},				
			"targets" : 7,		
			},],
		"order": [[ 7, "desc" ]],
	
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "Tất cả" ] ],
		select : "single",
	});
}
