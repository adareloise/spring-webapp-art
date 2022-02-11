package cl.arteValparaiso.webapp.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.arteValparaiso.webapp.models.email.Mail;
import cl.arteValparaiso.webapp.models.entity.User;
import cl.arteValparaiso.webapp.models.service.IUserService;
import cl.arteValparaiso.webapp.models.service.MailService;

@Controller
@SessionAttributes("mail")
@RequestMapping(value ="/contact", method = RequestMethod.GET)
public class ContactController {
	
	@Autowired
	MailService mailService;
	
	@Autowired
	private IUserService userServ; 
	
	@GetMapping("/info")
	public String contact(Model model) {
		Mail mail = new Mail();
		model.addAttribute("mail", mail);
		model.addAttribute("titulo", "Contacto");
		Long id = (long) 1;
		User user = userServ.findOne(id);
		model.addAttribute("user", user);
		
		return "form/contact_info";
	}
	
	@PostMapping("/send")
	public String send(@Validated Mail mail, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {	
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Contacto");
			return "redirect:/contact/info";
		}
		mail.setMailFrom("dutreras369@gmail.com");
        mailService.sendEmail(mail);
		
		String mensajeFlash = "Muchas gracias pronto me contactare contigo";
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/galeria";

	}	
	
	@GetMapping("/obra/{id}")
	public String obra(Map<String, Object> model) {
		Long id = (long) 1;
		User user = userServ.findOne(id);
		model.put("user", user);
			
		return "form/contact_info";
	}
}
