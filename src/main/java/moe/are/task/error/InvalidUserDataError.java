package moe.are.task.error;

import java.util.ArrayList;
import java.util.List;

public class InvalidUserDataError extends Throwable {
	private final List<Throwable> errors;

	public InvalidUserDataError() {
		this.errors = new ArrayList<>();
	}

	public InvalidUserDataError(List<Throwable> errors) {
		this.errors = errors;
	}

	public List<Throwable> getErrors() {
		return errors;
	}
}
