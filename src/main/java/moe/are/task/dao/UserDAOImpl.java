package moe.are.task.dao;

import moe.are.task.error.UserLoginExistsError;
import moe.are.task.error.UserNotFoundError;
import moe.are.task.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<User> list() {
		Session session = sessionFactory.getCurrentSession();
		List<User> users = session.createQuery("from users").list();
		return users;
	}

	public User getByLogin(String login) throws UserNotFoundError {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, login);
		if (user == null)
			throw new UserNotFoundError();
		return user;
	}

	public void removeByLogin(String login) throws UserNotFoundError {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, login);
		if (user == null)
			throw new UserNotFoundError();
		session.remove(user);
	}

	public void add(User user) throws UserLoginExistsError {
		Session session = sessionFactory.getCurrentSession();
		User entry = session.get(User.class, user.getLogin());
		if (entry != null)
			throw new UserLoginExistsError();
		session.persist(user);
	}

	public void updateByLogin(String login, User user) throws UserNotFoundError {
		Session session = sessionFactory.getCurrentSession();
		User entry = session.get(User.class, login);
		if (entry == null)
			throw new UserNotFoundError();
		session.remove(entry);
		session.persist(user);
	}
}
