package com.cg;



import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cg.model.Product;
import org.junit.jupiter.api.MethodOrderer;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {
	private RestTemplate restTemplate;
	private static final String productResourceUrl="http://localhost:8083/api/v1/products";
	
	@BeforeEach
	public void setup() {
		restTemplate=new RestTemplate();
	}
	
	@Test
	@Order(1)
	void testfindById() {
		System.out.println("Running find by id url");
		String url=productResourceUrl+"/1007";
	    final ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);
	    Product p=response.getBody();
	    assertEquals(1007,p.getProductId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        
//        url=productResourceUrl+"/1010";
//        final ResponseEntity<String> response1 = restTemplate.getForEntity(url, String.class);
//        //Product with 1010 not found
//        String res=response1.getBody();
//        System.out.println(res);
//        assertEquals(response1.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Test
	@Order(2)
	void testfindById1() {
		System.out.println("Running find by id url as Json");
		String url=productResourceUrl+"/1007";
	    final ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
	    String responseBody=response.getBody();
	    System.out.println(responseBody);
	    JSONParser parser = new JSONParser();
        JSONObject jsonResponseObject = new JSONObject();
        Object obj = new Object();
        try {
            obj = parser.parse(responseBody);  /// convert String json into java Object
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        jsonResponseObject = (JSONObject) obj;  // type cast convrted object into JSONObject
        System.out.println(jsonResponseObject.toJSONString());
        
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals("ReadMe",jsonResponseObject.get("productName"));  // search productName key in json and retrieve its value
	}
	

	@Test
	@Order(3)
	void testfindById2() {
		System.out.println("Running find by id url as Product object");
		String url=productResourceUrl+"/1007";
	    final Product p = restTemplate.getForObject(url, Product.class);
	    System.out.println(p);
	    assertEquals(1007,p.getProductId());
	    assertEquals("ReadMe",p.getProductName());
	
	}
	
	@Test
	@Order(4)
	public void testAddProduct() {
		System.out.println("Running add product url test using postForEntity");
		String url=productResourceUrl;
	    HttpHeaders headers = new HttpHeaders();        
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
          
         String jsonBody = "{\"productId\":1009,\"productName\":\"DELL Laptop\",\"quantity\":5,\"price\":78900.0,\"expiryDate\":\"31-Mar-2025\"}";
        //System.out.println("\n\n" + jsonBody);
        HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
         
        //POST Method to Add New Product
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);
		String responseBody=response.getBody();
		System.out.println(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

	}
	
	@Test
	@Order(5)
	public void testAddProduct1() {
		System.out.println("Running add product url test using postForObject");
		String url=productResourceUrl;
		Product p=new Product(1010,"Pen drive",3,500.00,Date.valueOf(LocalDate.of(2023, 11, 30)));
        HttpEntity<Product> entity = new HttpEntity<Product>(p);
         
        //POST Method to Add New Product
       String response = this.restTemplate.postForObject(url, entity,  String.class);
        
        
        System.out.println(response);

        assertNotNull(response);

	}
	
	@Test
	@Order(6)
	public void testUpdateProduct() {
		System.out.println("Running update product url test using exchange");
		String url=productResourceUrl;
	    HttpHeaders headers = new HttpHeaders();        
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
          
         String jsonBody = "{\"productId\":1009,\"productName\":\"DELL Laptop\",\"quantity\":10,\"price\":95900.0,\"expiryDate\":\"31-Mar-2025\"}";
        System.out.println("\n\n" + jsonBody);
        HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
         
        //POST Method to Add New Product
        ResponseEntity<String> response = this.restTemplate.exchange(url,HttpMethod.PUT, entity, String.class);
		String responseBody=response.getBody();
		System.out.println(responseBody);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(responseBody.contains("95900.0"));

	}
	
	@Test
	@Order(7)
	public void testDeleteProduct() {
		System.out.println("Running delete product url test using exchange");
		String url=productResourceUrl+"/1001";
		HttpHeaders headers = new HttpHeaders();        
        HttpEntity<String> entity = new HttpEntity<String>( headers);
        ResponseEntity<String> response= restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);      
        String responseBody = response.getBody();
        System.out.println(responseBody);
        assertFalse(responseBody.contains("1001"));
         
	}

}
