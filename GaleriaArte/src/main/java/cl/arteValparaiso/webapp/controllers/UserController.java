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
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.arteValparaiso.webapp.models.entity.User;
import cl.arteValparaiso.webapp.models.service.IUploadFileService;
import cl.arteValparaiso.webapp.models.service.IUserService;

@Controller
@SessionAttributes("user")
@RequestMapping(value ="/user", method = RequestMethod.GET)
public class UserController {

	@Autowired
	private IUserService userService; 
	
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
	
	@GetMapping("/create")
	public String crear(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("titulo", "Formulario de Cliente");
		return "form/user";
	}
	
	@PostMapping("/save")
	public String guardar(@Valid User user, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
			RedirectAttributes flash, SessionStatus status) {		
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuario");
			return "form/user";
		}
		
		if (!foto.isEmpty()) {

			if (user.getId() != null && user.getId() > 0 && user.getFoto() != null
					&& user.getFoto().length() > 0) {

				uploadFileService.delete(user.getFoto(), "perfil");
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto, "perfil");
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			user.setFoto(uniqueFilename);
		}
		
		String mensajeFlash = (user.getId() != null) ? "Usuario editada con éxito!" : "Usuario creado con éxito!";			
		userService.save(user);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/service";
	}  
		
	@GetMapping(value="/edit/{id}")
	public ModelAndView editar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		User user = null;
		ModelAndView mv = new ModelAndView("form/user");

		if(id > 0) {
			user = userService.findOne(id);
		} else {
			flash.addFlashAttribute("error", "El ID de la usuario no puede ser cero!");
			return new ModelAndView("redirect:/service");
		}
		mv.addObject("user", user);
		mv.addObject("titulo", "Editar Usuario");
		
		return mv;
	}		
		
	@GetMapping(value="/delete/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		
		if(id > 0) {
			userService.delete(id);
		}
		return "redirect:/service";
	}
}
