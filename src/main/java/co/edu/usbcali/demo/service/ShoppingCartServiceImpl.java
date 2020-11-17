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

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;

@Scope("singleton")
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private final static Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(ShoppingCart shoppingCart) throws ConstraintViolationException {

		Set<ConstraintViolation<ShoppingCart>> constraintViolations = validator.validate(shoppingCart);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return shoppingCartRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingCart> findAll() {
		log.debug("finding all ShoppingCart instances");
		return shoppingCartRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingCart save(ShoppingCart entity) throws Exception {
		log.debug("saving ShoppingCart instance");

		if (entity == null) {
			throw new Exception("ShoppingCart nulo");
		}

		validate(entity);

		if (shoppingCartRepository.existsById(entity.getCarId())) {
			throw new Exception("Ya existe un ShoppingCart con ese Id");
		}

		return shoppingCartRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(ShoppingCart entity) throws Exception {
		log.debug("deleting ShoppingCart instance");

		if (entity == null) {
			throw new Exception("ShoppingCart nulo");
		}

		if (entity.getCarId() == null) {
			throw new Exception("El id es nulo");
		}

		if (shoppingCartRepository.existsById(entity.getCarId()) == false) {
			throw new Exception("No existe un ShoppingCart con Id:"+entity.getCarId());
		}

		findById(entity.getCarId()).ifPresent(entidad -> {
			List<ShoppingProduct> shoppingProducts = entidad.getShoppingProducts();
			if (shoppingProducts.size()>0) {
				throw new RuntimeException("El shopping cart tiene shoppingProducts asociados");
			}
		});

		shoppingCartRepository.deleteById(entity.getCarId());
		log.debug("delete ShoppingCart successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		log.debug("deleting ShoppingCart instance");
		if (id == null) {
			throw new Exception("El id es nulo");
		}
		if (shoppingCartRepository.existsById(id)) {
			delete(shoppingCartRepository.findById(id).get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ShoppingCart update(ShoppingCart entity) throws Exception {

		log.debug("updating ShoppingCart instance");

		if (entity == null) {
			throw new Exception("ShoppingCart nulo");
		}

		validate(entity);

		if (shoppingCartRepository.existsById(entity.getCarId()) == false) {
			throw new Exception("No existe un ShoppingCart con Id:"+entity.getCarId());
		}

		return shoppingCartRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ShoppingCart> findById(Integer carId) throws Exception {
		log.debug("getting ShoppingCart instance");
		return shoppingCartRepository.findById(carId);
	}

	@Override
	@Transactional(readOnly = true)
	public ShoppingCart findByCustomerEmail(String email) throws Exception {

		return null;
	}

}
