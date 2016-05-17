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
			"targets" : 5,		
			},],
		"order": [[ 6, "desc" ]],
	
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "Tất cả" ] ],
		select : "single",
	});
}
