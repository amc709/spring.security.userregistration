package com.amc.spring.security.userregistration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@GetMapping
	public String productsHome() {
		return """
				<center>
				<h1>Products</h1>
				</center>
				""";
	}
}
