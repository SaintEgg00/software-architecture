package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    List<Product> getProductListByCategory(String CategoryId);

    Product getProduct(String productId);
    List<Product> searchProductList(String keywords);

    void deleteProduct(String id);

    void deleteProductList(String[] ids);
}
