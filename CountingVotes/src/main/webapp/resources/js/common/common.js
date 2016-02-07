$(document).ready(function() {

	 $('.form-datetime').datetimepicker({
		format: "dd / mm / yyyy - hh:ii",
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1
	});
	
});

function showDialog(id) {
	var dialog = $(id).data('dialog');
	dialog.open();
};

