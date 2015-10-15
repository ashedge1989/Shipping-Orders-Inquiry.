<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>


<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Odyssey Transportation</title>

<!--  Load the style sheet for main page -->
<link rel="stylesheet" href="css/app/style.css">

<!-- Load style sheet for JSON tree view -->
<link rel="stylesheet" href="css/jqTree/jqtree.css">

</head>

<body>

	<!-- page header -->
	<header>
		<p class="siteName">
			<span id="siteMainName"><strong>Odyssey Order</strong></span>
		</p>

		<!-- page navigation option -->
		<nav>
			<div>
				<label> <input type="radio" id='createRadio'
					name="colorRadio" value="create"> Create
				</label> <label> <input type="radio" id='LoopUpRadio'
					name="colorRadio" value="lookUp"> LookUp
				</label>
			</div>
		</nav>
	</header>

	<!-- page content -->
	<article>

		<section>

			<div id="orderInfo">
				<div class="orderContent">

					<!-- view for create order -->
					<div class="createOrder box">

						<strong>Create Order</strong>

						<form id="createOrder" name="createOrder" action="orderItem"
							method="post">
							Please upload an Order XML file to save
							<br> 
							<input type="file" name="creaOrderFile"> 
							<input type="submit" name="orderItem" value="Create Order" />  <br>
						</form>

						<p>Stored Order is:</p>
						<p id="newOrderId"><%=request.getAttribute("savedOrderNumber") %></p>

					</div>


					<!-- view for look up order -->
					<div class="lookUpOrder box">

						<strong>Please enter an order number to get it's details</strong>

						<form id="lookUpOrder" name="lookUpOrder" action="lookup"
							method="get">
							Order number: <input type="text" name="userSearchOrderNumber"> <br>
							<input type="submit" name="lookup" value="LookUp Order">
						</form>

						<p>Your Order:</p>

						<div id="tree1"></div>

					</div>

				</div>
			</div>

		</section>

	</article>

	<!-- page footer -->
	<footer>
		<div class="footerInfo">
			<p>&copy; 2015 Odyssey Transportation. All Rights Reserved.   Contact us at +1 988-888-0001.</p>
		</div>
	</footer>



	<!-- Load scripts at end to allow rendering of HTML elements first -->

	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="script/jqTree/tree.jquery.js"></script>
	<script type="text/javascript" src="script/app/odysseyTransFormater.js"></script>


	<script type="text/javascript">		

		//check if control has returned from create or lookup
		// get data form request object. 
		var retData = <%=request.getAttribute("jsonString")%>;
		if (retData == null) {
			console.log("check create");
			$("#createRadio").prop("checked", true);
			$(".box").not(".createOrder").hide();
			$(".createOrder").show();
		} else {
			console.log("check lookup");
			$("#LoopUpRadio").prop("checked", true);
			$(".box").not(".lookUpOrder").hide();
			$(".lookUpOrder").show();
		}

		
		// toggle the view by selecting the option mentioned on the navigation bar. 	
		$(document).ready(function() {

			$('input[type="radio"]').click(function() {
				if ($(this).attr("value") == "create") {
					$(".box").not(".createOrder").hide();
					$(".createOrder").show();
				}
				if ($(this).attr("value") == "lookUp") {
					$(".box").not(".lookUpOrder").hide();
					$(".lookUpOrder").show();
				}
			});
		});
		
		// call the format function and get back the formated javascript object
		var formatedData = formatJSON(retData);
		// set the formated object onto the 'data' of 'jTree'.  
		$(function() {
			$('#tree1').tree({
				data : formatedData
			});
		});
						
	</script>
	
	

</body>

</html>