 package cl.arteValparaiso.webapp.models.dao;

import org.springframework.data.repository.CrudRepository;

import cl.arteValparaiso.webapp.models.entity.User;

public interface IUserDao extends CrudRepository <User, Long>{

}
