package co.edu.usbcali.demo.jpql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.usbcali.demo.domain.Customer;

@SpringBootTest
class TestCustomerJPQL {
	
	private final static Logger log=LoggerFactory.getLogger(TestCustomerJPQL.class);
	
	@Autowired
	public  EntityManager entityManager;
	
	@BeforeEach
	public void beforeEach() {
		log.info("beforeEach");
		assertNotNull(entityManager,"El entityManager es nulo");
	}
	
	@Test
	public void selectWhereParam() {
		log.info("selectWhereParam");
		String jqpl="SELECT cus FROM Customer cus WHERE cus.enable=:enable AND cus.email=:email";
		List<Customer> customers=entityManager.
				createQuery(jqpl,Customer.class).
				setParameter("enable", "Y").
				setParameter("email", "bropsdf@imgur.com").
				getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
			log.info(customer.getEnable());
		});
		
	}
	
	@Test
	public void selectWhereEnable() {
		log.info("selectWhereEnable");
		String jqpl="SELECT cus FROM Customer cus WHERE cus.enable='Y' ORDER BY cus.email";
		List<Customer> customers=entityManager.createQuery(jqpl,Customer.class).getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
			log.info(customer.getEnable());
		});
		
	}
	
	

	@Test
	public void selectLike() {
		log.info("selectLike");
		String jqpl="SELECT cus FROM Customer cus WHERE cus.name LIKE 'Mar%'";
		List<Customer> customers=entityManager.createQuery(jqpl,Customer.class).getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
		});
		
	}
	
	@Test
	public void selectAll() {
		log.info("selectAll");
		String jqpl="SELECT cus FROM Customer cus";
		List<Customer> customers=entityManager.createQuery(jqpl,Customer.class).getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
		});
		
		for (Customer customer : customers) {
			log.info(customer.getEmail());
			log.info(customer.getName());
		}
		
	}

}
