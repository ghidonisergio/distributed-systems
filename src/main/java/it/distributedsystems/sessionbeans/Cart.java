package it.distributedsystems.sessionbeans;

import java.util.List;
import java.util.Set;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;

public interface Cart {

	Set<Product> getProducts();

	int getOrder(Product item);

	void put(int prodNum);

	void empty();
	
	void removeByProductNumber(int productNumber);
	
	int getPurchaseNumber() throws Exception;
	
	void setPurchaseNumber(int purchaseNumber);
	
	 boolean isPurchasePresent();
	 
	 List<Purchase> findAllPurchasesByCustomerName(String name);
	 
	 boolean isEmpty();
	 
	boolean confirmPurchase(String customerName) throws Exception;
	public Customer getCustomer() ;
	
	void removeItem(int prodNum);
		
	

}