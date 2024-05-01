package live.jacobin.service;

import live.jacobin.entity.OrderItem;

public interface OrderItemService {

    void saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(OrderItem orderItem);
}
