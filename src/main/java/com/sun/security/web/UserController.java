package com.sun.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.framework.springmvc.support.BaseController;
import com.sun.security.entity.User;

@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {
	@RequestMapping(value = "get")
	@ResponseBody
	public User getUser() {
		User user = new User();
		user.setUsername("sunxiongwei");
		user.setId(1000L);
		return user;
	}
}
