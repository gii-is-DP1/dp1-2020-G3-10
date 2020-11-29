package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
	
	private ProductRepository productRepository;


	@Autowired
	public ProductService(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	
	@Transactional
	public Product findProductById(final int productId) {
		Product res = this.productRepository.findById(productId);
		return res;
	}
	
	

}
