package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.arteValparaiso.webapp.models.dao.INewDao;
import cl.arteValparaiso.webapp.models.entity.Noticia;

@Service
public class NewServiceImpl implements INewService {

	@Autowired
	private INewDao newDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Noticia> findAll() {
		return (List<Noticia>) newDao.findAll();
	}

	@Override
	@Transactional
	public void save(Noticia n) {
		newDao.save(n);
	}

	@Override
	@Transactional(readOnly = true)
	public Noticia findOne(Long id) {
		return newDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		newDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Noticia> findAll(Pageable peageble) {
		return newDao.findAll(peageble);
	}
}
