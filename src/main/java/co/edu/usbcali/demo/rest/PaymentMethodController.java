package co.edu.usbcali.demo.rest;

import co.edu.usbcali.demo.domain.*;
import co.edu.usbcali.demo.dto.PaymentMethodDTO;
import co.edu.usbcali.demo.mapper.PaymentMethodMapper;
import co.edu.usbcali.demo.service.PaymentMethodService;

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
@RequestMapping("/api/v1/paymentMethod")
@CrossOrigin(origins = "*")

public class PaymentMethodController {
	@Autowired
	private PaymentMethodService paymentMethodService;
	@Autowired
	private PaymentMethodMapper paymentMethodMapper;

	@GetMapping(value = "/{payId}")
	public ResponseEntity<?> findById(@PathVariable("payId") Integer payId) throws Exception {

		PaymentMethod paymentMethod = (paymentMethodService.findById(payId).isPresent() == true)
				? paymentMethodService.findById(payId).get()
				: null;

		return ResponseEntity.ok().body(paymentMethodMapper.paymentMethodToPaymentMethodDTO(paymentMethod));
	}

	@GetMapping()
	public ResponseEntity<?> findAll() throws Exception {

		return ResponseEntity.ok()
				.body(paymentMethodMapper.listPaymentMethodToListPaymentMethodDTO(paymentMethodService.findAll()));
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws Exception {

		PaymentMethod paymentMethod = paymentMethodMapper.paymentMethodDTOToPaymentMethod(paymentMethodDTO);
		paymentMethod = paymentMethodService.save(paymentMethod);

		return ResponseEntity.ok().body(paymentMethodMapper.paymentMethodToPaymentMethodDTO(paymentMethod));
	}

	@PutMapping()
	public ResponseEntity<?> update(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws Exception {

		PaymentMethod paymentMethod = paymentMethodMapper.paymentMethodDTOToPaymentMethod(paymentMethodDTO);
		paymentMethod = paymentMethodService.update(paymentMethod);

		return ResponseEntity.ok().body(paymentMethodMapper.paymentMethodToPaymentMethodDTO(paymentMethod));
	}

	@DeleteMapping(value = "/{payId}")
	public ResponseEntity<?> delete(@PathVariable("payId") Integer payId) throws Exception {

		paymentMethodService.deleteById(payId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(paymentMethodService.count());
	}
}
