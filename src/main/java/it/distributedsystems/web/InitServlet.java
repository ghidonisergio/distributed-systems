package it.distributedsystems.web;

import it.distributedsystems.model.ejb.HelloBean;
import it.distributedsystems.sessionbeans.SessionBeanFactory;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet(name = "InitServlet", urlPatterns = {"hello"}, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "distributed-systems-demo")
	EntityManager em;
	
	

	@Resource
	private UserTransaction utx;

	

	@Override
	public void init(ServletConfig config)throws ServletException {
		super.init(config);
		System.out.println("EXECUTING CREATE TABLE");
		
		SessionBeanFactory.getCatalogue().addProducer("n. d.");
		
		/*
		 * 
		 * WATCH OUT!!! l'istanza em che passi NON è la stessa che recuperi dopo
		 * quasi sicuramente generando un persistence context diverso. Ricorda jsp:bean di tempo fa...
		COME AL SOLITO usare servletctx per passare roba container managed è una pessima idea
		this.getServletContext().setAttribute("em", em);
		
		*/
		/*this.getServletContext().setAttribute("utx", utx);
		try {
		utx.begin();
		em.createNativeQuery("create table IF NOT EXISTS atable (mycol int);").executeUpdate();
		 utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			 try {
				utx.rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		System.out.println("  TABLE created");*/
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("Hello, World!");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HelloBean ejb = new HelloBean();
		String name = request.getParameter("name");
		if (name == null) name = "World";
		PrintWriter out = response.getWriter();
		out.println("Hello "+name+"!!!");
		out.println(ejb.toString());
	}
}