package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.arteValparaiso.webapp.models.dao.IPerfilDao;
import cl.arteValparaiso.webapp.models.entity.Perfil;

@Service
public class PerfilServiceImpl implements IPerfilService {

	@Autowired
	private IPerfilDao perfilDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Perfil> findAll() {
		return (List<Perfil>) perfilDao.findAll();
	}

	@Override
	@Transactional
	public void save(Perfil perfil) {
		perfilDao.save(perfil);
	}

	@Override
	@Transactional(readOnly = true)
	public Perfil findOne(Long id) {
		return perfilDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		perfilDao.deleteById(id);
	}
}
