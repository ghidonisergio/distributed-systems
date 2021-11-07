package it.distributedsystems.model.dao;

import java.util.List;
import java.util.Set;

public interface PurchaseDAO {


    //public int removePurchaseByNumber(int purchaseNumber);

    public int removePurchaseById(int id);

    public Purchase findPurchaseByNumber(int purchaseNumber);

    public Purchase findPurchaseById(int id);

    public List<Purchase> getAllPurchases();

    public List<Purchase> findAllPurchasesByCustomer(Customer customer);

    public List<Purchase> findAllPurchasesByProduct(Product product);



	int insertPurchase(Purchase purchase);
}
