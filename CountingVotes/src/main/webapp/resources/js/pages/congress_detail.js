/**
 * 
 */

$(document).ready(function() {

	var delegate_table;
	var unit_table;
	loadDelegates();
	//loadUnits();
	
});

function loadUnits(){
	var unit_table = $('#unit-table').DataTable({
		destroy : true,
		ajax : "units",
		"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "Tất cả"]],
		rowId : "id",
		columns: [
		    { data : "ordinal" },
			{ data : "name" },
			{ data : "placeOfBirth" },
			{ data : "dateOfBirth" },
			{ data : "gender" },
			{ data : "unitName" },
			{ data : "position" },
			{ data : "typeName" },
			{ data : "note" },
				   
			{ data : "codeImage" },
			{ data : "codeContent", "visible": false}
		],
	});
}

function loadDelegates(){
	var delegate_table = $('#delegate-table').DataTable({
		destroy : true,
		ajax : "delegates",
		"lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "Tất cả"]],
		rowId : "id",
		columns: [
		   { data : "ordinal" },
		   { data : "name" },
		   { data : "placeOfBirth" },
		   { data : "dateOfBirth" },
		   { data : "gender" },
		   { data : "unitName" },
		   { data : "position" },
		   { data : "typeName" },
		   { data : "note" },
		   
		   { data : "codeImage" },
		   { data : "codeContent", "visible": false}
		 ],
		 
		 "columnDefs": [{
		    "render" : function(data, type, row) {
		       return "<img class='img-thumbnail' src='../img/" + data + "' height='50' width='50'>";
		     },
		     "targets" : 9
		 }]
		
		
	});
};