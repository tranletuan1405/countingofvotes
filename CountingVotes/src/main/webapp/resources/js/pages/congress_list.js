
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
    
  

});