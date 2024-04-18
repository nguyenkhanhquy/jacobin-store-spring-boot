package live.jacobin.service;

import live.jacobin.entity.Cart;
import live.jacobin.entity.LineItem;
import live.jacobin.entity.User;

public interface CartService {

    void saveCart(Cart cart);

    void deleteCart(Cart cart);

    Cart selectCartById(int cartId);

    Cart selectCartByUser(User user);

    Cart selectCartByLineItem(LineItem item);

}
