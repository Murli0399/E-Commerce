package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Product;
import com.masai.exception.ProductException;
import com.masai.model.ProductModel;
import com.masai.service.ProductServicesImpl;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductServicesImpl productServices;

	@PostMapping("/save")
	public ResponseEntity<Product> SaveProduct(@RequestBody Product product) throws ProductException {

		if (product.getQuantity() > 0)
			product.setPresent(true);
		else
			product.setPresent(false);

		Product saveProduct = productServices.SaveProduct(product);

		return new ResponseEntity<Product>(saveProduct, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) throws ProductException {

		String str = productServices.deleteProduct(id);

		return new ResponseEntity<>(str, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody ProductModel productModel, @PathVariable("id") int id)
			throws ProductException {

		Product saveProduct = productServices.updateProduct(productModel, id);

		return new ResponseEntity<Product>(saveProduct, HttpStatus.ACCEPTED);
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<Product> readProduct(@PathVariable("id") int id) throws ProductException {

		Product saveProduct = productServices.readProduct(id);

		return new ResponseEntity<Product>(saveProduct, HttpStatus.ACCEPTED);

	}

	@GetMapping("/realAll")
	public ResponseEntity<List<Product>> findAllProductcont() throws ProductException {

		return new ResponseEntity<>(productServices.findAllProduct(), HttpStatus.ACCEPTED);
	}

}