package live.jacobin.service;

import live.jacobin.entity.LineItem;
import live.jacobin.entity.Product;
import live.jacobin.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineItemServiceImpl implements LineItemService {

    private final LineItemRepository lineItemRepository;

    @Autowired
    public LineItemServiceImpl(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    @Override
    public void saveLineItem(LineItem lineItem) {
        lineItemRepository.save(lineItem);
    }

    @Override
    public void deleteLineItem(LineItem lineItem) {
        lineItemRepository.delete(lineItem);
    }

    @Override
    public LineItem selectLineItemById(int lineItemId) {
        return lineItemRepository.findById(lineItemId).orElse(null);
    }

    @Override
    public List<LineItem> selectLineItemByProduct(Product product) {
        return lineItemRepository.findByProduct(product);
    }

}
