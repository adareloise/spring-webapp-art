package cl.arteValparaiso.webapp.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.arteValparaiso.webapp.models.entity.Perfil;
import cl.arteValparaiso.webapp.models.service.IPerfilService;
import cl.arteValparaiso.webapp.models.service.IUploadFileService;

@Controller
@SessionAttributes("perfil")
@RequestMapping(value ="/perfil", method = RequestMethod.GET)
public class PerfilController {
	
	@Autowired
	private IPerfilService perfilServ; 

	@Autowired 
	private IUploadFileService uploadFileService;
	
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		
		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename, "perfil");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@PostMapping("/save")
	public String guardar(@Validated  Perfil perfil, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de usuarios");
			return "serv/user_edit";
		}
		
		if (!foto.isEmpty()) {

			if (perfil.getId() != null && perfil.getId() > 0 && perfil.getFoto() != null
					&& perfil.getFoto().length() > 0) {

				uploadFileService.delete(perfil.getFoto(), "perfil");
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto, "perfil");
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			perfil.setFoto(uniqueFilename);
		}
		
		perfilServ.save(perfil);
		status.setComplete();
		flash.addFlashAttribute("success", "Perfil editado correctamente");
		return "redirect:/service";
	}
	
	@GetMapping(value="/edit/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model) {
		
		Perfil perfil = null;
		
		if(id > 0) {
			perfil = perfilServ.findOne(id);
		} else {
			return "redirect:/service";
		}
		
		model.addAttribute("perfil", perfil);
		model.addAttribute("titulo", "Editar Perfil");
		
		return "serv/perfil_editar";
	}	
}
