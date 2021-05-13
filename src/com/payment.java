 package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class payment {

	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}	
	//read payment
	public String readPayments() 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>User ID</th> "
	 +"<th>payment Method</th><th>Total Price</th>"
	 + "<th>country</th> "
	 + "<th>city</th> "
	 + "<th>address</th> "
	 +"<th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from paymenttable"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String pid = Integer.toString(rs.getInt("pid")); 
	 String userid = rs.getString("userid"); 
	 String pMethod = rs.getString("pMethod");
	 String totalPrice = Double.toString( 
	 rs.getDouble("totalPrice")); 
	 String country = rs.getString("country");
	 String city = rs.getString("city");
	 String addres = rs.getString("addres"); 
	 // Add into the html table
	 output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + pid
	 + "'>" + userid + "</td>"; 
	 output += "<td>" + pMethod + "</td>"; 
	 output += "<td>" + totalPrice + "</td>"; 
	 output += "<td>" + country + "</td>";
	 output += "<td>" + city + "</td>";
	 output += "<td>" + addres + "</td>";
	//buttons
	output += "<td><input name='btnUpdate' "
	+"type='button' value='Update' "
	+"class='btnUpdate btn btn-secondary'></td>"
	+ "<td><input name='btnRemove' "
	+"type='button' value='Remove' "
	+"class='btnRemove btn btn-danger' "
	+"data-pid='"
	+ pid + "'>" + "</td></tr>";
	} 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while reading the payment."; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
	//inserting*******************************************************************
	public String insertPayments(String userID, String payMethod, String totPrice, String Country, String City,String Address ) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into paymenttable (`pid`,`userid`,`pMethod`, `totalPrice`, `country`, `city`, `addres`)"
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, userID);
			preparedStmt.setString(3, payMethod);
			preparedStmt.setDouble(4, Double.parseDouble(totPrice));
			preparedStmt.setString(5, Country);
			preparedStmt.setString(6, City);
			preparedStmt.setString(7, Address);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	  //update items*******************************************************************************
	public String updatePayments(String ID,String userID ,String payMethod, String totPrice, String Country, String City,String Address) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE paymenttable SET userid=?,pMethod=?,totalPrice=?,country=?,city=?,addres=? WHERE pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1,userID );
			preparedStmt.setString(2, payMethod);
			preparedStmt.setDouble(3, Double.parseDouble(totPrice));
			preparedStmt.setString(4, Country);
			preparedStmt.setString(5, City);
			preparedStmt.setString(6, Address);
			preparedStmt.setInt(7, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayments(String pid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from paymenttable where pid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
