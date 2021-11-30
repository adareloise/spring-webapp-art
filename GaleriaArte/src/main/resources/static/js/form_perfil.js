$(function validate() {
	
	var perfil_validate = {
		
		rules: {
		      nombre: {
		        required: true
		      },
		      descripcion1: {
		        required: true
		      },
		      descripcion2: {
		        required: true
		      },
			  descripcion3: {
		        required: true
		      },
			  descripcion4: {
		        required: true
		      },
			  file : {
			  extension: "jpg|jpeg|png"
			}
		    },

		messages:{
		      nombre: {
		        required: "Ingrese nombre "
		      },
		     descripcion1: {
		        required: "Ingrese descripción 1"
		      },
		      descripcion2: {
		        required: "Ingrese descripción 2"
		      },
			  descripcion3: {
		        required: "Ingrese descripción 3"
		      },
			  descripcion4: {
		        required: "Ingrese descripción 4"
		      },
			  file : {
			  extension: "Ingrese un formato valido (JPG, JPEG o PNG)"
			}
			}	  
	 	};
		
	 $('#editar_perfil').validate(perfil_validate);
  });