$(function validate() {
	
	var contact_validate = {
		
		rules: {
		      name: {
		        required: true
		      },
		      emailAddress: {
		        required: true,
  		      	email: true			
		      },
			  telephone: {
		        required: true
		      },
		      mensaje: {
		        required: true
		      }
		    },

		messages:{
		      name: {
		        required: "Ingrese nombre"
		      },
		      emailAddress: {
		        required: "Ingrese apellidos",
				email: "Ingrese un email valido"
		      },
			  telephone: {
		        required: "Ingrese telefono"
		      },
		      mensaje: {
		        required: "Ingrese mensaje"
		      },
			}	  
	 	};
		
	 $('#contact').validate(contact_validate);

  });