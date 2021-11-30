package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cl.arteValparaiso.webapp.models.entity.Obra;

public interface IObraService {
	
	public List<Obra> findAll();
	
	public Page<Obra> findAll(Pageable peageble);

	public void save(Obra obra);
	
	public Obra findOne(Long id);
	
	public void delete(Long id);
	
}
