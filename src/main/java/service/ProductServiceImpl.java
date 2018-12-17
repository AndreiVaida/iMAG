package service;

import converter.ProductConverter;
import domain.Product;
import dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductDto get(final Integer id) {
        return ProductConverter.toDto(productRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void addProduct(final ProductDto productDto) {
        final Product product = ProductConverter.toEntity(productDto);
        productRepository.save(product);
    }
}
