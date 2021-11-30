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
import org.springframework.web.bind.support.SessionStatus;

import cl.arteValparaiso.webapp.models.entity.User;
import cl.arteValparaiso.webapp.models.service.IUserService;

@Controller
@RequestMapping(value ="/users", method = RequestMethod.GET)
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
		return "user_create";
	}
	
	@PostMapping("/save")
	public String guardar(@Validated  User user, BindingResult result, Model model, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Cliente");
			return "user_form";
		}
		
		userService.save(user);
		status.setComplete();
		return "redirect:/users/listar";
	}
	
	@GetMapping(value="/edit/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model) {
		
		User user = null;
		
		if(id > 0) {
			user = userService.findOne(id);
		} else {
			return "redirect:/users/listar";
		}
		model.addAttribute("user", user);
		model.addAttribute("titulo", "Formulario de Usuario");
		
		return "user_form";
	}
		
	@GetMapping(value="/delete/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		
		if(id > 0) {
			userService.delete(id);
		}
		return "redirect:/users/listar";
	}
}
