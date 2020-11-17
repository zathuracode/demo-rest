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

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.repository.ProductRepository;

@Scope("singleton")
@Service
public class ProductServiceImpl implements ProductService {

	private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(Product product) throws ConstraintViolationException {

		Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return productRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		log.debug("finding all Product instances");
		return productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Product save(Product entity) throws Exception {
		log.debug("saving Product instance");

		if (entity == null) {
			throw new Exception("El Product es nulo");
		}

		validate(entity);

		if (productRepository.existsById(entity.getProId())) {
			throw new Exception("Ya existe un Product con id:" + entity.getProId());
		}

		return productRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Product entity) throws Exception {

		
		log.debug("deleting Product instance");

		if (entity == null) {
			throw new Exception("El Product es nulo");
		}

		if (entity.getProId() == null) {
			throw new Exception("El proId es nulo");
		}

		if (productRepository.existsById(entity.getProId()) == false) {
			throw new Exception("No existe un Product con id:" + entity.getProId());
		}

		findById(entity.getProId()).ifPresent(entidad -> {
			List<ShoppingProduct> shoppingProducts = entidad.getShoppingProducts();
			if (shoppingProducts.size()>0 ) {
				throw new RuntimeException("El prodcut esta asociado a shoppingProducts");
			}
		});

		productRepository.deleteById(entity.getProId());
		log.debug("delete Product successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		log.debug("deleting Product instance");
		if (id == null) {
			throw new Exception("El id es  nulo");
		}
		if (productRepository.existsById(id)) {
			delete(productRepository.findById(id).get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Product update(Product entity) throws Exception {

		log.debug("updating Product instance");

		if (entity == null) {
			throw new Exception("El Product es nulo");
		}

		validate(entity);

		if (productRepository.existsById(entity.getProId()) == false) {
			throw new Exception("El product no existe");
		}

		return productRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(String proId) throws Exception {
		log.debug("getting Product instance");
		return productRepository.findById(proId);
	}

}
