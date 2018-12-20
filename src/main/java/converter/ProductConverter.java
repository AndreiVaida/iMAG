package converter;

import domain.Product;
import dto.ProductDto;

public class ProductConverter {
    private ProductConverter() {}

    public static ProductDto toDto(final Product product) {
        final ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDetails(product.getDetails());
        productDto.setImage(product.getImage());
        return productDto;
    }

    public static Product toEntity(final ProductDto productDto) {
        final Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDetails(productDto.getDetails());
        product.setImage(productDto.getImage());
        return product;
    }
}
