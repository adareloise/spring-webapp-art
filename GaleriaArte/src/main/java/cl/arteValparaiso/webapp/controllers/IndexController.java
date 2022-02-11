package cl.arteValparaiso.webapp.controllers;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.arteValparaiso.webapp.models.entity.User;
import cl.arteValparaiso.webapp.models.service.IUserService;

@Controller
public class IndexController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private IUserService userServ; 

	@GetMapping({"/index", "/", "/home"})
	public String index(Map<String, Object> model){
		
		Long idusr = (long) 1;
		User user = userServ.findOne(idusr);
		model.put("user", user);
		
		return "view/index";
	}
	
	@GetMapping("/service")
	public String indexServ(Model model, Authentication authentication){
		

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		return "serv/index";
	} 
}
