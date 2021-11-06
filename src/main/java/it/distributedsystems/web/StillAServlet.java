/* NOTA IMPORTANTE
 * L' istanza di gestioneAstaController come quella di gestioneUtentiLoggati
 * sarà creata da gestioneUtentiLoggati&Aste (nel suo costruttore)
 * A sua volta l' istanza di gestioneUtentiLoggati&Aste sarà creata come bean con 
 * scope application dalla prima jsp delle 4 che riceve una qualsiasi richiesta.
 * Le 4 jsp sono storicoAsteController, StampaStatoController, creaAstaController e
 * cerca&PartecipaController
 */


package it.distributedsystems.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class StillAServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String homeURL = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.homeURL = config.getInitParameter("homeURL");
	}

	@Override
	public void service(ServletRequest req, ServletResponse resp)
	throws ServletException, IOException {
		// tempo di attesa, qui inserito artificiosamente
		try {
			//Thread.sleep(5000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// un altro forward eseguito lato servlet
		req.getRequestDispatcher(homeURL).forward(req, resp);
	}
	
}
