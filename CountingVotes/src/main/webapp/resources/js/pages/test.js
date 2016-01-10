var editor; // use a global for the submit and return data rendering in the examples
var table;
$(document).ready(function() {
    editor = new $.fn.dataTable.Editor( {
    	table: "#example",
  		ajax : "update/_id_",
        idSrc : "id",
        fields: [
        	{
                label: "Name:",
                name: "name"
            },

            {
                label: "Age:",
                name: "age"
            },

        	{
                label: "Position:",
                name: "position"
            }
        ]
    } );
 
    table = $('#example').DataTable( {
        
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        ajax: "test",
		
         "columnDefs": [ {
            "searchable": false,
            "orderable": false,
            "targets": 0
        } ],
        "order": [[ 1, 'asc' ]],
        columns: [
        	{ data: null },
        	{ data: "id" },
            { data: "name" },
            { data: "age" },
            { data: "position" },

        ],
        select: 'single',
        dom: 'B<"clear">lfrtip',
        buttons: [
            { extend: "create", editor: editor },
            { extend: "edit",   editor: editor },
            { extend: "remove", editor: editor }
        ]
  
    } );

    table.on( 'order.dt search.dt', function () {
        table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        } );
    } ).draw();

   

	// For demo to fit into DataTables site builder...
	$('#example')
		.removeClass( 'display' )
		.addClass('table table-striped table-bordered');

	
    // Display the buttons
    
} );