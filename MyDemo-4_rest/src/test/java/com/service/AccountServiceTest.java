package com.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import com.dao.AccountDAO;
import com.data.Account;
import com.service.AccountService;

@SpringBootTest
@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
		//@InjectMocks     
		@Autowired 
	    AccountService accountService;
	     
	    @MockBean
	    AccountDAO dao;
	    
	    /*
	    @Before
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	    }
	    */
	    @Test
	    public void testShowAllAccounts() {
	    	System.out.println("Running test for show all accounts");
	    	List<Account> templist=new ArrayList<>();
	    	templist.add(new Account(1,"Ram",32000,"ram@abc.com"));
	    	templist.add(new Account(2,"Sham",10000,"sham@abc.com"));
	    	templist.add(new Account(3,"Ramesh",45000,"ramesh@abc.com"));
	    	
	    	when(dao.findAll()).thenReturn(templist);
	    	
	    	List<Account> resultlist=accountService.getAllAccounts();
	    	
	    	assertEquals(3,resultlist.size());
	    	
	    	verify(dao,times(1)).findAll() ;
	    	
	    }

	    @Test
	    public void createAccountTest()
	    {
	    	System.out.println("Running test for saving an account");
	        Account ac = new Account(1,"Lokesh",11000,"lokesh@email.com");
	         
	        when(dao.save(ac)).thenReturn(ac);
	        Account resultac =accountService.saveAccount(ac);
	        
	        assertEquals(1,resultac.getAid());
	         
	        verify(dao, times(1)).save(ac);
	    }
	    
	    @Test
	    public void updateAccount() {
	    	System.out.println("Running Test for updating account");
	    	 Account ac = new Account(100,"Lokesh",11000,"lokesh@email.com");
	    	 Optional<Account> optac=Optional.of(ac);
	    	 int id=100;
	    	 when(dao.findById(id)).thenReturn(optac);
	    	 
	    	 
	    	 Account newac=optac.get();
	    	 newac.setBalance(55000);
	    	// System.out.println(newac);
	  
	    	 when(dao.save(newac)).thenReturn(newac);
	    	 
	    	 Account resultac=accountService.updateAccount(newac,id);
	    	 //System.out.println(resultac);
	    	 assertEquals(55000,resultac.getBalance());
	    	 verify(dao, times(1)).save(ac);
	    }
	    
}
