package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Cart;
import com.masai.exception.CartException;
import com.masai.exception.ProductException;
import com.masai.service.CartServiceImpl;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartServiceImpl cartServices;

	@PostMapping("/save")
	public ResponseEntity<Cart> saveCartController(@RequestBody Cart cart) throws CartException {

		Cart saveCart = cartServices.saveCart(cart);

		return new ResponseEntity<Cart>(saveCart, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/{pId}/{cId}")
	public ResponseEntity<Cart> updateCartController(@PathVariable("pId") int pId, @PathVariable("cId") int cid)
			throws CartException, ProductException {

		Cart cart = cartServices.updateCart(pId, cid);
		return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);

	}

	@PutMapping("/deActive/{cid}")
	public ResponseEntity<Cart> deActiveCartController(@PathVariable("cid") int cid) throws CartException {

		Cart cart = cartServices.deactiveCart(cid);
		return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);

	}

	@GetMapping("/getCarts")
	public ResponseEntity<List<Cart>> findAllcartController() throws CartException {

		List<Cart> carts = cartServices.findAllcart();
		return new ResponseEntity<List<Cart>>(carts, HttpStatus.ACCEPTED);

	}

	@GetMapping("/getCart")
	public ResponseEntity<Cart> findCartByUserIdController() throws CartException {

		Cart cart = cartServices.findCartByUserId();
		return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);

	}

	@GetMapping("/getCartById/{cid}")
	public ResponseEntity<Cart> findByIdController(@PathVariable("cid") int cid) throws CartException {

		Cart cart = cartServices.findById(cid);
		return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);
	}

}