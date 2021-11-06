package it.distributedsystems.model.ejb;

import javax.annotation.Resource;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.transaction.UserTransaction;

public class HelloBean implements SessionBean
{

	@Resource
	UserTransaction utx;
    public String helloWorld ()   {
    	
        return "Hello from myEjb.HelloBean";
    }

    public void ejbCreate () throws CreateException {}
    public void ejbRemove () {}
    public void setSessionContext (SessionContext ctx) {}
    public void ejbActivate () {}
    public void ejbPassivate () {}
}