package repository;

import domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    Wishlist findByUser_id(final Integer userId);
}
