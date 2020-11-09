package co.edu.usbcali.demo.rest;

import co.edu.usbcali.demo.domain.*;
import co.edu.usbcali.demo.dto.ShoppingCartDTO;
import co.edu.usbcali.demo.mapper.ShoppingCartMapper;
import co.edu.usbcali.demo.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@RestController
@RequestMapping("/api/v1/shoppingCart")
@CrossOrigin(origins = "*")
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	@GetMapping(value = "/{carId}")
	public ResponseEntity<?> findById(@PathVariable("carId") Integer carId) throws Exception {

		ShoppingCart shoppingCart = (shoppingCartService.findById(carId).isPresent() == true)
				? shoppingCartService.findById(carId).get()
				: null;

		return ResponseEntity.ok().body(shoppingCartMapper.shoppingCartToShoppingCartDTO(shoppingCart));
	}

	@GetMapping()
	public ResponseEntity<?> findAll() throws Exception {

		return ResponseEntity.ok()
				.body(shoppingCartMapper.listShoppingCartToListShoppingCartDTO(shoppingCartService.findAll()));
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody ShoppingCartDTO shoppingCartDTO) throws Exception {

		ShoppingCart shoppingCart = shoppingCartMapper.shoppingCartDTOToShoppingCart(shoppingCartDTO);
		shoppingCart = shoppingCartService.save(shoppingCart);

		return ResponseEntity.ok().body(shoppingCartMapper.shoppingCartToShoppingCartDTO(shoppingCart));
	}

	@PutMapping()
	public ResponseEntity<?> update(@Valid @RequestBody ShoppingCartDTO shoppingCartDTO) throws Exception {

		ShoppingCart shoppingCart = shoppingCartMapper.shoppingCartDTOToShoppingCart(shoppingCartDTO);
		shoppingCart = shoppingCartService.update(shoppingCart);

		return ResponseEntity.ok().body(shoppingCartMapper.shoppingCartToShoppingCartDTO(shoppingCart));
	}

	@DeleteMapping(value = "/{carId}")
	public ResponseEntity<?> delete(@PathVariable("carId") Integer carId) throws Exception {

		shoppingCartService.deleteById(carId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(shoppingCartService.count());
	}
}
