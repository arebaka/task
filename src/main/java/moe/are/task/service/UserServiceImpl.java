package moe.are.task.service;

import moe.are.task.dao.UserDAO;
import moe.are.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	public List<User> listAllUsers() {
		List<User> users = userDAO.list();
		return userDAO.list();
	}

	public User getUserByLogin(String login) {
		return userDAO.getByLogin(login);
	}

	public void removeUserByLogin(String login) {
		userDAO.removeByLogin(login);
	}

	public void addUser(User user) {
		userDAO.add(user);
	}

	public void updateUser(User user) {
		userDAO.update(user);
	}

	public void editUser(String login, User user) {
		User u = userDAO.getByLogin(login);

	}
}
