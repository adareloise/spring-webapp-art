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
			file: {
				extension: "jpg|jpeg|png"
			}
		},

		messages: {
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
			file: {
				extension: "Ingrese un formato valido (JPG, JPEG o PNG)"
			}
		}
	};

	$('#crear_noticia').validate(noticia_validate);

});

function getId(link_video) {

	var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
	var match = link_video.match(regExp);

	if (match && match[2].length == 11) {
		return match[2];
	} else {
		return 'error';
	}
}

var videoId = $('#link_video').val();
var myId = getId(videoId);
$('#myVideo').html('<iframe src="//www.youtube.com/embed/' + myId + '" frameborder="0" allowfullscreen></iframe>');
