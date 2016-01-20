
$(document).ready(function() {

	
	var editor; // use a global for the submit and return data rendering in the examples
	var table;
	
	$(".panel").panel();

		
	$("#datepicker").datetimepicker({
		format : "dd MM yyyy - hh:ii",
		linkFormat : "yyyy-mm-dd hh:ii",
		initialDate : new Date()
	});

	
});