package moe.are.task.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
	@Id
	@NonNull
	@Column(nullable = false, unique = true, length = 255)
	private String login;

	@NonNull
	@Column(nullable = false, length = 255)
	private String password;

	@NonNull
	@Column(nullable = false, length = 255)
	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "roles_of_users",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "role_id") }
	)
	private Set<Role> roles;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", roles=" + roles + "]";
	}
}
