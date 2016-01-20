var editor; // use a global for the submit and return data rendering in the examples
var table;
$(document).ready(function() {
   /* editor = new $.fn.dataTable.Editor( {
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
    } );*/
 
    table = $('#example').DataTable( {
        
        ajax: "test",
        "columnDefs": [{ "searchable": false, "targets": 0 }],
        "order": [[ 1, 'asc' ]],
        columns: [
            { data : "id"},
            { data: "name" },
            { data: "age" },
            { data: "position" },

        ],
        select: 'single'
  
    } );
/*
    $('#example').on( 'click', 'tbody td:not(:first-child)', function (e) {
        editor.inline( this );
    } );*/
   

	// For demo to fit into DataTables site builder...
	$('#example')
		.removeClass( 'display' )
		.addClass('table table-striped table-bordered');

	
    // Display the buttons
    
} );