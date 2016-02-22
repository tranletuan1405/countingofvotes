$(document).ready(function() {

	$('.form-datetime').datetimepicker({
		format : "dd/mm/yyyy - hh:ii",
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1
	});

});

function showModal(target) {
	var modal = $(target).modal({
		keyboard : false,
		backdrop : 'static'
	});
};

function removeInput(input) {
	var inp = $(input).val('');
}

function loadEnterEvent() {
	$('.dataTables_filter>label>input').keypress(function(event) {
		var code = event.keyCode || event.which;
		if (code == 13) {
			event.preventDefault();
			$(this).select();
		}
	});
	
	$('.dataTables_filter>label>input').focus(function(){
		$(this).select();
	});
}
