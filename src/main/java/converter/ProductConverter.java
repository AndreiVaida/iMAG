package converter;

import domain.Product;
import dto.ProductDto;

public class ProductConverter {
    private ProductConverter() {}

    public static ProductDto toDto(final Product product) {
        final ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    public static Product toEntity(final ProductDto productDto) {
        final Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
