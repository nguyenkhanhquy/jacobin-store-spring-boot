package live.jacobin.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import live.jacobin.entity.Cart;
import live.jacobin.entity.LineItem;
import live.jacobin.entity.Product;
import live.jacobin.service.CartService;
import live.jacobin.service.LineItemService;
import live.jacobin.service.ProductService;
import live.jacobin.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final HttpServletRequest request;

    private final CartService cartService;
    private final ProductService productService;
    private final LineItemService lineItemService;

    @Autowired
    public CartController(final HttpServletRequest request, CartService cartService, ProductService productService, LineItemService lineItemService) {
        this.request = request;
        this.cartService = cartService;
        this.productService = productService;
        this.lineItemService = lineItemService;
    }

    @GetMapping("/cart")
    public String showCartPage() {
        HttpSession session = request.getSession();

        if (SessionUtil.getLoginedUser(session) == null) {
            return "redirect:/login";
        }

        return "customer/cart_page";
    }

    @PostMapping("/cart")
    public String requestUpdateCart(@RequestParam String action,
                                    @RequestParam int productId,
                                    @RequestParam(name = "quantity",required = false) String quantityString) {
        HttpSession session = request.getSession();

        if (SessionUtil.getLoginedUser(session) == null) {
            return "redirect:/login";
        }

        // Lấy thông tin giỏ hàng của khách hàng từ session
        Cart cart = cartService.selectCartByUser(SessionUtil.getLoginedUser(session));

        int quantity;
        if (action.equals("add")) {
            quantity = 1;
        } else {
            try {
                quantity = Integer.parseInt(quantityString);
                if (quantity < 0) {
                    quantity = -1;
                }
            } catch (NumberFormatException nfe) {
                quantity = -1;
            }
        }

        Product product = productService.selectProductById(productId);

        LineItem lineItem = new LineItem();
        lineItem.setProduct(product);
        lineItem.setQuantity(quantity);

        if (quantity != 0) {
            if (action.equals("add")) {
                lineItem = cart.addItem(lineItem);

            } else {
                lineItem = cart.updateItem(lineItem);
            }
            lineItemService.saveLineItem(lineItem);
        } else {
            int lineItemId = cart.removeItem(lineItem);
            lineItemService.deleteLineItem(lineItemService.selectLineItemById(lineItemId));
        }

        cartService.saveCart(cart);

        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutsPage() {
        HttpSession session = request.getSession();

        if (SessionUtil.getLoginedUser(session) == null) {
            return "redirect:/login";
        }

        return "customer/checkout_page";
    }

}
