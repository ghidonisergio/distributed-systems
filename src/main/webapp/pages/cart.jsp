<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="it.distributedsystems.sessionbeans.Cart"%>
<%@ page import="it.distributedsystems.model.dao.Product"%>
<%@ page import="it.distributedsystems.sessionbeans.Catalogue"%>
<%@ page import="it.distributedsystems.sessionbeans.SessionBeanFactory"%>
<%@ page import="java.util.*"%>

<%!double total = 0;%>
<!-- codice html restituito al client -->
<html>
<head>
<title>Cart JSP</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css" type="text/css" />
</head>

<body>

	<%@ include file="../fragments/header.jsp"%>
	<%@ include file="../fragments/menu.jsp"%>

	<div id="main" class="clear">

		
		<%
		Cart cart = SessionBeanFactory.getCart();
		Catalogue catalogue = SessionBeanFactory.getCatalogue();
		if (application.getAttribute("purchaseNumber") == null) {
			application.setAttribute("purchaseNumber", new Integer(0));
		}
		
			if (request.getParameterNames().hasMoreElements()) {
				if (request.getParameter("remove") != null) {
					int prodNum = Integer.parseInt(request.getParameter("productNumber") );
					cart.removeByProductNumber(prodNum);
					
					response.sendRedirect(request.getContextPath()+"/pages/cart.jsp");
						

					}	 else {
						
						
					 if(	!cart.isPurchasePresent() ) {
						 //primo pprod nel cart
					Integer purchaseNumber = ((Integer) application.getAttribute("purchaseNumber")) + 1;
					application.setAttribute("purchaseNumber",purchaseNumber);
					cart.setPurchaseNumber(purchaseNumber);
					 }
					
					int productNumber = Integer.parseInt(request.getParameter("productNumber"));
					
					cart.put(productNumber);
				}
			}
		%>

		<div id="myleft">
			<p>Add an item to the cart:</p>

			<form method="post">
				<table class="formdata">
					<tr>
						<th>Name</th>


						<th>Price (&#8364;)</th>


						<th>Product Number</th>

						<th>Producer Name</th>
					</tr>

					<%
						
									for (Product product : catalogue.getProducts()) {
					%>

					<tr>

						<td><%=product.getName()%></td>

						<td><%=product.getPrice()%></td>
						
						<td><%=product.getProductNumber()%></td>

						<td><%=product.getProducer().getName()%></td>


						<td>
						<input type="hidden" name="name" value="<%=product.getName()%>"/>
						<input type="hidden" name="price" value="<%=product.getPrice()%>"/>
						<input type="hidden" name="producerName" value="<%=product.getProducer().getName()%>"/>
							<button type="submit" name="productNumber" value="<%=product.getProductNumber()%>">
								Add to cart</button>
						</td>
					</tr>
					<%
						}
					%>


				</table>
			</form>
		</div>

		<div id="myright">
			<p>Current cart</p>

			<table class="formdata">
				<tr>
					<th>Name</th>


					<th>Price (&#8364;)</th>

					<th>Product Number</th>

					<th>Producer Name</th>

					<th id="iconaElimina"></th>
				</tr>

				<%
					total = 0;
							Set<Product> cartItems = cart.getProducts();
							List<Product> catalogueItems = catalogue.getProducts();
							boolean changed = cartItems.retainAll(catalogueItems);
							for (Product item2 : cartItems) {
				%>

				<tr>

					<td><%=item2.getName()%></td>

					<td><%=item2.getPrice()%></td>

					<td><%=item2.getProductNumber()%></td>

					<td><%=item2.getProducer().getName()%></td>
					<%
						total += item2.getPrice();
					%>
					<td><a
						href="?remove=ok&productNumber=<%=item2.getProductNumber()%>"> <img
							src="../images/remove.gif" alt="remove" /></a></td>
				</tr>

				<%
					}
				%>
			</table>
			<p>
				Total cart price:
				<%=total%>
				&#8364;
			</p>
			<p>
			<% if (changed == true) { %>
				<b>
					Some items are no longer available and thus they are been removed from your cart !!				
				</b>
			
			<%} %>
			</p>
		</div>

		<div class="clear">
			<p>&nbsp;</p>
		</div>

	</div>

	<%@ include file="../fragments/footer.jsp"%>

</body>
</html>
