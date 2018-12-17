package service;

import domain.Product;
import dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    ProductDto get(final Integer id);
    void addProduct(final ProductDto product);
}
