package cl.arteValparaiso.webapp.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import cl.arteValparaiso.webapp.models.entity.Noticia;
import cl.arteValparaiso.webapp.models.service.INewService;
import cl.arteValparaiso.webapp.models.service.IUploadFileService;
import cl.arteValparaiso.webapp.util.paginator.PageRender;

@Controller
@SessionAttributes("noticia")
@RequestMapping(value = "/news", method = RequestMethod.GET)
public class NewsController {

	@Autowired
	private INewService newServ;
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename, "noticia");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@GetMapping(value = "/object/{id}")
	public String ver(@RequestParam(name="page", defaultValue="0") int page, @PathVariable(value = "id") Long id, 
			Map<String, Object> model, RedirectAttributes flash) {

		Noticia noticia = newServ.findOne(id);
				
		if (noticia == null) {
			flash.addFlashAttribute("error", "La noticia no existe en la base de datos");
			return "redirect:/news/listar";
		}

		Pageable pageRequest = PageRequest.of(page, 3);
		Page<Noticia> noticias = newServ.findAll(pageRequest);
		
		PageRender<Noticia> pageRender =  new PageRender<Noticia>("/object/{id}", noticias);	
		
		model.put("noticia", noticia);
		model.put("titulo", noticia.getTitulo());
		model.put("noticias", noticias);
		model.put("page", pageRender);
		
		return "object/noticia";
	}

	@GetMapping("/create")
	public String crear(Model model) {
		Noticia noticia = new Noticia();
		model.addAttribute("noticia", noticia);
		model.addAttribute("titulo", "Crear Noticia");
		return "form/noticia";
	}

	@PostMapping("/save")
	public String guardar(@Validated Noticia noticia, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear de noticia");
			return "redirect:/news/listar";
		}

		if (!foto.isEmpty()) {

			if (noticia.getId() != null && noticia.getId() > 0 && noticia.getFoto() != null
					&& noticia.getFoto().length() > 0) {

				uploadFileService.delete(noticia.getFoto(), "noticia");
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto, "noticia");
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");

			noticia.setFoto(uniqueFilename);
		}
		String mensajeFlash = (noticia.getId() != null) ? "Noticia editada con éxito!" : "Noticia creada con éxito!";
		newServ.save(noticia);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:/news/listar";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Noticia noticia = null;
		ModelAndView mv = new ModelAndView("form/noticia");

		if (id > 0) {
			noticia = newServ.findOne(id);
		} else {
			flash.addFlashAttribute("error", "El ID de la noticia no puede ser cero!");
			return new ModelAndView("redirect:/news/listar");
		}
		mv.addObject("noticia", noticia);
		mv.addObject("titulo", "Editar noticia");

		return mv;
	}

	@GetMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de Noticias");
		model.addAttribute("noticias", newServ.findAll());
		return "serv/listar_noticias";
	}

	@GetMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Noticia noticia = newServ.findOne(id);
			newServ.delete(id);
			flash.addFlashAttribute("success", "Noticia eliminada con éxito!");
		
			if (uploadFileService.delete(noticia.getFoto(), "noticia")) {
				flash.addFlashAttribute("info", "Foto " + noticia.getFoto() + " eliminada con exito!");
			}
		}
		return "redirect:/news/listar";
	}
}
