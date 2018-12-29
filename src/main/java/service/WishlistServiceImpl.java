package service;

import converter.WishlistConverter;
import domain.Product;
import domain.User;
import domain.Wishlist;
import dto.WishlistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import repository.UserRepository;
import repository.WishlistRepository;

import javax.persistence.EntityNotFoundException;

@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public WishlistDto getWishlist(final Integer userId) {
        final Wishlist wishlist = wishlistRepository.findByUser_id(userId);
        if (wishlist == null) {
            throw new EntityNotFoundException();
        }

        return WishlistConverter.toDto(wishlist);
    }

    @Override
    public void createWishlist(final Integer userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        final Wishlist wishlist = new Wishlist(user);
        wishlistRepository.save(wishlist);
    }

    @Override
    public void addWishlistProduct(final Integer userId, final Integer productId) {
        final Wishlist wishlist = wishlistRepository.findByUser_id(userId);
        if (wishlist == null) {
            throw new EntityNotFoundException();
        }

        final Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        wishlist.addProduct(product);
        wishlistRepository.save(wishlist);
    }

    @Override
    public void removeWishlistProduct(final Integer userId, final Integer productId) {
        final Wishlist wishlist = wishlistRepository.findByUser_id(userId);
        if (wishlist == null) {
            throw new EntityNotFoundException();
        }

        final Product product = wishlist.getProductById(productId);
        if (product == null) {
            throw new EntityNotFoundException();
        }

        wishlist.removeProduct(product);
        wishlistRepository.save(wishlist);
    }
}
