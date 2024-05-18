package org.alx.article._25_global_lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.integration.support.locks.LockRegistry;

import java.util.List;
import java.util.concurrent.locks.Lock;

@Service
public class ProductService {

    @Autowired
    private LockRegistry lockRegistry;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void buyProducts1(List<Product> products, Payment userPayment) {

        // execute check "SELECT 1 FROM Store WHERE id = ? AND amount > 1";
        // execute insert payment "INSERT INTO Payment (amount, transaction) VALUES ...";
        // execute insert order "INSERT INTO ProductOrder (payment, product_id) VALUES ..."
        // execute store update "UPDATE Store SET amount = amount - 1 WHERE id = ?"

        final String checkSql =
                "SELECT 1 FROM Store WHERE id = ? AND amount > 1";
        //jdbcTemplate...
        final String insertPaymentSql =
                "INSERT INTO Payment (amount, transaction) VALUES ...";
        //jdbcTemplate...
        final String insertOrderSql =
                "INSERT INTO ProductOrder (payment, product_id) VALUES ...";
        //jdbcTemplate...
        final String updateStoreSql =
                "UPDATE Store SET amount = amount - 1 WHERE id = ?";
        //jdbcTemplate...
    }

    @Transactional
    public void buyProducts(List<Product> products, Payment userPayment) {
        for (Product product : products) {
            Lock lock = lockRegistry.obtain(product.getId());
            lock.lock(); // lock.tryLock();
            try {
                doInsertAndUpdate(product);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    private void doInsertAndUpdate(Product product) throws InterruptedException {
        // Insert and update logic goes here
    }
}