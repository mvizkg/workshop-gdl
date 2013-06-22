<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Loans</title>
</head>
<body style="background-color: blue">
<form action="close_loan" method="post">
	<h1>Loans </h1>
	<table bgcolor="#D8D8D8" border="2">
		<tr bgcolor="#D8D8D8">
			<th>#</th>
			<th>Bank Code</th>
			<th>RFC</th>
			<th>Full Name</th>
			<th>Country</th>
			<th>Loun Amount</th>
			<th>Loun Date</th>
			<th>Qualification</th>
			<th>Active</th>
			<th>Loan ID</th>
		</tr>
		<s:iterator value="loans" var="loan">
		<tr bgcolor="#FFFFFF">
			<td><input type="radio" name="id_loan" value="<s:property value="#loan[8]"/>"></td>
			<s:iterator value="top">
			<td><s:property/></td>
			</s:iterator>
		</tr>
		</s:iterator>
	</table>
	<br />
	<s:hidden name="operation" value="2"/>
	<input type="submit" value="Submit">
	<p><a href="<s:url action='index' />">Go back ...</a></p>
</form>
</body>
</html>