$(function validate() {
	
	var noticia_validate = {
		rules: {
		      titulo: {
		        required: true
		      },
			  fecha: {
		        required: true
		      },
		      introduccion: {
		        required: true
		      },
		      redaccion: {
		        required: true
		      },
			  conclusion: {
		        required: true
		      },
		  	  file : {
				extension: "jpg|jpeg|png"
			 }
		    },

		messages:{
		      titulo: {
		        required: "Ingrese titulo"
		      },
			  fecha: {
		        required: "Ingrese fecha"
		      },
		      introduccion: {
		        required: "Ingrese Introducción"
		      },
		      redaccion: {
		        required: "Ingrese redacción"
		      },
			  conclusion: {
		        required: "Ingrese conclusión"
		      },
		  	  file : {
				extension: "Ingrese un formato valido (JPG, JPEG o PNG)"
			 }
			}	  
	 	};
		
	 $('#crear_noticia').validate(noticia_validate);

  });
