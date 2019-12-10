package com.dujingxin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dujingxin.entity.User;
import com.dujingxin.util.PageUtils;

@Controller
@SuppressWarnings("all")
public class UserController {
	
	@Resource
	private RedisTemplate redisTemplate;
	@RequestMapping("/list")
	public String list(Model model,String currentPage) {
		int count = redisTemplate.boundListOps("user_list").range(0, -1).size();
		int pageSize= 10;
		PageUtils pageUtils = new PageUtils(currentPage, count, pageSize);
		BoundListOperations listOps = redisTemplate.boundListOps("user_list");
		
//		List<User> list = new ArrayList<>();
//		for (int i = 1; i <= 100; i++) {
//			User user = (User) hashOps.get(i+"");
//			list.add(user);
//		}
		List list = listOps.range(pageUtils.getPageRecord(), pageUtils.getPageRecord()+pageSize-1);
		String page = pageUtils.getPage();
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		System.out.println(list);
		return "list";
	}
	
	
}
