package com.fanxing.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@GetMapping("Hello")
	public String getInfo() {
		return "Hello,this is a test line";
	}

}
