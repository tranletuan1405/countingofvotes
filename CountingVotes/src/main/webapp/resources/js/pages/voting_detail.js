/**
 * 
 */

$(document).ready(function () {

	var candidate_table;
	loadCandidateTable();
	initCountingRule();
	initSelectCandidateModal();
});

/* Counting Rule */
function initCountingRule(){
	
	var minPercentVal = $('#min-percent').val();
	var maxSelectedVal = $('#max-selected').attr('max');
	var curSelectedVal = $('#max-selected').val();
	
	$('#slider-min-percent').slider({
	    orientation: "horizontal",
	    range: "min",
	    min: 0,
	    max: 100,
	    value: minPercentVal,
	    slide: function (event, ui) {
	        $("#min-percent").val(ui.value);
	    }
	});
	
	$('#slider-max-selected').slider({
	    orientation: "horizontal",
	    range: "min",
	    min: 0,
	    max: maxSelectedVal,
	    value: curSelectedVal,
	    slide: function (event, ui) {
	        $("#max-selected").val(ui.value);
	    }
	});
	
	$('#min-percent').on('change keyup', function(){
		var val = parseInt($(this).val());
	
		if(val >= 0 && val <= 100){
			$('#slider-min-percent').slider({value : val});
		}
	});
	
	$('#min-percent').on('focusout', function(){
		var val = parseInt($(this).val());
		var curVal = $('#slider-min-percent').slider('value');
		if(!(val >= 0 && val <= 100)){
			$(this).val(curVal);
		}
	});
	
	$('#max-selected').on('change keyup', function(){
		var val = parseInt($(this).val());
	
		if(val >= 0 && val <= maxSelectedVal){
			$('#slider-max-selected').slider({value : val});
		}
	});
	
	$('#max-selected').on('focusout', function(){
		var val = parseInt($(this).val());
		var curVal = $('#slider-max-selected').slider('value');
		if(!(val >= 0 && val <= maxSelectedVal)){
			$(this).val(curVal);
		}
	});
	
	$('#min-percent, #max-selected').on('keypress', function(event){
		if (event.keyCode == 10 || event.keyCode == 13) {
	        event.preventDefault();
	        $(this).blur();
		}
	});
	
	$('#min-percent, #max-selected').on('focus', function(){
		$(this).select();
	})
}

/* Candidate Table */
function loadCandidateTable(){
	var disableClass = $('#max-selected').attr('max') > 0 ? '' : 'disabled';
	
	candidate_table = $('#candidate-table').DataTable({
		destroy : true,
		ajax : "candidates",
		rowId : "id",
		dom : "<'row'<'col-sm-4'B><'col-sm-4'l><'col-sm-4'f>>"
			+ "<'row'<'col-sm-12'tr>>"
			+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",
		buttons : [ {
			text : "Chỉnh sửa",
			className : "btn-warning btn-voting-detail no-radius",
			action : function(){
				loadSelectListCandidates();
			},
		}, {
			text : "Tạo phiếu",
			className : "btn-info btn-voting-detail no-radius " + disableClass,
			action : function(){
				
			},
		}, {
			text : "In phiếu",
			className : "btn-success btn-voting-detail no-radius " + disableClass,
			action : function(){
				
			},
		}],
		columns : [ {
			data : "ordinal",
		}, {
			data : "name",
		}, {
			data : "dateOfBirth",
		}, {
			data : "gender",
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

/* Select Candidates */
function initSelectCandidateModal(){
	
	$('#select-delegates').multiselect({
        search: {
            left: '<input type="text" name="q" class="form-control" placeholder="Tìm..." />',
            right: '<input type="text" name="q" class="form-control" placeholder="Tìm..." />',
        },
        right : "#select-candidates",
        submitAllLeft : false,
        
        /*rightAll: '#select_rightAll',*/
        rightSelected: '#select_rightSelected',
        leftSelected: '#select_leftSelected',
        /*leftAll: '#select_leftAll'*/
    });
	
	$('#btn-select-candidates').on('click', function(){
		$('#btn-select-candidates-real').click();
	});

}

function loadSelectListCandidates(){
	$.ajax({
		url : "get_candidate_selection_list",
		type : "GET",
		success : function(data){
			var delegates = data.delegates;
			var candidates = data.candidates;
			
			$('#select-delegates').empty();
			$('#select-candidates').empty();
			
			$.each(delegates, function (i, item) {
				var option = "<option value='" + item.id
						+ "' style='margin-top : 5px; font-weight : bold;' class='" + item.codeContent + "'>"
						+ item.name + " - " + item.dateOfBirth + ' - '
						+ item.gender + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(' 
						+ item.codeContent + ")</option>";
			    $('#select-delegates').append(option);
			});
			
			$.each(candidates, function (i, item) {
				var option = "<option value='" + item.id
						+ "' style='margin-top : 5px; font-weight : bold;' class='" + item.codeContent + "'>"
						+ item.name + " - " + item.dateOfBirth + ' - '
						+ item.gender + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(' 
						+ item.codeContent + ")</option>";
			    $('#select-candidates').append(option);
			});
			
			showModal('#select-candidates-modal');
		},
		
		error : function(){
			console.log('selection list failed!');
		}
	});
}

/* Create Ballot */
function loadCreateBallotModal(){
	
}