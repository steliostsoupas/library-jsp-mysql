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
			margin-top: 50px; /* Adjust this value to position it where desired */
		}
		.book-details {
			margin-top: 20px;
			line-height: 1.6;
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
		<h1>Book Inserted Successfully</h1>
		<div class="book-details">
			<p>Book ID: ${requestScope.insertedBook.id}</p>
			<p>Book Title: ${requestScope.insertedBook.title}</p>
			<p>Book Author: ${requestScope.insertedBook.author}</p>
		</div>
		<div>
			<a class="a1" href="${pageContext.request.contextPath}/library/static/templates/book/booksmenu.jsp">Return</a>
		</div>
	</div>
</main>
<footer>
	<jsp:include page="../footer.jsp" />>
</footer>
</body>
</html>