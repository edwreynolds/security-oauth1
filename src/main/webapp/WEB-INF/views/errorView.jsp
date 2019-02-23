<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>Error Page</title>
</head>

<body>
	<h1>${httpStatusCode} Error -- ${httpStatusReasonTxt}</h1>
	<p>Application has encountered an error.  For assistance please contact technical support.</p>	
	<p>Time:  "${timestamp}"</p>
	<p>URL:   "${url}"</p>
</body>
</html>
