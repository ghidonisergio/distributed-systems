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
	public Set<Product> getProducts() {
		Set<Product> res = new HashSet<>();
		for(int prodnum : items.keySet()) {
			res.add(items.get(prodnum));
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#getOrder(it.distributedsystems.model.dao.Product)
	 */
	@Override
	public int getOrder(Product item) {
		//return items.get(item);
		return 0;
	}

	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#put(it.distributedsystems.model.dao.Product, int)
	 */
	@Override
	public void put(int prodNum ) {
		Product prod = catalogue.getByProductNumber(prodNum);
		items.put( prodNum, prod);
	}
	
	/* (non-Javadoc)
	 * @see it.distributedsystems.sessionbeans.Cart#empty()
	 */
	@Override
	public void empty() {
		this.items = new HashMap<Integer,Product>();
	}

	@Override
	public String toString() {
		return "Cart [items=" + items + "]";
	}

	@Override
	public void removeByProductNumber(int productNumber) {
		items.remove(productNumber);
		
	}

	public boolean isPurchasePresent() {
		return purchase != null;
	}
	
	@Override
	public int getPurchaseNumber() throws Exception {
		if(purchase == null) {
			throw new Exception("must create purchase before access its number!");
		}
		return purchase.getPurchaseNumber();
	}

	@Override
	public void setPurchaseNumber(int purchaseNumber) {
		if(purchase == null) {
			purchase = new Purchase(purchaseNumber);
		}
		
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void confirmPurchase(String customerName) throws Exception {
		Set<Product> cartProducts = new HashSet<>();
		for(int prodnum : items.keySet()) {
			cartProducts.add(items.get(prodnum));
		}
		
		List<Product> catalogueProducts = catalogue.getProducts();
		boolean changed = cartProducts.retainAll(catalogueProducts);
		if(changed) throw new Exception("Meanwhile the item in the catalogue has been removed");
		
		try {
		customer = customerDAO.findCustomerByName(customerName);
		} catch (EntityNotFoundException e) {
			customer = new Customer(customerName);
		}
		customer.getPurchases().add(purchase);
		purchase.setCustomer(customer);
		purchase.setProducts(cartProducts);
		for(int prodnum : items.keySet()) {
			items.get(prodnum).setPurchase(purchase);
		}
		
		purchaseDAO.insertPurchase(purchase);
		for(int prodnum : items.keySet()) {
			catalogue.remove(prodnum);
		}
		
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	
}
