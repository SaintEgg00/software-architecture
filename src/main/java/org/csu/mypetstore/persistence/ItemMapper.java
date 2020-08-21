package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemMapper {
    void updateInventoryQuantity(Map<String,Object> param);

    void updateItem(Item item);

    int getInventoryQuantity(String itemId);

    List<Item> getItemListByProduct(String productId);

    Item getItem(String itemId);

    void deleteItem(String id);

    void deleteItemByProductId(String id);

    void deleteItemList(String[] ids);

    void deleteInventoryList(String[] ids);

    void deleteInventory(String id);

    void insertItem(Product product);
}
