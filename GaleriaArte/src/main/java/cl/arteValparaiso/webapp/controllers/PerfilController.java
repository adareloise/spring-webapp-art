package cl.arteValparaiso.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.arteValparaiso.webapp.models.entity.Perfil;
import cl.arteValparaiso.webapp.models.service.IPerfilService;

@Controller
@SessionAttributes("perfil")
@RequestMapping(value ="/perfil", method = RequestMethod.GET)
public class PerfilController {
	
	@Autowired
	private IPerfilService perfilServ; 
	
	@GetMapping("/listar") 
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Idiomas");
		model.addAttribute("perfiles", perfilServ.findAll());
		return "serv/listar_perfiles";
	}
	
	@PostMapping("/save")
	public String guardar(@Validated  Perfil perfil, BindingResult result, Model model, 
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de usuarios");
			return "object/perfil";
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
		
		return "form/perfil";
	}	
}
