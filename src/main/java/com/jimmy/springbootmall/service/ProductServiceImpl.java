package com.jimmy.springbootmall.service;

import com.jimmy.springbootmall.dao.ProductDao;
import com.jimmy.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductId(Integer productId) {
        return productDao.getProductId(productId);
    }
}
