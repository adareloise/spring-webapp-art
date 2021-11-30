package cl.arteValparaiso.webapp.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import cl.arteValparaiso.webapp.models.entity.Noticia;

public interface INewDao extends PagingAndSortingRepository<Noticia, Long>{

}
