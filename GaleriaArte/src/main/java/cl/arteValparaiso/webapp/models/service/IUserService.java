package cl.arteValparaiso.webapp.models.service;

import java.util.List;

import cl.arteValparaiso.webapp.models.entity.User;

public interface IUserService {

	public List<User> findAll();

	public void save(User user);
	
	public User findOne(Long id);
	
	public void delete(Long id);
}
