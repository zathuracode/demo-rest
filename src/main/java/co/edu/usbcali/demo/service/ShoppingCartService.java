package co.edu.usbcali.demo.service;

import java.util.Optional;

import co.edu.usbcali.demo.domain.ShoppingCart;



public interface ShoppingCartService extends GenericService<ShoppingCart, Integer> {
	
	 public ShoppingCart findByCustomerEmail(String email) throws Exception;
}
