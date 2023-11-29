package com.jimmy.springbootmall.dao;

import com.jimmy.springbootmall.dto.ProductRequest;
import com.jimmy.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId,ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
