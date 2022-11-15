package moe.are.task.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@NonNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int id;

	@NonNull
	@Column(nullable = false, length = 255)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
