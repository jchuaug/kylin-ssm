package com.fanxing.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {
	@GetMapping("/Hello")
	public String getInfo() {
		return "Hello,this is a test line";
	}

}
