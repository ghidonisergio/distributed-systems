package it.distributedsystems.model.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import it.distributedsystems.model.dao.CustomerDAO;
import it.distributedsystems.model.dao.Producer;
import it.distributedsystems.model.dao.ProducerDAO;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.ProductDAO;
import it.distributedsystems.model.dao.PurchaseDAO;

@Stateless
@Local
public class EntityController {
	 private static Logger logger = Logger.getLogger("DAOFactory");
	
	@PersistenceContext(unitName = "distributed-systems-demo")
	EntityManager em;

	@EJB
	ProducerDAO producerDAO;

	@EJB
	ProductDAO productDAO;

	/*@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int insertProductWithProducer(
			String producerName,
			String productName,
			int productNumber) {
		Producer producer = new Producer();
		producer.setName( producerName );
		int id = producerDAO.insertProducer( producer );
		Product product = new Product();
		product.setName( productName );
		product.setProductNumber(productNumber);
		
		product.setProducer(producer);
			id=	productDAO.insertProduct(product);
		
		return  id;
	}*/
	
	public static EntityController getEntityController() {
		  try {
		InitialContext context = new InitialContext();
		EntityController result = (EntityController)context.lookup("java:app/distributed-systems-demo.war/EntityController!it.distributedsystems.model.ejb.EntityController");
        return result;
      
       
        } catch (Exception var3) {
            logger.error("Error looking up EJB encity controller", var3);
            return null;
        }
	}

}
