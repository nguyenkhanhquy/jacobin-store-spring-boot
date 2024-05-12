package live.jacobin.service;

import live.jacobin.entity.Cart;
import live.jacobin.entity.LineItem;
import live.jacobin.entity.User;
import live.jacobin.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public Cart selectCartById(int cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    @Override
    public Cart selectCartByUser(User user) {
        return cartRepository.findCartByUser(user);
    }

    @Override
    public Cart selectCartByLineItem(LineItem item) {
        return cartRepository.findCartByLineItem(item);
    }

}
