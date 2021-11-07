package it.distributedsystems.sessionbeans;

import java.util.List;

import it.distributedsystems.model.dao.Producer;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;
import it.distributedsystems.notused.Item;

public interface Catalogue {

	List<Product> getAvailableProducts();

	void empty();
	
	
	
	void add(String prodName,int price,int prodNumber,String producerName);
	
	List<Producer> getAllProducers();

	void remove(int productNumber);
	
	Product getByProductNumber(int productNumber);
	
	void addProducer(String producerName);

	void flush();

}