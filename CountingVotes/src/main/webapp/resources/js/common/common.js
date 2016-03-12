$(document).ready(function() {

	$('.form-datetime').datetimepicker({
		format : "dd/mm/yyyy - hh:ii",
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1,
		
	});
	
	
});

function getDateTimeNow() {
	var now = new Date();

	var day = ("0" + now.getDate()).slice(-2);
	var month = ("0" + (now.getMonth() + 1)).slice(-2);

	var today = (day) + "/" + (month) + "/" + now.getFullYear() + " - " + now.getHours() + ":" + now.getMinutes();

	return today;
}

function makeRect(target, ratio){
	var cw = parseInt($(target).width());
	var ch = parseInt((cw * ratio)) + 'px';
	$(target).css('height', ch);
}

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

function focusSearchInput(){
	$('.dataTables_filter>label>input').focus();
}
