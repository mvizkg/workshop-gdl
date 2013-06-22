<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loan Closed</title>
</head>
<body style="background-color: red">
	<h1>Your Loan with ID number <s:property value="id_loan" /> was closed...</h1>
	<br />
	<p><a href="<s:url action='index'/>">Go back...</a></p>
</body>
</html>