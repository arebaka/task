package moe.are.task.service;

import moe.are.task.model.User;

import java.util.List;

public interface UserService {

	List<User> listAllUsers();
	User getUserByLogin(String login);
	void removeUserByLogin(String login);
	void addUser(User user);
	void updateUser(User user);
	void editUser(String login, User user);
}
