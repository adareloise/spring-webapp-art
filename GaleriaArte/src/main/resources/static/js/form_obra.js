$(function validate() {

	var obra_validate = {

		rules: {
			titulo: {
				required: true
			},
			dimension: {
				required: true
			},
			tecnica: {
				required: true
			},
			disponibilidad: {
				required: true
			},
			file: {
				extension: "jpg|jpeg|png"
			},
			posicion: {
				required: true
			}
		},

		messages: {
			titulo: {
				required: "Ingrese titulo"
			},
			dimension: {
				required: "Ingrese dimensión"
			},
			tecnica: {
				required: "Ingrese tecnica"
			},
			disponibilidad: {
				required: "Ingrese disponibilidad"
			},
			file: {
				extension: "Ingrese un formato valido (JPG, JPEG o PNG)"
			},
			posicion: {
				required: "Ingrese posicion"
			}
		}
	};

	$('#crear_obra').validate(obra_validate);
});