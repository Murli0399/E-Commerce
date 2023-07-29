package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masai.entity.Cart;
import com.masai.entity.Order;
import com.masai.entity.User;
import com.masai.exception.CartException;
import com.masai.exception.OrderException;
import com.masai.repository.CartRepository;
import com.masai.repository.OrderRepository;
import com.masai.repository.UserRepository;

@Service
@Transactional
public class OrderServicesImpl {

	@Autowired
	private OrderRepository orderDao;

	@Autowired
	private CartRepository cartDao;

	@Autowired
	private CartServiceImpl cartServices;

	@Autowired
	private UserRepository userDao;

	public Order makeOrder() throws OrderException, CartException {

		Cart cart = cartServices.findCartByUserId(); // if Active cart not present than it throw an error

		User user = cart.getUser();
		cart.setcPresent(false);
		Order order = new Order();
		order.setActive(true);
		order.setCard(cart);
		order.setUser(user);

		Order saveOrder = orderDao.save(order);

		cart.setcPresent(false);

		cartDao.save(cart);

		return saveOrder;
	}

	public Order placedOrders(int oId) throws OrderException {

		Optional<Order> order = orderDao.findById(oId);

		if (order.isEmpty())
			throw new OrderException("Invalid OrderId " + oId);

		Order presentOrder = order.get();

		if (presentOrder.getActive() == false)
			throw new OrderException("Order already Placed Thanks...");

		presentOrder.setActive(false);

		Order updateOrder = orderDao.save(presentOrder);

		return updateOrder;
	}

	public List<Order> totalOrders() throws OrderException {

		List<Order> orders = orderDao.findAll();

		if (orders.isEmpty())
			throw new OrderException("0 Orders Present right now ");

		return orders;
	}

	public List<Order> totalOrderPandingToPlaced() throws OrderException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();

		List<Order> orders = orderDao.findAll();

		if (orders.isEmpty())
			throw new OrderException("0 Orders Present right now ");

		List<Order> orderPlacedByUser = new ArrayList<>();

		for (Order order : orders) {
			if (order.getUser().getId() == uid && order.getActive()) {
				orderPlacedByUser.add(order);
			}
		}

		if (orderPlacedByUser.isEmpty())
			throw new OrderException("You are not placed any order right not Please Placed a Order...");

		return orderPlacedByUser;
	}

	public Order getOrderById(int oId) throws OrderException {

		Optional<Order> order = orderDao.findById(oId);

		if (order.isEmpty())
			throw new OrderException("Invalid Order Id " + oId);

		return order.get();
	}

	public List<Order> totalOrdersByUser() throws OrderException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();

		List<Order> orders = orderDao.findAll();

		if (orders.isEmpty())
			throw new OrderException("0 Orders Present right now ");

		List<Order> orderPlacedByUser = new ArrayList<>();

		for (Order order : orders) {
			if (order.getUser().getId() == uid) {
				orderPlacedByUser.add(order);
			}
		}

		if (orderPlacedByUser.isEmpty())
			throw new OrderException("You are not placed any order right not Please Placed a Order...");

		return orderPlacedByUser;
	}

}