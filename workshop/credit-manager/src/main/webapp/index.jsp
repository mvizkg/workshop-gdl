<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome!</title>
</head>
<body>
	<h1>RFC</h1>
	<!-- <s:textarea name="job" label="Job" id="job" cols="32" rows="3" /> -->
	<s:form action="loan">
		<s:textfield name="rfc" label="Write your RFC" />
		<s:select label="Select an option" headerKey="-1" 
			list="#{'1':'Search RFC', '2':'Close Loan', '3':'Add Loan', '4':'Payments'}" 
			name="operation" value="1">
		</s:select>
		<s:submit value="SUBMIT" />
	</s:form>
</body>
</html>