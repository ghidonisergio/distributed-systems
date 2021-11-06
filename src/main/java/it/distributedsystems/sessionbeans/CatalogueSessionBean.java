package it.distributedsystems.sessionbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.distributedsystems.model.dao.Producer;
import it.distributedsystems.model.dao.ProducerDAO;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.ProductDAO;
import it.distributedsystems.model.ejb.EJB3ProductDAO;
import it.distributedsystems.notused.Item;

@Stateless
@Local
public class CatalogueSessionBean implements Serializable, Catalogue {

	private static final long serialVersionUID = 1L;
	
	 @PersistenceContext(unitName = "distributed-systems-demo")
	    EntityManager em;
	 
	 @EJB
	 ProductDAO productDAO;
	 
	 @EJB
	 ProducerDAO producerDAO;

	//private List<Product> items = new ArrayList<Product>();

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Catalogue#getProducts()
	 */
	@Override
	public List<Product> getProducts() {
		return productDAO.getAllProducts();
	}

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Catalogue#empty()
	 */
	@Override
	public void empty() {
		List<Product> products = productDAO.getAllProducts();
		
		for(Product p : products) {
			productDAO.removeProductById(p.getId());
		}
	}
	

	
	@Override
	public void remove(int productNumber) {
		productDAO.removeProductByNumber(productNumber);
	

	}

	@Override
	public List<Producer> getAllProducers() {
		return producerDAO.getAllProducers();
	}


	@Override
	public Product getByProductNumber(int productNumber) {
		return productDAO.findProductByNumber(productNumber);
	}

	@Override
	public void addProducer(String producerName) {
		producerDAO.insertProducer(new Producer(producerName));
		
	}

	@Override
	public void add(String prodName, int price, int prodNumber, String producerName) {
		Product item = new Product();
		
		item.setPrice( price );
		item.setProductNumber(prodNumber );
		 item.setName(prodName);
		
		 if(producerName.equals("n. d.")) {
			 item.setProducer(new Producer("n.d."));
		 } else {
		Producer producer = producerDAO.findProducerByName(producerName);
		item.setProducer(producer);
		 
		producer.getProducts().add(item);
		 }
		productDAO.insertProduct(item);
		
	}

}
