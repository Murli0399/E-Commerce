package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masai.entity.Cart;
import com.masai.entity.Product;
import com.masai.entity.User;
import com.masai.exception.CartException;
import com.masai.exception.ProductException;
import com.masai.repository.CartRepository;
import com.masai.repository.ProductRepository;
import com.masai.repository.UserRepository;

@Service
@Transactional
public class CartServiceImpl {

	@Autowired
	private CartRepository cartDao;

	@Autowired
	private ProductRepository productDao;

	@Autowired
	private UserRepository userDao;

	public Cart saveCart(Cart cart) throws CartException {

		if (cartDao.findById(cart.getcId()).isPresent())
			throw new CartException("this cart Already Present ");

		List<Cart> carts = cartDao.findAll();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();
		boolean flag = false;

		for (Cart cartp : carts) {
			if (cartp.getUser().getId() == uid && cartp.getcPresent() == true) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			cart.setUser(user);
			return cartDao.save(cart);
		} else
			throw new CartException("Active Cart Already Present so I can not Create New Cart");

	}

	public Cart updateCart(int pid, int cid) throws CartException, ProductException {
		Optional<Cart> cart = cartDao.findById(cid);
		Optional<Product> product = productDao.findById(pid);

		if (cart.isEmpty())
			throw new CartException("Cart not Present in this id " + cid);

		if (product.isEmpty())
			throw new ProductException("product not present in this " + pid);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();

		if (uid != cart.get().getUser().getId())
			throw new CartException("You Are not Authontigated for this Cart");

		Cart presentCart = cart.get();

		Product presentProduct = product.get();

		presentCart.getProducts().add(presentProduct);

		presentProduct.getCarts().add(presentCart);

		return cartDao.save(presentCart);
	}

	public Cart deactiveCart(int cid) throws CartException {

		Optional<Cart> cart = cartDao.findById(cid);
		if (cart.isEmpty())
			throw new CartException("This cart not present id= " + cid);

		if (cart.get().getcPresent() == false)
			throw new CartException("this Cart Already deactiveted " + cid);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();

		if (uid != cart.get().getUser().getId())
			throw new CartException("You can not Authontigated for this Cart so you can not update Cart");

		Cart presentCart = cart.get();

		presentCart.setcPresent(false);

		return cartDao.save(presentCart);
	}

	public List<Cart> findAllcart() throws CartException {

		List<Cart> carts = cartDao.findAll();

		if (carts.isEmpty())
			throw new CartException("Right now no Cart Present");

		return carts;
	}

	public Cart findCartByUserId() throws CartException {

		List<Cart> carts = cartDao.findAll();

		if (carts.isEmpty())
			throw new CartException("Right now no Cart Present");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();

		for (Cart cart : carts) {
			if (cart.getUser().getId() == uid && cart.getcPresent())
				return cart;
		}
		throw new CartException("Actice Cart not present");
	}

	public Cart findById(int cid) throws CartException {
		// TODO Auto-generated method stub

		Optional<Cart> cart = cartDao.findById(cid);
		if (cart.isEmpty())
			throw new CartException("Cart not present in this id " + cid);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userDao.findByUsername(username).get();
		int uid = user.getId();

		if (uid != cart.get().getUser().getId())
			throw new CartException("You Are not Authontigated for this Cart");

		return cart.get();
	}

}