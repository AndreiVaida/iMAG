package service;

import dto.PageDto;
import dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();

    ProductDto get(final Integer id);

    void addProduct(final ProductDto product);

    PageDto<ProductDto> getPaginated(final Integer pageNumber, final Integer itemsPerPage);
}
