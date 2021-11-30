package cl.arteValparaiso.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value ="/contact", method = RequestMethod.GET)
public class ContactController {
	
	@GetMapping("/info")
	public String contact(Model model) {
		model.addAttribute("titulo", "Contacto");
		return "form/contact_info";
	}
	
	@GetMapping("/obra/{id}")
	public String obra() {
		return "form/contact_info";
	}
}
