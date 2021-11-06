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
		Cart cart = SessionBeanFactory.getCart();
	DAOFactory daoFactory = DAOFactory.getDAOFactory( application.getInitParameter("dao") );
	CustomerDAO customerDAO = daoFactory.getCustomerDAO();
	PurchaseDAO purchaseDAO = daoFactory.getPurchaseDAO();
		
	
		if(request.getParameter("ok") != null && !cart.isEmpty()){
			cart.confirmPurchase( request.getParameter("email"));
			cart.empty();
	%>
		<div id="main" class="clear">
		<p>Congratulations !!!!  Successful purchase</p>
		<form>
			<input type="submit" name="return" value="return">
			</form>
	</div>
		<%
			} else if(request.getParameter("order") != null){
			
		%>
			
			<div id="main" class="clear">
		<p>order detail</p>
		<ul>
		<%
			for(Product item : cart.getProducts()){
		%>
				<li>Name: <%=item.getName()%> &nbsp; 
				Product Number: <%=item.getProductNumber()%>
				&nbsp; unitary price <%=item.getPrice()%>
				&nbsp; Producer Name: <%=item.getProducer().getName()%>
				</li>
		<%
			}
		%>
		</ul>
			<form>
			<input type="submit" name="return" value="return">
			</form>
			
			</div>
			
	
	
	<%
		} else if(request.getParameter("headerorders") != null){
		String mail = request.getParameter("email");
		Customer customer = customerDAO.findCustomerByName(mail);
	List<Purchase> purchases=	purchaseDAO.findAllPurchasesByCustomer(customer);
						%>
			
			<div id="main" class="clear">
		<p>orderheader details</p>
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
		<span>Email (*) <input type="text" name="email">&nbsp;&nbsp;
		codordine <input type="text" name="codordine"></span><br><br>
		<input type="submit" name="ok" value="final submit">
		<input type="submit" name="order" value="show order">
		<input type="submit" name="headerorders" value="headers">
		<input type="submit" name="all" value="show all orders">
		</form>
	</div>
<%} %>
	<%@ include file="../fragments/footer.jsp"%>

</body>
</html>
