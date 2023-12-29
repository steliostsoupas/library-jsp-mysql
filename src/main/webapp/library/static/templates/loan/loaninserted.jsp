<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Library App</title>
	<style>
		main {
			margin: 0 auto;
			min-width: 412px;
			width: 600px;
			height: 600px;
		}
		.container2 {
			text-align: center;
			padding: 20px;
			border-radius: 8px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			background-color: #fff;
			margin-top: 50px;
		}
		.loan-details {
			margin-top: 20px;
			line-height: 1.6;
			text-align: center;
		}
		.a1 {
			display: inline-block;
			margin-top: 20px;
			padding: 10px 20px;
			text-decoration: none;
			color: #fff;
			background-color: #3498db;
			border-radius: 4px;
			transition: background-color 0.3s ease;
		}
		.a1:hover {
			background-color: #2980b9;
		}
	</style>
</head>
<body>
<header>
	<jsp:include page="../header.jsp" />
</header>
<main>
	<div class="container2">
		<h1>Loan Inserted Successfully</h1>
		<div class="loan-details">
			<p>Loan ID: ${requestScope.insertedLoan.id}</p>
			<p>Book ${requestScope.insertedLoan.book}</p>
			<p>Member ${requestScope.insertedLoan.member}</p>
			<p>Loan Date:  ${requestScope.insertedLoan.loanDate}</p>
			<p>Return Date:  ${requestScope.insertedLoan.returnDate}</p>
		</div>
		<div>
			<form method="POST" action="${pageContext.request.contextPath}/library/loan/menu"><input autofocus style="display: none;" /><button class="a1" >Return</button></form>
		</div>
	</div>
</main>
<footer>
	<jsp:include page="../footer.jsp" />
</footer>
</body>
</html>