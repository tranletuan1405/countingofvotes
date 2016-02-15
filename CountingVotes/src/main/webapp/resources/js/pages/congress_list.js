
$(document).ready(function() {
	var table;
	var delegate_table;
	/*$('#congress-table tfoot th').each( function () {
	  var title = $(this).text();
	  	$(this).html("<input type='text' class='form-control' placeholder='" + title + "'/>" );
	});
	  */
	

	table = $('#congress-table').DataTable( {
        ajax: "congress_table",
        "lengthMenu": [[5, 10, 15, -1], [5, 10, 15, "Tất cả"]],
        "order": [[ 0, "desc" ]],
        "columnDefs": [
        {
			"render" : function(data, type, row) {
				return "<a class='btn btn-default' href='view/" + data + "'>Chi tiết</a>";
			},
			"targets" : 7
		},
		{
			"render" : function(data, type, row) {
				return "<a class='btn btn-default' href='remove/" + data + "'>Xóa</a>";
			},
			"targets" : 8
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
            { data : "id", "searchable" : false, "orderable": false}
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
				//if(delegate_table != null) delegate_table.destroy();
				delegate_table = $('#delegate-table').DataTable({
					destroy : true,
		    		ajax : "delegates_table",
		    		//rowId : "id",
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
		    		   { data : "unitName" },
		    		   { data : "position" },
		    		   { data : "typeName" },
		    		   { data : "note" }
		    		],
		    		
		    		select: 'single',
		    		fixedColumns: false,
		    		"lengthMenu": [[10, 20, 30, -1], [10, 20, 30, "Tất cả"]],
		    		"initComplete": function(settings, json) {
		    			$('#process-modal').modal('hide');
						$('#modal-delegate-table').modal('show');
		    		  }
		    	});
			
			},
			error : function(){
				console.log("Error");
				$('#process-modal').modal('hide');
			}
		});	
    };
    
    $('#modal-delegate-table').on('hidden.bs.modal', function (e) {
    	 $('#modal-delegate-table .modal-body').removeClass('modal-body-show-table');
    });
    
    $('#form-create-congress').submit(function(){
    	showModal('#process-modal');
    });
});