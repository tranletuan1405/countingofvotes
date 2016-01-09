var editor; // use a global for the submit and return data rendering in the examples
 
$(document).ready(function() {
    /*editor = new $.fn.dataTable.Editor( {
        ajax: "test",
        table: "#example",
        fields: [ {
                label: "First name:",
                name: "name"
            }, {
                label: "Last name:",
                name: "age"
            }, {
                label: "Position:",
                name: "position"
            }
        ]
    } );*/
 
    var t = $('#example').DataTable( {
        
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
            { data: "name" },
            { data: "age" },
            { data: "position" },

        ],
        select: true
  
    } );

    t.on( 'order.dt search.dt', function () {
        t.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
            cell.innerHTML = i+1;
        } );
    } ).draw();


	// For demo to fit into DataTables site builder...
	$('#example')
		.removeClass( 'display' )
		.addClass('table table-striped table-bordered');
 
    // Display the buttons
    
} );