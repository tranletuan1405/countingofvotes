/**
 * 
 */

$(document).ready(function () {

	var candidate_table;
	loadCandidateTable();
});


/* Candidate Table */
function loadCandidateTable(){
	
	candidate_table = $('#candidate-table').DataTable({
		destroy : true,
		url : "candidates",
		rowId : "id",
		dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>"
			+ "<'row'<'col-sm-12'tr>>"
			+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",
		buttons : [ {
			text : "Chỉnh sửa",
			className : "btn-warning no-radius",
			action : function(){
				
			},
		},],
		columns : [ {
			data : "ordinal"
		}, {
			data : "name"
		}, {
			data : "dateOfBirth"
		}, {
			data : "gender"
		}, {
			data : "unitName",
		}, {
			data : "position",
		}, {
			data : "typeName",
		}, ],
		
		"lengthMenu" : [ [-1, 5, 10, 15 ], ["Tất cả", 5, 10, 15] ],
		select : false,
		
	});
}