package com.cg;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
//import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.dao.IProductDao;
import com.cg.exception.ProductNotFoundException;
import com.cg.model.Product;
import com.cg.service.IProductService;
import com.cg.service.ProductServiceImpl;

//@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // executes test cases in specified order
class ProductServiceTest {
	@Mock
	IProductDao productDao;  // mock implemented object for IProductDao interface
	
	@InjectMocks
	IProductService service=new ProductServiceImpl();  // injecting productDao object into service
	
	List<Product> plist;
	
	Product e;
	
	@BeforeEach
	public void beforeTest() {
		plist=new ArrayList<>();
		e=new Product(100,"Laptop",2,50000.00,Date.valueOf(LocalDate.of(2023, 3, 31)));
		plist.add(e);
	}

	@Test
	@Order(1)
	void testinsertProduct() {
		System.out.println("Testing Inserting product");

		// When record is inserted then return list with that product
		when(productDao.save(e)).thenReturn(e);
		when(productDao.findAll()).thenReturn(plist);
		List<Product> l1=service.saveProduct(e);
		// service will call dao saveProduct 
		assertTrue(l1.size()>0);
		assertEquals(1,l1.size());
		assertEquals("Laptop",l1.get(0).getProductName());
		
		// verify that productDao.saveProduct method got executed at least once
		verify(productDao,times(1)).save(e);
		verify(productDao,times(1)).findAll();
		
	}
	
	@Test
	@Order(2)
	void testupdateProduct() {
		System.out.println("Testing Update product");
		Product p=plist.get(0);
		p.setProductName("Hp Laptop");
		plist.set(0, p);

		// When record is update then return list with updated product
		when(productDao.save(e)).thenReturn(p);
		when(productDao.findAll()).thenReturn(plist);
		
		List<Product> l1=service.updateProduct(e);
		// service will call dao updateProduct 
		assertTrue(l1.size()>0);
		assertEquals(1,l1.size());
		assertEquals("Hp Laptop",l1.get(0).getProductName());
		
		// verify that productDao.updateProduct method got executed at least once
		verify(productDao,times(1)).save(e);
		verify(productDao,times(1)).findAll();
		
	}
	@Test
	@Order(4)
	void testdeleteProduct() {
		System.out.println("Testing Delete product");
		plist.remove(e);
		// When record is deleted then return list without that product
		doNothing().when(productDao).deleteById(100);
		when(productDao.findAll()).thenReturn(plist);
		
		List<Product> l1=service.deleteProduct(100);
		// service will call dao deleteProduct 
		assertTrue(l1.size()==0);
		assertEquals(0,l1.size());
		assertThrows(IndexOutOfBoundsException.class, ()->l1.get(0));
		
		// verify that productDao.deleteProduct method got executed at least once
		verify(productDao,times(1)).deleteById(100);
		verify(productDao,times(1)).findAll();
		
		
	}
	
	@Test
	@Order(3)
	void testfindProduct() throws ProductNotFoundException {
		System.out.println("Testing finding product by id");
		Optional<Product> op=Optional.of(e);
		// When record is findProduct then return  product of id 100
		when(productDao.findById(100)).thenReturn(op);
		
		Product p=service.findProduct(100);
		// service will call dao findProduct 
		assertNotNull(p);
		assertEquals(100,p.getProductId());
		assertEquals("Laptop",p.getProductName());
		assertThrows(ProductNotFoundException.class, ()->service.findProduct(1010));
		
		
		
		// verify that productDao.findProduct(100) method got executed at least once
		verify(productDao,times(1)).findById(100);
		
		
	}
	
	@Test
	public void test() {
		assertEquals(10, 10);
	}
	
}
