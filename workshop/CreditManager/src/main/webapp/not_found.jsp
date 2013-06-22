<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Loan Recjected</title>
</head>
<body style="background-color: red">
	<h1>Sorry, <s:property value="rfc" /> was not found...</h1>
	<p><a href="<s:url action='index'/>">Go back...</a></p>
</body>
</html>