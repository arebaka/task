package moe.are.task.service;

import moe.are.task.error.InvalidUserDataError;
import moe.are.task.error.UserLoginExistsError;
import moe.are.task.error.UserNotFoundError;
import moe.are.task.model.User;

import java.util.List;

public interface UserService {

	List<User> listAllUsers();
	User getUserByLogin(String login) throws UserNotFoundError;
	void removeUserByLogin(String login) throws UserNotFoundError;
	void addUser(User user) throws UserLoginExistsError;
	void updateUser(String login, User user) throws InvalidUserDataError, UserNotFoundError;
}
