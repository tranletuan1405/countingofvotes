/**
 * 
 */

$(document).ready(function () {

	var candidate_table;
	loadCandidateTable();
	initSlider();
	initSelectCandidateModal();
});

/* Init Layout*/
function initSlider(){
	$('#slider-min-percent').slider({
	    orientation: "horizontal",
	    range: "min",
	    min: 0,
	    max: 100,
	    value: 60,
	    slide: function (event, ui) {
	        $("#min-percent").val(ui.value);
	    }
	});
	
	$('#slider-max-selected').slider({
	    orientation: "horizontal",
	    range: "min",
	    min: 0,
	    max: 100,
	    value: 60,
	    slide: function (event, ui) {
	        $("#max-selected").val(ui.value);
	    }
	});

}

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
				showModal('#select-candidates-modal');
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

/* Select Candidates */
function initSelectCandidateModal(){
	$('#select-search').multiselect({
        search: {
            left: '<input type="text" name="q" class="form-control" placeholder="Tìm..." />',
            right: '<input type="text" name="q" class="form-control" placeholder="Tìm..." />',
        },
        
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