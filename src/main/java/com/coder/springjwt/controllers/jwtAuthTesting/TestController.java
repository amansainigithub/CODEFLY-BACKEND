//package com.coder.springjwt.controllers.jwtAuthTesting;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
////@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@CrossOrigin(origins = {"http://localhost:8080","http://localhost:4200"}, maxAge = 3600,allowCredentials="true")
//@RequestMapping("/api/test")
//public class TestController {
//	@GetMapping("/all")
//	public String allAccess() {
//		return "Public Content.";
//
//	}
//
//	@GetMapping("/user")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')  or hasRole('SACHT')")
//	public String userAccess() {
//		return "User Content.";
//	}
//
//	@GetMapping("/mod")
//	@PreAuthorize("hasRole('MODERATOR')")
//	public String moderatorAccess() {
//		return "Moderator Board.";
//	}
//
//	@GetMapping("/admin")
//	@PreAuthorize("hasRole('ADMIN')")
//	public String adminAccess() {
//		return "Admin Board.";
//	}
//
//	@GetMapping("/seller")
//	@PreAuthorize("hasRole('SELLER')")
//	public String sellerAccess() {
//		return "Seller Board.";
//	}
//}
