package com.sun.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.framework.springmvc.support.BaseController;
import com.sun.security.entity.User;
import com.sun.security.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "get")
	@ResponseBody
	public User getUser(Long id) {
		User user = userService.load(id);
		return user;
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public User save(User user) {
		user = userService.save(user);
		return user;
	}
}
