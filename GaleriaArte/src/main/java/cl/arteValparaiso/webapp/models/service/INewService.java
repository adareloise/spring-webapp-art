package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cl.arteValparaiso.webapp.models.entity.Noticia;

public interface INewService {

	public List<Noticia> findAll();
	
	public Page<Noticia> findAll(Pageable peageble);
	
	public void save(Noticia n);
	
	public Noticia findOne(Long id);
	
	public void delete(Long id);
}
