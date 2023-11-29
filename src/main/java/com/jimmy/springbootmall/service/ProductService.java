package com.jimmy.springbootmall.service;

import com.jimmy.springbootmall.dto.ProductRequest;
import com.jimmy.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
