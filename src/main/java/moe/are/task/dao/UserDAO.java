package moe.are.task.dao;

import moe.are.task.error.UserLoginExistsError;
import moe.are.task.error.UserNotFoundError;
import moe.are.task.model.User;

import java.util.List;

public interface UserDAO {

	List<User> list();
	User getByLogin(String login) throws UserNotFoundError;
	void removeByLogin(String login) throws UserNotFoundError;
	void add(User user) throws UserLoginExistsError;
	void updateByLogin(String login, User user) throws UserNotFoundError;
}
