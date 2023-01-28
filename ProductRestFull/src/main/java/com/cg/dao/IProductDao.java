package com.cg.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.model.Product;

@Repository
public interface IProductDao extends JpaRepository<Product,Integer>{

		public List<Product> findByProductName(@Param("prn")String prodName);
		
		public List<Product> findByProductNameContaining(String infix);
	
		@Query("SELECT prod FROM Product prod  WHERE prod.price>=:pri")
		public List<Product> getByPrice(@Param("pri") double price);

}

// List<Product> findAll()
//Optional<Product> findById(Integer productId);
//Product save(Product p);  // insert or update
// void deleteById(Integer productId);

// no need of implementation class ProductDaoImpl implements IProductDao


///findByProductName(@Param("prn")String prodName)-> select p from Product p where p.productName=:prn

/*
List<Product> findByProductNameContaining(String infix);

select p from Product p where p.productName like '%'+ infix + '%'

*/