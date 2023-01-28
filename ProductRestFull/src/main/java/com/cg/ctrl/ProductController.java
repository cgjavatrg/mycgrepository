package com.cg.ctrl;

import java.util.List;

import com.cg.exception.ProductNotFoundException;
import com.cg.model.Product;
import com.cg.service.IProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
	 Logger logger = LoggerFactory.getLogger(ProductController.class);
	 //http://localhost:8083/api/v1/
//	 @RequestMapping("/")
//	    public String index() {
//	        logger.trace("A TRACE Message");
//	        logger.debug("A DEBUG Message");
//	        logger.info("An INFO Message");
//	        logger.warn("A WARN Message");
//	        logger.error("An ERROR Message");
//
//	        return "Howdy! Check out the Logs to see the output...";
//	    }
	@Autowired
	private IProductService productService;
	
////http://localhost:8083/api/v1/products but http method is PUT
	@PutMapping("/products")
	public ResponseEntity<List<Product>> updateProduct(
			@RequestBody Product product){
		List<Product> products= productService.updateProduct(product);
		if(products.isEmpty())
		{
			logger.error("Products are not avaioabe");
			return new ResponseEntity("Sorry! Products not available!", 
					HttpStatus.NOT_FOUND);
		}
		logger.info("Product is successfully updated");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	
	//@RequestMapping(value ="/products", consumes = MediaType.APPLICATION_JSON_VALUE, headers="Accept=application/json",method = RequestMethod.POST)

	
	//// http://localhost:8083/api/v1/products but http method is POST
	@PostMapping("/products")
	public ResponseEntity<List<Product>> insertProduct(
			@RequestBody Product product){  // @RequetBody translate incoming JSON product data into Java Product oject
		List<Product> products= productService.saveProduct(product);
		if(products.isEmpty())
		{
			return new ResponseEntity("Sorry! Products not available!", 
					HttpStatus.NOT_FOUND);
		}
		logger.info("Product is successfully added");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
////http://localhost:8083/api/v1/products/1008 but http method is DELETE
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<List<Product>> deleteProduct(
			@PathVariable("productId")Integer productId){
		List<Product> products= productService.deleteProduct(productId);
		if(products.isEmpty() || products==null) {
			return new ResponseEntity("Sorry! ProductsId not available!", 
					HttpStatus.NOT_FOUND);
		}
		logger.info("Product is successfully deleted");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
////http://localhost:8083/api/v1/products/1008 but http method is GET
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> findProduct(
			@PathVariable("productId")Integer productId) throws ProductNotFoundException{
		Product product= productService.findProduct(productId);
//		if(product==null) {
//			return new ResponseEntity("Sorry! Products not found!", 
//					HttpStatus.NOT_FOUND);
//		}
		
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	// http://localhost:8083/api/v1/products
	@GetMapping("/products")  //@RequestMapping(value = "/products",method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> products= productService.getAllProducts();
		if(products.isEmpty()) {
			return new ResponseEntity("Sorry! Products not available!", 
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	//http://localhost:8083/api/v1/products/byProductName/ReadMe
	@GetMapping("/products/byProductName/{name}") 
	public ResponseEntity<List<Product>> getAllProductsByName(@PathVariable String name){
		List<Product> products=productService.getProductByProductName(name);
		if(products.isEmpty()) {
			return new ResponseEntity("Sorry! Products not available!", 
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	//http://localhost:8083/api/v1/products/byProductNameContains/Sony
	@GetMapping("/products/byProductNameContains/{name}") 
	public ResponseEntity<List<Product>> getAllProductsByNameContains(@PathVariable String name){
		List<Product> products=productService.getProductByProductNameContains(name);
		if(products.isEmpty()) {
			return new ResponseEntity("Sorry! Products not available!", 
					HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	 // local to the RestController
	 @ExceptionHandler(ProductNotFoundException.class)
	    public final ResponseEntity<String> handleExceptions(Exception ex, WebRequest request) {
	      logger.error(ex.getMessage());
	        return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}
