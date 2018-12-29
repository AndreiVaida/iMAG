package service;

import domain.User;
import dto.WishlistDto;

public interface WishlistService {
    WishlistDto getWishlist(final Integer userId);

    void createWishlist(final Integer userId);

    void addWishlistProduct(final Integer userId, final Integer productId);

    void removeWishlistProduct(final Integer userId, final Integer productId);
}
