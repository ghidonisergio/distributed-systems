package it.distributedsystems.sessionbeans;

import java.util.Hashtable;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;


public class SessionBeanFactory {
	 private static Logger logger = Logger.getLogger("DAOFactory");

	    public SessionBeanFactory() {


	    }

	    private static InitialContext getInitialContext() throws Exception {
	        Hashtable props = getInitialContextProperties();
	        return new InitialContext();
	    }

	    private static Hashtable getInitialContextProperties() {
	        Hashtable props = new Hashtable();
	        //props.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
	        //props.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
	        //props.put("java.naming.provider.url", "127.0.0.1:8080"); //(new ServerInfo()).getHostAddress()  --- 127.0.0.1 --
	        return props;
	    }

	    public static Catalogue getCatalogue() {
	        try {
	            InitialContext context = getInitialContext();
	            Catalogue result = (Catalogue)context.lookup("java:app/distributed-systems-demo.war/CatalogueSessionBean!it.distributedsystems.sessionbeans.Catalogue");
	            return result;
	        } catch (Exception var3) {
	            logger.error("Error looking up Catalogue", var3);
	            return null;
	        }
	    }

	    public static Cart getCart() {
	        try {
	            InitialContext context = getInitialContext();
	            Cart result = (Cart)context.lookup("java:app/distributed-systems-demo.war/CartSessionBean!it.distributedsystems.sessionbeans.Cart");
	            return result;
	        } catch (Exception var3) {
	            logger.error("Error looking up Cart", var3);
	            return null;
	        }
	    }

	   
	   
}
