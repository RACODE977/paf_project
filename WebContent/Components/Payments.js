/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentsForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayments").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentsSaveComplete(response.responseText, status);
		}
	});
});
function onPaymentsSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidPaymentIDSave").val("");
	$("#formPayments")[0].reset();
}
// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidPaymentIDSave").val(
					$(this).closest("tr").find('#hidPaymentIDUpdate').val());
			$("#userID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#pMethod").val($(this).closest("tr").find('td:eq(1)').text());
			$("#totalPrice").val($(this).closest("tr").find('td:eq(2)').text());
			$("#country").val($(this).closest("tr").find('td:eq(3)').text());
			$("#city").val($(this).closest("tr").find('td:eq(4)').text());
			$("#addres").val($(this).closest("tr").find('td:eq(5)').text());
		});

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "pid=" + $(this).data("pid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentsDeleteComplete(response.responseText, status);
		}
	});
});
function onPaymentsDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
// CLIENT-MODEL================================================================
function validatePaymentsForm() {
	// user id
	if ($("#userid").val().trim() == "") {
		return "Insert user id.";
	}
	// payment method
	if ($("#pMethod").val().trim() == "") {
		return "Insert payment method.";
	}
	
	// total price-------------------------------
	if ($("#totalPrice").val().trim() == "") {
		return "Insert total Price.";
	}
	// is numerical value
	var tmpPrice = $("#totalPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for total Price.";
	}
	// convert to decimal price
	$("#totalPrice").val(parseFloat(tmpPrice).toFixed(2));
	// Country------------------------
	if ($("#country").val().trim() == "") {
		return "Insert country.";
	}
	// city------------------------
	if ($("#city").val().trim() == "") {
		return "Insert city";
	}
	// address------------------------
	if ($("#addres").val().trim() == "") {
		return "Insert address.";
	}
	return true;
}