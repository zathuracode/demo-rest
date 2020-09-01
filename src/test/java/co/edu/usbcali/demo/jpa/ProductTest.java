package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
class ProductTest {
	
	private final static Logger log=LoggerFactory.getLogger(ProductTest.class);
	
	@Autowired
	EntityManager entityManager;

	@Test
	@Transactional
	void test() {
		//Continue la ejecucion si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product=entityManager.find(Product.class, "APPL45");
		
		//Continue la ejecucion si no es nulo quiere decir que existe
		assertNotNull(product, "El producto con proId APPL45 no existe");
		
		log.info(product.getName());
	}

}
