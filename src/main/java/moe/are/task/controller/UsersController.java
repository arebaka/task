package moe.are.task.controller;

import moe.are.task.model.User;
import moe.are.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listAllUsers(ModelMap model) {
		List<User> users = userService.listAllUsers();
		model.addAttribute("users", users);
		return "users";
	}

	@RequestMapping(value = "/get/{login}", method = RequestMethod.GET)
	public String getUserByLogin(ModelMap model, @PathVariable("login") String login) {
		User user = userService.getUserByLogin(login);
		model.addAttribute("user", user);
		return "user";
	}
}
