package co.edu.usbcali.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.usbcali.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
