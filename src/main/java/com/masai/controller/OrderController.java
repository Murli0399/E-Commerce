package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.entity.Order;
import com.masai.exception.CartException;
import com.masai.exception.OrderException;
import com.masai.service.OrderServicesImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderServicesImpl orderServices;

	@PostMapping("/save")
	public ResponseEntity<Order> makeOrderController() throws OrderException, CartException {

		Order order = orderServices.makeOrder();

		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Order> placedOrdersController(@PathVariable("id") int oId) throws OrderException {

		Order order = orderServices.placedOrders(oId);

		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

	@GetMapping("/Orders")
	public ResponseEntity<List<Order>> totalOrdersByUserController() throws OrderException {

		List<Order> orders = orderServices.totalOrdersByUser();

		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}

	@GetMapping("/allOrders")
	public ResponseEntity<List<Order>> totalOrdersController() throws OrderException {

		List<Order> orders = orderServices.totalOrders();

		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);

	}

	@GetMapping("/pendingOrders")
	public ResponseEntity<List<Order>> totalOrderPandingToPlacedController() throws OrderException {

		List<Order> orders = orderServices.totalOrderPandingToPlaced();

		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}

	@GetMapping("/order/{id}")
	public ResponseEntity<Order> getOrderByIdController(@PathVariable("id") int oId) throws OrderException {

		Order order = orderServices.getOrderById(oId);
		return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
	}

}