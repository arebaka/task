package moe.are.task.service;

import moe.are.task.dao.UserDAO;
import moe.are.task.error.*;
import moe.are.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static List<Throwable> validateUserData(User user) {
		List<Throwable> errors = new ArrayList<>();

		if (user.getName() == "") {
			errors.add(new NameIsEmptyError());
		}
		if (user.getLogin() == "") {
			errors.add(new LoginIsEmptyError());
		}

		String password = user.getPassword();
		String passwordPattern = "(?=.*[A-Z])(?=.*\\d)[A-Z\\d]";
		if (password == "") {
			errors.add(new PasswordIsEmptyError());
		}
		else if (!password.matches(passwordPattern)) {
			errors.add(new PasswordIsInvalid());
		}

		return errors;
	}

	@Autowired
	private UserDAO userDAO;

	public List<User> listAllUsers() {
		List<User> users = userDAO.list();
		return userDAO.list();
	}

	public User getUserByLogin(String login) throws UserNotFoundError {
		return userDAO.getByLogin(login);
	}

	public void removeUserByLogin(String login) throws UserNotFoundError {
		userDAO.removeByLogin(login);
	}

	public void addUser(User user) throws UserLoginExistsError {
		userDAO.add(user);
	}

	public void updateUser(String login, User user) throws InvalidUserDataError, UserNotFoundError {
		List<Throwable> errors = validateUserData(user);
		try {
			userDAO.getByLogin(login);
		}
		catch (UserNotFoundError error) {
			errors.add(error);
		}
		if (!errors.isEmpty())
			throw new InvalidUserDataError(errors);

		userDAO.updateByLogin(login, user);
	}
}
