package live.jacobin.service;

import live.jacobin.entity.LineItem;
import live.jacobin.entity.Product;

import java.util.List;

public interface LineItemService {

    void saveLineItem(LineItem lineItem);

    void deleteLineItem(LineItem lineItem);

    LineItem selectLineItemById(int lineItemId);

    List<LineItem> selectLineItemByProduct(Product product);

}
