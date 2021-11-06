package it.distributedsystems.sessionbeans;

import java.util.Set;

import it.distributedsystems.model.dao.Product;

public interface Cart {

	Set<Product> getProducts();

	int getOrder(Product item);

	void put(int prodNum);

	void empty();
	
	void removeByProductNumber(int productNumber);
	
	int getPurchaseNumber() throws Exception;
	
	void setPurchaseNumber(int purchaseNumber);
	
	 boolean isPurchasePresent();
	 

	 boolean isEmpty();
	 
	void confirmPurchase(String customerName) throws Exception;

}