<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="it.distributedsystems.sessionbeans.Catalogue"%>
<%@ page import="it.distributedsystems.model.dao.Product"%>
<%@ page import="it.distributedsystems.model.dao.Producer"%>
<%@ page import="it.distributedsystems.sessionbeans.SessionBeanFactory"%>
<%@ page import="java.util.List"%>



<!-- codice html restituito al client -->
<html>
	<head>
		<meta name="Author" content="pisi79">
		<title>Catalogue JSP</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/default.css" type="text/css"/>
	</head>

	<body>	

		<%@ include file="../fragments/header.jsp" %>
		<%@ include file="../fragments/menu.jsp" %>
	
		<div id="main" class="clear">

			
			
			<%
			Catalogue catalogue = SessionBeanFactory.getCatalogue();
			
			
							String name = request.getParameter("name");
							String producerName = request.getParameter("producerName");
							
								if ( name != null && ! name.equals("") ) {

								
									
									if ( request.getParameter("add") != null && request.getParameter("add").equals("submit item") ) {
										
										
										int price =  Integer.parseInt( request.getParameter("price") );
										int prodNumber = Integer.parseInt( request.getParameter("productNumber") );
					
										
										catalogue.add(name,price,prodNumber,producerName);
									}
									else if ( request.getParameter("remove") != null && request.getParameter("remove").equals("ok") ) {
										int productNumber = Integer.parseInt(request.getParameter("productNumber"));
										catalogue.remove(productNumber);
									}
									
								} else if (producerName != null && !producerName.isEmpty()) {
									catalogue.addProducer(producerName);
								}
						%>
			
			<div id="left" style="float: left; width: 48%; border-right: 1px solid grey">
			
				<p>Add an item to the catalogue:</p>
				<form>
					<table>
						<tr><td>
							<label for="name">Name:</label>
						</td><td>
							<input type="text" name="name"/>
						</td></tr>
						<tr><td>
							<label for="price">Price (&#8364;):</label>
						</td><td>
							<input type="text" name="price"/>
						</td></tr>
						<tr><td>
							<label for="productNumber">Product Number:</label>
						</td><td>
							<input type="text" name="productNumber"/>
						</td></tr>
						<tr><td>
						<label for="producerName">Producer:</label>
						</td> <td>
						
						 <select name="producerName">
			<%
				
				for ( Producer producer :  catalogue.getAllProducers()) {
					
			%>
			<option value="<%= producer.getName() %>"><%= producer.getName()%></option>
			<%
				}// end while
			%>
			<option value="n. d.">n. d.</option>
			</select>
			</td></tr><tr>
						<tr><td colspan="2">
							<input type="submit" name="add" value="submit item" style="width:100%"/>
						</td></tr>
					</table>
				</form>
		
		<p>Add a producer:</p>
		
		<form>
		
		<table>
						<tr><td>
							<label for="producerName">Name:</label>
						</td><td>
							<input type="text" name="producerName"/>
						</td></tr>
						
					<tr>
						<tr><td colspan="2">
							<input type="submit" name="add" value="submit producer" style="width:100%"/>
						</td></tr>
		
		</table>
		</form>
			</div>
			
			<div id="right" style="float: right; width: 48%">

				<p>Current catalogue:</p>
				<table class="formdata">
					<tr>
						<th style="width: 23%">Name</th>
						<th style="width: 23%">Price</th>
						<th style="width: 23%">Product Number</th>
						<th style="width: 23%">Producer Name</th>
						<th style="width: 8%"></th>
					</tr>
					<%
						Product[] items = catalogue.getProducts().toArray(new Product[0]);
								for( Product anItem : items ){
					%> 
						<tr>
							<td><%= anItem.getName() %></td>
							<td><%= anItem.getPrice() %> &#8364;</td>
							<td><%= anItem.getProductNumber() %></td>
							<td><%= anItem.getProducer().getName() %></td>
							<td>
								<a href="?remove=ok&productNumber=<%= anItem.getProductNumber() %>">
								<img src="../images/remove.gif" alt="remove"/></a>
							</td>
						</tr>
					<% } %>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table>			
			</div>
		
			<div class="clear">
				<p>&nbsp;</p>
			</div>
			
		</div>
	
		<%@ include file="../fragments/footer.jsp" %>

	</body>
</html>
