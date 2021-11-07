<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<%@ page import="it.distributedsystems.sessionbeans.*"%>
<%@ page import="it.distributedsystems.model.dao.*"%>
<%@ page import="java.util.*"%>
<html>
<head>
<meta name="Author" content="pisi79">
<title>Checkout JSP</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/styles/default.css"
	type="text/css" />
</head>


<body>

	<%@ include file="../fragments/header.jsp"%>
	<%@ include file="../fragments/menu.jsp"%>


	<%
	//WATCH OUT!!! ogni JNDI lookup genera un nuovo riferimento allo stateful
			//sb e quindi un nuovo stato. Ricorda che il container ti garantisce lo stesso
			// a patto che tu usi lo stesso rif.(locale o remoto). PERO' il rif. te lo devi gestire tu.
			Cart cart = (Cart) session.getAttribute("cart");
			if(cart == null) {
				cart = SessionBeanFactory.getCart();
				session.setAttribute("cart",cart);
			}
			
		
	
		if(request.getParameter("ok") != null && !cart.isEmpty()){
			
			boolean status = cart.confirmPurchase( request.getParameter("email"));
			
			
			if(status) {
				cart.empty();
			
			%>	
			
		<div id="main" class="clear">
		<p>Congratulations !!!!  Successful purchase</p>
		<form>
			<input type="submit" name="return" value="return">
			</form>
	</div>
	
	<% } else { %>
	<div id="main" class="clear">
			<p> Meanwhile the item in the catalogue has been removed</p>
				<form>
					<input type="submit" name="return" value="return">
					</form>
					</div>
	<% }
		} else if(request.getParameter("purchases") != null){
		String mail = request.getParameter("email");
		
	List<Purchase> purchases=	cart.findAllPurchasesByCustomerName(mail);
						%>
			
			<div id="main" class="clear">
		<p>Purchases details</p>
		<ul>
		<%
			for(Purchase pur : purchases){
		%>
				<li>Purchase Number: <%=pur.getPurchaseNumber()%> &nbsp; Customer: 
				<%=pur.getCustomer().getName()%>	</li>
		<%
			}
		%>
		</ul>
			<form>
			<input type="submit" name="return" value="return">
			</form>
			</div>
			
	
		
						
	
		<%} else {%>
	<div id="main" class="clear">
		<p>Your cart:</p>
		<ul>
			<% 
				int total=0;
				for(Product item : cart.getProducts()) { %>

			<li>Name: <%=item.getName() %> &nbsp; Product Number: <%=item.getProductNumber() %>
				&nbsp; Price: <%=item.getPrice() %>(&#8364;)

				<%total += item.getPrice(); %>

			</li>

			<%}%>

		</ul>
		<p>
			Total:
			<%=total %>(&#8364;)
		</p>

		<form>
			
		
		<p>Shipment information ( * = mandatory): </p>
		<span>Email (*) <input required type="text" name="email">&nbsp;&nbsp;
		</span><br><br>
		<input type="submit" name="ok" value="final submit">
		
		<input type="submit" name="purchases" value="purchases on email">
	
		</form>
	</div>
<%} %>
	<%@ include file="../fragments/footer.jsp"%>

</body>
</html>
