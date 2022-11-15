package moe.are.task.dao;

import moe.are.task.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<User> list() {
		List<User> users = getSession().createQuery("from users").list();
		return users;
	}

	public User getByLogin(String login) {
		User user = getSession().load(User.class, login);
		return user;
	}

	public void removeByLogin(String login) {
		User user = getSession().load(User.class, login);
		if (user != null) {
			getSession().remove(user);
		}
	}

	public void add(User user) {
		getSession().persist(user);
	}

	public void update(User user) {
		getSession().update(user);
	}
}
