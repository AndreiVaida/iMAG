package service;

import converter.ProductConverter;
import domain.Product;
import dto.PageDto;
import dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
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

    @Override
    public PageDto<ProductDto> getPaginatedSortedByName(final Integer pageNumber, final Integer itemsPerPage) {
        final Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNumber - 1, itemsPerPage, Sort.by("name")));
        final List<ProductDto> productDtos = productPage.stream()
                .map(ProductConverter::toDto)
                .collect(Collectors.toList());
        return new PageDto<>(pageNumber, itemsPerPage, productPage.getTotalPages(), productDtos);
    }

    @Override
    public void saveProductImage(final Integer productId, final byte[] bytes) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        product.setImage(bytes);
        productRepository.save(product);
    }

}
