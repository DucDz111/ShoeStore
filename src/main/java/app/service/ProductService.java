package app.service;

import app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import app.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Page<Product> getProduct(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest);
        }
}
