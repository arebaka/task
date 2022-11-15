package moe.are.task.dao;

import moe.are.task.model.User;

import java.util.List;

public interface UserDAO {

	List<User> list();
	User getByLogin(String login);
	void removeByLogin(String login);
	void add(User user);
	void update(User user);
}
