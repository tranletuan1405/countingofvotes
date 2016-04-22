/**
 * 
 */

$(document).ready(function () {

	var candidate_table;
	var create_ballot_table;
	var editor;
	
	initEventModal();
	loadCandidateTable();
	initCountingRule();
	initSelectCandidateModal();
	initCreateCodeModal();
	initPrintBallotModal();
});

function initEventModal(){
	$('.modal').on('show.bs.modal', function(e){
		$('body').css('overflow', 'hidden');
	});
	
	$('.modal').on('hide.bs.modal', function(e){
		$('body').css('overflow', 'auto');
	});
}


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
			$('#btn-submit-rules').removeAttr('disabled');
			$('.btn-edit-ballot').addClass('disabled');
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
			$('#btn-submit-rules').removeAttr('disabled');
			$('.btn-edit-ballot').addClass('disabled');
	    }
	});
	
	$('#min-percent').on('change keyup', function(){
		var val = parseInt($(this).val());
	
		if(val >= 0 && val <= 100){
			$('#slider-min-percent').slider({value : val});
		}
		
		$('#btn-submit-rules').removeAttr('disabled');
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
		
		$('#btn-submit-rules').removeAttr('disabled');
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
		dom : "<'row'<'col-sm-5'B><'col-sm-3'l><'col-sm-4'f>>"
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
			className : "btn-success btn-voting-detail no-radius btn-edit-ballot " + disableClass,
			action : function(){
				$('#btn-create-codes').removeAttr('disabled');
				$('#btn-cancel-create-codes').removeAttr('disabled');
				showModal('#create-codes-modal');
			},
		}, {
			text : "Xuất phiếu",
			className : "btn-info btn-voting-detail no-radius btn-edit-ballot " + disableClass,
			action : function(){
				getPattern();
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
			data : "codeImagePath",
			searchable : false,
			orderable : false,
		}, ],
		"columnDefs": [{
			"render" : function(data, type, row) {
				return "<img class='img-responsive' src='../img/barcode/" + row['codeImagePath'] + "'style='width : 50px; height : 50px;'/>";
			},
			"targets" : 6
		},],
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
        
        rightSelected: '#select_rightSelected',
        leftSelected: '#select_leftSelected',
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
						+ "' style='margin-top : 5px; font-weight : bold;' class='" + item.encode + "'>"
						+ item.name + " - " + item.dateOfBirth + ' - '
						+ item.gender + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(' 
						+ item.encode + ")</option>";
			    $('#select-delegates').append(option);
			});
			
			$.each(candidates, function (i, item) {
				var option = "<option value='" + item.id
						+ "' style='margin-top : 5px; font-weight : bold;' class='" + item.encode + "'>"
						+ item.name + " - " + item.dateOfBirth + ' - '
						+ item.gender + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(' 
						+ item.encode + ")</option>";
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

function initCreateCodeModal(){
	$('#btn-create-codes').on('click', function(){
		$('#btn-cancel-create-codes').attr('disabled', 'disabled');
		$(this).attr('disabled', 'disabled');
		
		var index = 1;
		create_ballot_table = $('#create-ballot-table').DataTable({
			destroy : true,
			paging : false,
			orderMulti : true,
			ajax : "create_ballot",
			rowId : "id",
			columns : [ {
				data : "id",
				orderable : false,
				searchable : false,
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
				data : "countingEncodeImage",
				searchable : false,
				orderable : false,
			},],
			"columnDefs": [{
				"render" : function(data, type, row){
					
					return index++;
				},
				"targets" : 0
			},{
				"render" : function(data, type, row) {
					return "<img class='img-responsive code-pattern' src='../img/barcode/" + data + "'style='width : 50px; height : 50px;'/>";
				},
				"targets" : 6
			},],
			select : false,
			"initComplete": function(settings, json) {
				$('#create-codes-modal').modal('hide');
				showModal('#create-ballot-modal');
				
				$('a.toggle-vis').on( 'click', function (e) {
			        e.preventDefault();
			 
			        // Get the column API object
			        var column = create_ballot_table.column( $(this).attr('data-column') );
			 
			        // Toggle the visibility
			        column.visible( ! column.visible() );
			    } );
				
				editor = CKEDITOR.replace( 'title-editor' );
				
				$('#tab-result').on('show.bs.tab', function (e) {
					  
					//Get title design 
					var title = editor.getData();
					
					//Get table
					var body = $('#create-ballot-table').html();
				
					//Append title & body
					var title_ballot = $('#title-ballot');
					var body_ballot = $('#body-ballot');
					
					title_ballot.empty();
					body_ballot.empty();
					
					title_ballot.append(title);
					body_ballot.append(body);
					body_ballot.find('thead').empty();
					body_ballot.find('tfoot').empty();
					
					$('#btn-create-ballot').removeClass('hidden');
				});
				
				
				$('#tab-create-ballot').on('show.bs.tab', function(e){
					//Append title & body
					var title_ballot = $('#title-ballot');
					var body_ballot = $('#body-ballot');
					
					title_ballot.empty();
					body_ballot.empty();
					$('#btn-create-ballot').addClass('hidden');
				});
				
				$('#btn-create-ballot').on('click', function(){
					savePattern();
				});
				
				$('#slider-code-size').slider({
				    orientation: "horizontal",
				    range: "min",
				    min: 30,
				    max: 120,
				    value: 50,
				    slide: function (event, ui) {
				       var code = $('.code-pattern');
				       code.css('width', ui.value + "px");
				       code.css('height', ui.value + "px");
				    }
				});
			},
		});
	});
	
	
}

function savePattern() {
	
	$('.btn-ballot').attr('disabled', 'disabled');
	
	var patternStr = $('#result-panel').html();
	
	$.ajax({
		url : "save_pattern",
		type : "POST",
		data : {
			pattern : patternStr
		},
		success : function(data) {
			if (data.trim() && data != 'Failed') {
				showPrintBallotModal(data);
				$('#create-ballot-modal').modal('hide');
			}
			
			$('.btn-ballot').removeAttr('disabled');
		},
		error : function(){
			console.log('Save Ballot Pattern Error');
			$('.btn-ballot').removeAttr('disabled');
		}
	});
}

function getPattern(){
	
	$.ajax({
		url : "get_pattern",
		type : "GET",
		success : function(data){
			showPrintBallotModal(data);
		},
		error : function(){
			console.log('Get Ballot Pattern Error');
		}
	});
}

function showPrintBallotModal(data){
	var content = $('#print-ballot-content').empty();
	
	if (data.trim() && data != 'Failed') {
		content.append(data);
		$('#btn-print-ballot').removeAttr('disabled');
	} else {
		var error = "<strong>Bạn chưa tạo mẫu phiếu, vui lòng tạo mẫu phiếu trước khi xuất!</strong>";
		$('#btn-print-ballot').attr('disabled', 'disabled');
		content.append(error);
	}
	
	showModal('#print-ballot-modal');
}


/* Print Ballot */

function initPrintBallotModal(){
	
	$('#btn-print-ballot').on('click', function(){
		
		$('#print-ballot-modal').modal('hide');
		
		$("#print-ballot-content").print({
			globalStyles : true,
			mediaPrint : false,
			stylesheet : null,
			noPrintSelector : ".no-print",
			iframe : true,
			append : null,
			prepend : null,
			manuallyCopyFormValues : true,
			deferred : $.Deferred(),
			timeout : 250,
			title : "Phiếu bầu",
			doctype : '<!doctype html>'
		});
		
	});
}

