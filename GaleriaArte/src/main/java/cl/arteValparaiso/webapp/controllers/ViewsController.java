package cl.arteValparaiso.webapp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.arteValparaiso.webapp.models.entity.Noticia;
import cl.arteValparaiso.webapp.models.entity.Obra;
import cl.arteValparaiso.webapp.models.entity.Perfil;
import cl.arteValparaiso.webapp.models.entity.User;
import cl.arteValparaiso.webapp.models.service.INewService;
import cl.arteValparaiso.webapp.models.service.IObraService;
import cl.arteValparaiso.webapp.models.service.IPerfilService;
import cl.arteValparaiso.webapp.models.service.IUserService;
import cl.arteValparaiso.webapp.util.paginator.PageRender;

@Controller
public class ViewsController {

	@Autowired
	private INewService newServ;
	@Autowired
	private IObraService obraServ;
	@Autowired
	private IPerfilService perfilServ; 
	@Autowired
	private IUserService userServ; 
	
	@RequestMapping(value = "/biografia", method = RequestMethod.GET)
	public String biografia(Map<String, Object> model) {
	
		Long id = (long) 1;
		Perfil perfil = perfilServ.findOne(id);
		User user = userServ.findOne(id);
		model.put("titulo", "Perfil");
		model.put("perfil", perfil);
		model.put("user", user);
		
		return "view/biography";
	}	
	
	@RequestMapping(value = "/noticias", method=RequestMethod.GET)
	public String blog(@RequestParam(name="page", defaultValue="0") int page, Map<String, Object> model) {
			
		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Noticia> noticias = newServ.findAll(pageRequest);
		
		List<Noticia> list = newServ.findAll();
		Noticia noticia = list.get(list.size() - 1);
		
				
		PageRender<Noticia> pageRender =  new PageRender<Noticia>("/noticias", noticias);	
		
		model.put("noticia", noticia);
		model.put("titulo", "Noticias");
		model.put("noticias", noticias);
		model.put("page", pageRender);
		
		return "view/news";
	}
	
	@RequestMapping(value = "/noticias/{id}", method=RequestMethod.GET)
	public String blogId(@RequestParam(name="page", defaultValue="0") int page,@PathVariable(value = "id") Long id, Map<String, Object> model) {
			
		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Noticia> noticias = newServ.findAll(pageRequest);
		Noticia noticia = newServ.findOne(id);
						
		PageRender<Noticia> pageRender =  new PageRender<Noticia>("/noticias", noticias);	
		
		model.put("noticia", noticia);
		model.put("titulo", "Noticias");
		model.put("noticias", noticias);
		model.put("page", pageRender);
		
		return "view/news";
	}
	
	@RequestMapping(value = "/galeria", method=RequestMethod.GET)
	public String galeria(@RequestParam(name="page", defaultValue="0") int page, Map<String, Object> model) {
		
		Pageable pageRequest = PageRequest.of(page, 9);
		Page<Obra> obras = obraServ.findAll(pageRequest);
		
		PageRender<Obra> pageRender =  new PageRender<Obra>("/galeria", obras);				
		model.put("titulo", "Galeria");
		model.put("obras", obras);
		model.put("page", pageRender);
		
		return "view/gallery";
	}
}
