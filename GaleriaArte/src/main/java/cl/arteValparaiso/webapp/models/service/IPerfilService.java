package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import cl.arteValparaiso.webapp.models.entity.Perfil;


public interface IPerfilService {
	
	public List<Perfil> findAll();

	public void save(Perfil perfil);
	
	public Perfil findOne(Long id);
	
	public void delete(Long id);

}
