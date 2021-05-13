<%@page import="com.payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="View/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/Payments.js"></script>
</head>
<body>
<div class="container">
		<div class="row">
			<div class="col-6">
				<h1></h1>
				<form id="formPayments" name="formPayments">
					    USER ID: 
					    <input id="userid" name="userid" type="text"
						class="form-control form-control-sm"> <br> 
						
						Payment Method:
						<input id="pMethod" name="pMethod" type="text"
						class="form-control form-control-sm"> <br> 
						
					   Total price: 
					    <input id="totalPrice" name="totalPrice" type="text"
						class="form-control form-control-sm"> <br> 
						
					   Country:
					   
					   <input id="country" name="country" type="text"
						class="form-control form-control-sm"> <br> 
						City:
						  <input id="city" name="city" type="text"
						   class="form-control form-control-sm"> <br> 
					   Address:
						  <input id="addres" name="addres" type="text"
						  class="form-control form-control-sm"> <br> 
						
						<input id="btnSave" name="btnSave" type="button" value="Save"
						 class="btn btn-primary"> 
						<input type="hidden"id="hidPaymentIDSave" 
						name="hidPaymentIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divPaymentsGrid">
					<%
						payment paymentObj = new payment();
						out.print(paymentObj.readPayments());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>