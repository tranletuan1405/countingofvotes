
$(document).ready(function() {

	/*$('#congress-table tfoot th').each( function () {
	  var title = $(this).text();
	  	$(this).html("<input type='text' class='form-control' placeholder='" + title + "'/>" );
	});
	  */
	
    var table = $('#congress-table').DataTable( {
        ajax: "congress_table",
        "order": [[ 0, "desc" ]],
        "columnDefs": [{
			"render" : function(data, type, row) {
				return "<a class='btn btn-default' href='id/" + data + "'>Chi tiết</a>";
			},
			"targets" : 7
		} ],
        columns: [
            { data : "id", "searchable" : false},
            { data: "name" },
            { data: "totalUnit" },
            { data: "totalDelegate" },
            { data: "totalVoting" },
            { data: "startTime" },
            { data: "endTime" },
            { data : "id", "searchable" : false, "orderable": false},
        ],
        select: 'single'
    } );
    
    
    var file = $('#delegates-file').on('change', prepareUpload);
    
    function prepareUpload(event) {
    	var file = event.target.files[0];
    	if(file == null) return;
    	showModal('#process-modal');
    	
    	var data = new FormData();
    	data.append("delegatesFile", file);
  
    	$.ajax(
		{
			url : "checking_delegates_file",
			type : "POST",
			data : data,
			contentType: false,
	        processData: false,
	        cache: false,

			success : function(data){
				console.log(data);
				var delegate_table = $('#delegate-table').DataTable({
		    		ajax : "delegates_table",
		    		"order": [[ 0, "desc" ]],
		    		rowId : "id",
		    		columns: [
		    		   { data : "ordinal" },
		    		   { data : "name" },
		    		   { data : "placeOfBirth" },
		    		   { data : "address" },
		    		   { data : "dateOfBirth" },
		    		   { data : "gender" },
		    		   { data : "ethnic" },
		    		   { data : "religion" },
		    		   { data : "dateOfYouthUnion" },
		    		   { data : "dateOfPartyPreparatory" },
		    		   { data : "dateOfPartyOfficial" },
		    		   { data : "unit.shortName" },
		    		   { data : "position" },
		    		   { data : "type.shortName" },
		    		   { data : "note" }
		    		],
		    		
		    		select: 'single'
		    	});
				
				$('#process-modal').modal('toggle');
				$('#modal-delegate-table').modal('show');
			},
			error : function(){
				console.log("Error");
				$('#process-modal').modal('toggle');
			}
		});	
    };
});