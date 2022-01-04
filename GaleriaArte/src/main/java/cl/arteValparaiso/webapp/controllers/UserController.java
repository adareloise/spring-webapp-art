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

import cl.arteValparaiso.webapp.models.entity.User;
import cl.arteValparaiso.webapp.models.service.IUserService;

@Controller
@SessionAttributes("user")
@RequestMapping(value ="/user", method = RequestMethod.GET)
public class UserController {

	@Autowired
	private IUserService userService; 
	
	@GetMapping("/listar") 
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", userService.findAll());
		return "serv/user_listar";
	}
	
	@GetMapping("/create")
	public String crear(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("titulo", "Formulario de Cliente");
		return "form/user";
	}
	
	@PostMapping("/save")
	public String guardar(@Validated  User user, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuario");
			return "form/user";
		}
		
		userService.save(user);
		flash.addFlashAttribute("success", "Usuario editado correctamente");
		status.setComplete();
		return "redirect:/service";
	}
	
	@GetMapping(value="/edit/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model) {
		
		User user = null;
		
		if(id > 0) {
			user = userService.findOne(id);
		} else {
			return "redirect:/service";
		}
		model.addAttribute("user", user);
		model.addAttribute("titulo", "Formulario de Usuario");
		
		return "form/user";
	}
		
	@GetMapping(value="/delete/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		
		if(id > 0) {
			userService.delete(id);
		}
		return "redirect:/service";
	}
}
