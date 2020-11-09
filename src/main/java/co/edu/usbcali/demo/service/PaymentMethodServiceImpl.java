package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.repository.PaymentMethodRepository;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */

@Scope("singleton")
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
	
	private final static Logger log=LoggerFactory.getLogger(PaymentMethodServiceImpl.class);

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(PaymentMethod paymentMethod) throws ConstraintViolationException {

		Set<ConstraintViolation<PaymentMethod>> constraintViolations = validator.validate(paymentMethod);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return paymentMethodRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaymentMethod> findAll() {
		log.debug("finding all PaymentMethod instances");
		return paymentMethodRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PaymentMethod save(PaymentMethod entity) throws Exception {
		log.debug("saving PaymentMethod instance");

		if (entity == null) {
			throw new Exception("PaymentMethod Null");
		}

		validate(entity);

		if (paymentMethodRepository.existsById(entity.getPayId())) {
			throw new Exception("Ya existe un PaymentMethod con el id:" + entity.getPayId());
		}

		return paymentMethodRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(PaymentMethod entity) throws Exception {
		log.debug("deleting PaymentMethod instance");

		if (entity == null) {
			throw new Exception("PaymentMethod Null");
		}

		if (entity.getPayId() == null) {
			throw new Exception("PaymentMethod id Null");
		}

		if (paymentMethodRepository.existsById(entity.getPayId()) == false) {
			throw new Exception("No existe un PaymentMethod con el Id:"+entity.getPayId());
		}

		findById(entity.getPayId()).ifPresent(entidad -> {
			List<ShoppingCart> shoppingCarts = entidad.getShoppingCarts();
			if (shoppingCarts.size()>0) {
				throw new RuntimeException("El PaymentMethod esta sociado con ShoppingCar");
			}
		});

		paymentMethodRepository.deleteById(entity.getPayId());
		log.debug("delete PaymentMethod successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		log.debug("deleting PaymentMethod instance");
		if (id == null) {
			throw new Exception("PaymentMethod id Null");
		}
		if (paymentMethodRepository.existsById(id)) {
			delete(paymentMethodRepository.findById(id).get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PaymentMethod update(PaymentMethod entity) throws Exception {

		log.debug("updating PaymentMethod instance");

		if (entity == null) {
			throw new Exception("PaymentMethod Null");
		}

		validate(entity);

		if (paymentMethodRepository.existsById(entity.getPayId()) == false) {
			throw new Exception("No existe un PaymentMethod con el Id:"+entity.getPayId());
		}

		return paymentMethodRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PaymentMethod> findById(Integer payId) throws Exception {
		log.debug("getting PaymentMethod instance");
		return paymentMethodRepository.findById(payId);
	}

}
