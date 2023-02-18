package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.advice.ErrorResponse;
import com.dao.AccountDAO;
import com.data.Account;
import com.data.User;
import com.data.UserDTO;
import com.exception.AccountNotFoundException;
import com.service.AccountService;
import com.service.UserService;


@RestController
//@CrossOrigin(origins="http://localhost:4200",methods = {RequestMethod.GET})
@CrossOrigin(origins="http://localhost:4200")
public class AccountController {
	@Autowired
	AccountService accountService;

	@Autowired
	UserService userService;
	
	@PostMapping("/accounts/login")
	public UserDTO login(@RequestParam String username,@RequestParam String password) {
		return userService.validateLogin(username, password);
	}

	@PostMapping("/accounts/createUser")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@GetMapping("/accounts/byUser/{username}")
	public Set<Account> getAccountsByUserName(@PathVariable String username){
		return userService.getAccountsByUsername(username);
	} 
	
	@GetMapping("/accounts/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	//http://localhost:8082/accounts
	@GetMapping("/accounts")
	public List<Account> showAllAcc()
	{
		List<Account> alist=accountService.getAllAccounts();
		return alist;
	}
	
	 @PostMapping("/accounts")
	  public Account newAccount(@Valid @RequestBody Account newAccount) {
		 return accountService.saveAccount(newAccount);
	  }
	   
	 @GetMapping("/accounts/{id}")
	  public Account showByAid(@PathVariable Integer id)  {

		 return accountService.getAccountById(id);
	  }

	 @GetMapping("/accountsByCust/{cust}")
	  public List<Account> showByCustomer(@PathVariable String cust)  {

		 if(cust==null || cust.isEmpty()) {
			 throw new InputMismatchException("Customer can not be Empty");
		 }
		 else
			 return accountService.getAccountsByCustomer(cust) ;
	  }

	 @GetMapping("/accountsByBalance/{minbal}/{maxbal}")
	  public List<Account> showByBalanceRange(@PathVariable double minbal,@PathVariable double maxbal) throws AccountNotFoundException {

		 if(minbal<0 || maxbal<0 || minbal>maxbal) {
			 throw new InputMismatchException("Enter proper range of balance");
		 }
		 else
			  return accountService.getAccountsByBalanceRange(minbal, maxbal);
	  }
	    
	 @PutMapping("/accounts/{id}")
	  public Account replaceAccount(@Valid @RequestBody Account newAccount, @PathVariable Integer id) {
		 
	
	        return accountService.updateAccount(newAccount,id);

	  }
	        
	 @DeleteMapping("/accounts/{id}")
	  public void deleteAccount(@PathVariable Integer id) {
		 accountService.deleteAccount(id);
	  }
	 
	 
	 // local to the RestController
	 @ExceptionHandler(InputMismatchException.class)
	    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
	        List<String> details = new ArrayList<>();
	        details.add(ex.getLocalizedMessage());
	        ErrorResponse error = new ErrorResponse("Server Error From controller", details);
	        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
