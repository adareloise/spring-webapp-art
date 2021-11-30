$(function validate() {
	
	var file_validate = {
		
		rules: {
			file : {
				required: true,
				extension: "jpg|jpeg|png"
				}
			},
		messages:{
		 	file : {
				required: "Ingrese Imagen",
				extension: "Ingrese un formato valido (JPG, JPEG o PNG)"
			 	}
			}	  
	 	};
		
	 $('#upload').validate(file_validate);
  });
			