package com.tml.poc.Wallet.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tml.poc.Wallet.jwt.resorce.AuthenticationException;
import com.tml.poc.Wallet.models.usermodels.UserModel;
import com.tml.poc.Wallet.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/{id}/update")
	public Object getUserUpdate(@PathVariable long id,@RequestBody UserModel userModule) {
		
		return ResponseEntity.ok(userService.doUserUpdate(id, userModule,false));
	}
	
	@GetMapping("/{id}/get")
	public Object getGivenUser(@PathVariable long id)  throws AuthenticationException {
		
		return ResponseEntity.ok((userService.doGetUserById(id)));
	}
	
	@DeleteMapping("/delete/{id}")
	public Object DeleteUser(@PathVariable long id)  throws AuthenticationException {
		
		return ResponseEntity.ok((userService.doDeleteUserById(id)));
	}
	
	@DeleteMapping("/image/remove/{id}")
	public Object removeUserImage(@PathVariable long id)  throws AuthenticationException {
		
		return ResponseEntity.ok((userService.doRemoveImageUserById(id)));
	}


			
}
