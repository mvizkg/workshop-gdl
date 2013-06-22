<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Congratulations</title>
</head>
<body style="background-color: green">
	<h1>Congratulations, <s:property value="fname" /> your loan was established!!!</h1>
	<h2>Loan Amount = <s:property value="loan_amount" /></h2>
	<p><a href="<s:url action='index' />">Go back ...</a></p>
</body>
</html>