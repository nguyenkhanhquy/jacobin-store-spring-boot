package live.jacobin.controller.customer;

import live.jacobin.entity.Product;
import live.jacobin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoadMoreController {

    private final ProductService productService;

    @Autowired
    public LoadMoreController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/load")
    @ResponseBody
    public String loadMore(@RequestParam String cId, @RequestParam Integer exits) {
        List<Product> listP;
        if (!cId.equals("null") && !cId.isEmpty()) {
            int categoryId = Integer.parseInt(cId);
            listP = productService.selectNext5ProductByCategoryId(categoryId, exits);
        } else {
            listP = productService.selectNext10Product(exits);
        }
        return generateHtml(listP);
    }

    private String generateHtml(List<Product> listP) {
        StringBuilder html = new StringBuilder();
        for (Product p : listP) {
            html.append("<div class=\"product col-lg-4 col-md-6 mb-4\">\r\n"
                    + "    <div class=\"card h-100\">\r\n"
                    + "        <a href=\"detail?pId=" + p.getProductId() + "\"><img class=\"card-img-top\" src=\"" + p.getImage() + "\" alt=\"\"></a>\r\n"
                    + "        <div class=\"card-body\">\r\n"
                    + "            <h4 class=\"card-title\">\r\n"
                    + "                <a href=\"detail?pId=" + p.getProductId() + "\">" + p.getName() + "</a> - " + p.getSize() + "\r\n"
                    + "            </h4>\r\n"
                    + "            <h5>" + p.getPriceCurrencyFormat() + "</h5>\r\n"
                    + "            <p class=\"card-text\">" + p.getTitle() + "</p>\r\n"
                    + "        </div>\r\n"
                    + "        <div class=\"card-footer\">\r\n"
                    + "            <a href=\"detail?pId=" + p.getProductId() + "\" class=\"btn btn-outline-dark\">Chi tiết</a>\r\n"
                    + "            <form action=\"cart\" method=\"post\" style=\"display: inline-block;\">\r\n"
                    + "                <input type=\"hidden\" name=\"action\" value=\"add\">\r\n"
                    + "                <input type=\"hidden\" name=\"productId\" value=\"" + p.getProductId() + "\">\r\n"
                    + "                <button class=\"btn btn-outline-primary\" type=\"submit\"><i class=\"fas fa-shopping-cart\">&nbsp;</i>Thêm vào giỏ hàng</button>\r\n"
                    + "            </form>\r\n"
                    + "        </div>\r\n"
                    + "    </div>\r\n"
                    + "</div>");
        }
        return html.toString();
    }

}
