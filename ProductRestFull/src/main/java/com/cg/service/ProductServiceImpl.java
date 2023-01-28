
package com.cg.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.cg.ctrl.ProductController;
import com.cg.dao.IProductDao;
import com.cg.exception.ProductNotFoundException;
import com.cg.model.Product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productService")
@Transactional
public class ProductServiceImpl implements IProductService{

	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private IProductDao productDao;
	
	@Override
	public List<Product> getAllProducts(){
		logger.info("Products are retrieved from database");
		return productDao.findAll();
	}

	@Override
	public Product findProduct(Integer productId) throws ProductNotFoundException {
		Optional<Product> op=productDao.findById(productId);
		if(op.isPresent()) {
			logger.info("Product is retrieved from database for productID "+productId);
			return op.get();
		}
		else
		{
			logger.error("Product is NOT retrieved from database for productID "+productId);
			throw new ProductNotFoundException("Product with "+productId + " not found");
		}
	}

	@Override
	public List<Product> deleteProduct(Integer productId) {
		productDao.deleteById(productId);
		
		return getAllProducts();
	}

	@Override
	public List<Product> saveProduct(Product product) {
		Product p=productDao.save(product);
		if(p!=null) {
			return getAllProducts();
		}
		else {
			return null;
		}
	}

	@Override
	public List<Product> updateProduct(Product product) {
		Product p=productDao.save(product);
		if(p!=null) {
			return getAllProducts();
		}
		else {
			return null;
		}
	}

	@Override
	public List<Product> saveAll() {
		// TODO Auto-generated method stub
		AtomicInteger productId=new AtomicInteger(1000);
		List<Product> products=new ArrayList<>();
		products.add(new Product(productId.incrementAndGet(), "Apple Xs", 12, 450000,new Date(2025-1900, 3, 12)));
		products.add(new Product(productId.incrementAndGet(), "Apple X", 3, 34000, new Date(2019-1900, 3, 28)));
		products.add(new Product(productId.incrementAndGet(), "Sony Z1", 1, 120000, new Date(2021-1900, 2, 10)));
		products.add(new Product(productId.incrementAndGet(), "Sony ZL", 12, 560000, new Date(2020-1900, 1, 12)));
		products.add(new Product(productId.incrementAndGet(), "Samsung J7", 10, 450000, new Date(2022-1900, 7, 22)));
		products.add(new Product(productId.incrementAndGet(), "Samsung J5", 11, 2234555, new Date(2025-1900, 3, 2)));
		products.add(new Product(productId.incrementAndGet(), "ReadMe", 9, 678900, new Date(2025-1900, 3, 1)));
		
		productDao.saveAll(products);
		return getAllProducts();
	}

	@Override
	public List<Product> getProductByProductName(String name) {
		// TODO Auto-generated method stub
		return productDao.findByProductName(name);
	}

	@Override
	public List<Product> getProductByProductNameContains(String infix) {
		// TODO Auto-generated method stub
		return productDao.findByProductNameContaining(infix);
	}
}
