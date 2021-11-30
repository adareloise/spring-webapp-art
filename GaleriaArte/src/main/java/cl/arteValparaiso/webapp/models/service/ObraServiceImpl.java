package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.arteValparaiso.webapp.models.dao.IObraDao;
import cl.arteValparaiso.webapp.models.entity.Obra;

@Service
public class ObraServiceImpl implements IObraService {
	
	@Autowired
	private IObraDao obraDao; 
	
	@Override
	@Transactional(readOnly = true)
	public List<Obra> findAll() {
		return (List<Obra>) obraDao.findAll();
	}

	@Override
	@Transactional
	public void save(Obra obra) {
		obraDao.save(obra);
	}

	@Override
	@Transactional(readOnly = true)
	public Obra findOne(Long id) {
		return obraDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		obraDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Obra> findAll(Pageable peageble) {
		return obraDao.findAll(peageble);
	}
}
