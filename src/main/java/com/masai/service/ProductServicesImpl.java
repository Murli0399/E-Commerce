package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.entity.Product;
import com.masai.exception.ProductException;
import com.masai.model.ProductModel;
import com.masai.repository.ProductRepository;

@Service
public class ProductServicesImpl {

	@Autowired
	private ProductRepository productDao;

	public Product SaveProduct(Product product) throws ProductException {

		if (product == null)
			throw new ProductException("can't save null value");

		if (productDao.findById(product.getPid()).isPresent())
			throw new ProductException("This Product is already Present " + product.toString());

		Product saveProduct = productDao.save(product);

		return saveProduct;
	}

	public String deleteProduct(int id) throws ProductException {
		// TODO Auto-generated method stub

		Optional<Product> product = productDao.findById(id);

		if (product.isEmpty())
			throw new ProductException(
					"Product Not Present in this product id  **" + id + "** so can not delete Product");

		productDao.delete(product.get());

		return "Product deleted Successfully. Product detail is :- " + product.get().toString();
	}

	public Product updateProduct(ProductModel productModel, int id) throws ProductException {

		Optional<Product> product = productDao.findById(id);

		if (product.isEmpty())
			throw new ProductException(
					"Product Not Present in this product id  **" + id + "** so can not Update Product");

		Product exsitingProduct = product.get();

		exsitingProduct.setName(productModel.getName());
		exsitingProduct.setPrice(productModel.getPrice());
		exsitingProduct.setQuantity(productModel.getQuantity());
		exsitingProduct.setPresent(productModel.getPresent());

		return productDao.save(exsitingProduct);
	}

	public Product readProduct(int id) throws ProductException {
		// TODO Auto-generated method stub

		Optional<Product> product = productDao.findById(id);

		if (product.isEmpty())
			throw new ProductException("Product Not Present in this product id  **" + id + "** ");

		return product.get();
	}

	public List<Product> findAllProduct() throws ProductException {

		List<Product> products = productDao.findAll();
		if (products.isEmpty())
			throw new ProductException("Product table is empty");

		return products;
	}

}