package cl.arteValparaiso.webapp.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.arteValparaiso.webapp.models.entity.Obra;

public interface IObraDao extends PagingAndSortingRepository<Obra, Long>{

}
