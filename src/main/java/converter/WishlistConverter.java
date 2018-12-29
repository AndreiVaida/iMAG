package converter;

import domain.Wishlist;
import dto.WishlistDto;

import java.util.HashSet;
import java.util.stream.Collectors;

public class WishlistConverter {
    private WishlistConverter() {}

    public static WishlistDto toDto(final Wishlist wishlist) {
        final WishlistDto wishlistDto = new WishlistDto();
        wishlistDto.setProductDtos(new HashSet<>(
                wishlist.getProducts().stream()
                        .map(ProductConverter::toDto)
                        .collect(Collectors.toList())));
        return wishlistDto;
    }

    public static Wishlist toEntity(final WishlistDto wishlistDto) {
        final Wishlist wishlist = new Wishlist();
        wishlist.setProducts(new HashSet<>(
                wishlistDto.getProductDtos().stream()
                        .map(ProductConverter::toEntity)
                        .collect(Collectors.toList())));
        return wishlist;
    }
}
