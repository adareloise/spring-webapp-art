$(document).ready( function () {
   $('#table_id').DataTable();
} );


var elems = document.getElementsByClassName('confirmation');
var confirmIt = function (e) {
    if (!confirm('¿Esta seguro de eliminar?')) e.preventDefault();
};

for (var i = 0, l = elems.length; i < l; i++) {
	elems[i].addEventListener('click', confirmIt, false);
}
