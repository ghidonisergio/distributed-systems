package it.distributedsystems.sessionbeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityNotFoundException;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.CustomerDAO;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;
import it.distributedsystems.model.dao.PurchaseDAO;

@Stateful
@Local
public class CartSessionBean implements Serializable, Cart {

	private static final long serialVersionUID = 1L;

	@EJB
	Catalogue catalogue;
	
	@EJB
	PurchaseDAO purchaseDAO;
	
	@EJB 
	CustomerDAO customerDAO;
	
	//mappa produt number:prodotto
	private Map<Integer,Product> items = new HashMap<Integer,Product>();
	
	private Purchase purchase;
	private Customer customer;

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#getProducts()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Set<Product> getProducts() {
		Set<Product> res = new HashSet<>();
		for(int prodnum : items.keySet()) {
			Product prod = items.get(prodnum);
			
			res.add(prod);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#getOrder(it.distributedsystems.model.dao.Product)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getOrder(Product item) {
		//return items.get(item);
		return 0;
	}

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#put(it.distributedsystems.model.dao.Product, int)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void put(int prodNum ) {
		Product prod = catalogue.getByProductNumber(prodNum);
		prod.getProducer().getName();
		items.put( prodNum, prod);
	}
	
	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#empty()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void empty() {
		this.items = new HashMap<Integer,Product>();
		this.purchase = null;
		
	}

	@Override
	public String toString() {
		return "Cart [items=" + items + "]";
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeByProductNumber(int productNumber) {
		items.remove(productNumber);
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean isPurchasePresent() {
		return purchase != null;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getPurchaseNumber() throws Exception {
		if(purchase == null) {
			throw new Exception("must create purchase before access its number!");
		}
		return purchase.getPurchaseNumber();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setPurchaseNumber(int purchaseNumber) {
		if(purchase == null) {
			purchase = new Purchase(purchaseNumber);
		}
		
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean confirmPurchase(String customerName) throws Exception {
		Set<Product> cartProducts = new HashSet<>();
		for(int prodnum : items.keySet()) {
			cartProducts.add(items.get(prodnum));
		}
		
	
		Set<Product> tempSet = new HashSet<>();
		Set<Product> catalogueItems = new HashSet<>(catalogue.getAvailableProducts());
		boolean changed = false;
		boolean tempchanged = false;
		for(Product pmy : cartProducts) {
			tempchanged = true;
			for(Product pcat : catalogueItems) {
				if(pmy.getProductNumber() == pcat.getProductNumber()) {
					tempSet.add(pmy);
					
					tempchanged = false;
					break;
				}
			}
			
			if(tempchanged == true) {
				items.remove(pmy.getProductNumber());
				changed = true;
				}
			
		}
		
		cartProducts = tempSet;
		
		
		if(changed) {
			return false;
		}
		
		/*
		 * 
		 * HYPER WATCH OUT!!!! se l'entity manager esplode con una exception,
		 * non lo puoi più usare. (ridà need transaction exception, anche quando la transazione c'è)
		 * try {
		customer = customerDAO.findCustomerByName(customerName);
		} catch (Exception e) {
			customer = new Customer(customerName);
		}*/
		
		Customer cust = customerDAO.findCustomerByName(customerName);
		if(cust != null) {
		customer = cust;
		} else {
			customer = new Customer(customerName);
		}
		
		/*
		 * HYPER WATCH OUT 2 !!!  usando l'attributo mappedby delle @onetomany,...
		 * devi settare le relazioni tra le entity usando l'attributo specificato con
		 * mappedby (ricorda che la classe con l'attributo mappedby è l'owner)
		 * 
		 * INOLTRE usa cascade SOLO QUANDO NECESSARIO
		 */
		
		customer.getPurchases().add(purchase);
		purchase.setCustomer(customer);
		purchase.setProducts(cartProducts);
		for(int prodnum : items.keySet()) {
			items.get(prodnum).setPurchase(purchase);
		}
		
	
		purchaseDAO.insertPurchase(purchase);
		return true;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean isEmpty() {
		return items.isEmpty();
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public List<Purchase> findAllPurchasesByCustomerName(String name) {
		Customer customer = customerDAO.findCustomerByName(name);
		return	purchaseDAO.findAllPurchasesByCustomer(customer);
		
	}
	
	public void removeItem(int prodNum) {
		items.remove(prodNum);
	}
	
}
