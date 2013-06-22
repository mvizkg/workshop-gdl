<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New Loan</title>
</head>
<body style="background-color: silver">
	<s:form action="add_loan">
		<s:textfield name="rfc" label="RFC" readonly="true" />
		<s:textfield name="fname" label="Full Name" />
		<s:textfield name="country" label="Country" />
		<s:textfield name="loan_amount" label="Loan Amount" />
		<s:submit value="SUBMIT" />
	</s:form>
	<br />
	<p><a href="<s:url action='index' />">Go back ...</a></p>
</body>
</html>