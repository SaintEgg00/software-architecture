package org.csu.mypetstore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;

    public Category getCategory(String categoryId){
        return categoryMapper.getCategory(categoryId);
    }

    public Product getProduct(String productId){
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId){
        return productMapper.getProductListByCategory(categoryId);
    }
    public List<Item> getItemListByProduct(String productId){
        return itemMapper.getItemListByProduct(productId);
    }

    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public Item getItem(String itemId){
        return itemMapper.getItem(itemId);
    }

    public boolean isItemInStock(String itemId){
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }

    public List<Category> getCategoryList(){
        return categoryMapper.getCategoryList();
    }

    public PageInfo<Category> getCategoryList(int page, int limit){
        PageHelper.startPage(page,limit);
        List<Category> categoryList = categoryMapper.getCategoryList();
        PageInfo<Category> pageInfo = new PageInfo<Category>(categoryList);
        return pageInfo;
    }
    public PageInfo<Product> getProductList(int page, int limit,String categoryId){
        PageHelper.startPage(page,limit);
        List<Product> productList = productMapper.getProductListByCategory(categoryId);
        //processProductDescription(productList);
        PageInfo<Product> pageInfo = new PageInfo<Product>(productList);
        return pageInfo;
    }

    public PageInfo<Item> getItemList(int page, int limit, String productId) {
        PageHelper.startPage(page,limit);
        List<Item> itemList = itemMapper.getItemListByProduct(productId);
        PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);
        return pageInfo;
    }
    @Transactional
    public void deleteItem(String id) {
        itemMapper.deleteItem(id);
        itemMapper.deleteInventory(id);
    }
    @Transactional
    public void deleteItemList(String[] ids) {
        itemMapper.deleteItemList(ids);
        itemMapper.deleteInventoryList(ids);
    }
    @Transactional
    public void deleteProduct(String id) {
        productMapper.deleteProduct(id);  //删除product
    }

    public void deleteProductList(String[] ids) {
        productMapper.deleteProductList(ids);
    }
    public void updateItem(Item item) {
        itemMapper.updateItem(item);
    }
    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
    }
    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }


    public void insertItem(Product product) {
        itemMapper.insertItem(product);
    }
}
