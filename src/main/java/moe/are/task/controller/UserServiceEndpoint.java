package moe.are.task.controller;

import moe.are.task.bean.*;
import moe.are.task.error.InvalidUserDataError;
import moe.are.task.error.UserLoginExistsError;
import moe.are.task.error.UserNotFoundError;
import moe.are.task.model.Role;
import moe.are.task.model.User;
import moe.are.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class UserServiceEndpoint{
	private static final String NAMESPACE_URI = "http://task.are.moe/UserService";

	private static moe.are.task.bean.User mapUserWithoutRoles(User user) {
		moe.are.task.bean.User bean = new moe.are.task.bean.User();
		bean.setName(user.getName());
		bean.setLogin(user.getLogin());
		return bean;
	}

	@Autowired
	private UserService userService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListAllUsersRequest")
	@ResponsePayload
	public ListAllUsersResponse listAllUsers(@RequestPayload ListAllUsersRequest request) {
		ListAllUsersResponse response = new ListAllUsersResponse();
		List<User> users = userService.listAllUsers();

		response.setSuccess(Success.TRUE);
		for (User user : users) {
			response.getResult().add(mapUserWithoutRoles(user));
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByLoginRequest")
	@ResponsePayload
	public GetUserByLoginResponse getUserByLogin(@RequestPayload GetUserByLoginRequest request) {
		GetUserByLoginResponse response = new GetUserByLoginResponse();

		try {
			User user = userService.getUserByLogin(request.getLogin());
			response.setSuccess(Success.TRUE);

			UserDetails userDetails = new UserDetails();
			userDetails.setName(user.getName());
			userDetails.setLogin(user.getLogin());
			for (Role role : user.getRoles()) {
				userDetails.getRoles().add(role.getName());
			}
			response.setResult(userDetails);
		}
		catch (UserNotFoundError error) {
			response.setSuccess(Success.FALSE);
			response.getErrors().add(error.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "RemoveUserByLoginRequest")
	@ResponsePayload
	public RemoveUserByLoginResponse removeUserByLogin(@RequestPayload RemoveUserByLoginRequest request) {
		RemoveUserByLoginResponse response = new RemoveUserByLoginResponse();

		try {
			userService.removeUserByLogin(request.getLogin());
			response.setSuccess(Success.TRUE);
		}
		catch (UserNotFoundError error) {
			response.setSuccess(Success.FALSE);
			response.getErrors().add(error.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddUserRequest")
	@ResponsePayload
	public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
		AddUserResponse response = new AddUserResponse();
		User user = new User();
		UserDetailsWithAuth data = request.getData();
		user.setName(data.getName());
		user.setLogin(data.getLogin());
		user.setPassword(data.getPassword());

		try {
			userService.addUser(user);
			response.setSuccess(Success.TRUE);
		}
		catch (UserLoginExistsError error) {
			response.setSuccess(Success.FALSE);
			response.getErrors().add(error.getMessage());
		}

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserByLoginRequest")
	@ResponsePayload
	public UpdateUserByLoginResponse editUser(@RequestPayload UpdateUserByLoginRequest request) {
		UpdateUserByLoginResponse response = new UpdateUserByLoginResponse();
		User user = new User();
		UserDetailsWithAuth data = request.getData();
		user.setName(data.getName());
		user.setLogin(data.getLogin());
		user.setPassword(data.getPassword());

		try {
			userService.updateUser(request.getLogin(), user);
			response.setSuccess(Success.TRUE);
		}
		catch (InvalidUserDataError error) {
			response.setSuccess(Success.FALSE);
			List<Throwable> errors = error.getErrors();

			for (Throwable err : errors) {
				response.getErrors().add(err.getMessage());
			}
		}
		catch (UserNotFoundError error) {
			response.setSuccess(Success.FALSE);
			response.getErrors().add(error.getMessage());
		}

		return response;
	}
}