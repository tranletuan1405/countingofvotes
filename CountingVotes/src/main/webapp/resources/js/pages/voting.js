/**
 * 
 */

$(document).ready(function(){
	
	var voting_table;
	//loadVotings();
	
});

//=========VOTING===========

function loadVotings() {
	
	voting_table = $('#voting-table').DataTable({
		destroy : true,
		ajax : "list",
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
		},],
		
		"lengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "Tất cả" ] ],
	});
	
}