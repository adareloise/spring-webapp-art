package cl.arteValparaiso.webapp.controllers;

import java.io.IOException; 
import java.net.MalformedURLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.arteValparaiso.webapp.models.entity.Obra;
import cl.arteValparaiso.webapp.models.service.IObraService;
import cl.arteValparaiso.webapp.models.service.IUploadFileService;

@Controller
@SessionAttributes("obra")
@RequestMapping(value ="/gallery", method = RequestMethod.GET)
public class GalleryController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IObraService obraServ;
	@Autowired 
	private IUploadFileService uploadFileService;
		
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		
		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename, "obra");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "/object/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Obra obra = obraServ.findOne(id);
		if (obra == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/gallery/listar";
		}

		model.put("obra", obra);
		model.put("titulo", "Detalle obra: " + obra.getTitulo());
		return "object/obra";
	}
	
	@GetMapping("/create")
	public String crear(Model model){
		Obra obra = new Obra();
		model.addAttribute("obra", obra);
		model.addAttribute("titulo", "Crear Obra");
		return "form/obra";
	}
	
	@PostMapping("/save")
	public String guardar(@Validated Obra obra, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
			RedirectAttributes flash, SessionStatus status) {		
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Crear de Obra");
			return "redirect:/gallery/listar";
		}
		
		if (!foto.isEmpty()) {

			if (obra.getId() != null && obra.getId() > 0 && obra.getFoto() != null
					&& obra.getFoto().length() > 0) {

				uploadFileService.delete(obra.getFoto(), "obra");
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto, "obra");
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			obra.setFoto(uniqueFilename);
		}
		
		String mensajeFlash = (obra.getId() != null) ? "Obra editada con éxito!" : "Obra creada con éxito!";			
		obraServ.save(obra);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/gallery/listar";
	}  
			
	@GetMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		Obra obra = null;
		ModelAndView mv = new ModelAndView("form/obra");

		if(id > 0) {
			obra = obraServ.findOne(id);
		} else {
			flash.addFlashAttribute("error", "El ID de la obra no puede ser cero!");
			return new ModelAndView("redirect:/gallery/listar");
		}
		mv.addObject("obra", obra);
		mv.addObject("titulo", "Editar obra");
		
		return mv;
	}
		
	@GetMapping("/listar")
	public String listar(Model model, Authentication authentication) {
		
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		model.addAttribute("titulo", "Listado de Obras");
		model.addAttribute("obras", obraServ.findAll());
		return "serv/listar_obras";
	}	
	
	@GetMapping(value="/delete/{id}")
	public String eliminar(@PathVariable(value="id") Long id, RedirectAttributes flash) {		
		if(id > 0) {
			Obra obra = obraServ.findOne(id);
			
			obraServ.delete(id);
			flash.addFlashAttribute("success", "Obra eliminada con éxito!");

			if (uploadFileService.delete(obra.getFoto(), "obra")) {
				flash.addFlashAttribute("info", "Foto " + obra.getFoto() + " eliminada con exito!");
			}
		}
		return "redirect:/gallery/listar";
	}
}


