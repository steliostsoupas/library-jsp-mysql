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
		.search {
			padding: 8px;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
		.search-btn {
			padding: 8px 16px;
			border: none;
			border-radius: 4px;
			background-color: #3498db;
			color: white;
			cursor: pointer;
		}
		.search-btn:hover {
			background-color: #2980b9;
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
	</style>
</head>
<body>
<header>
	<jsp:include page="../header.jsp" />
</header>
<main>
	<div class="container2">
		<h2>Member with id: ${requestScope.memberDTO.id} has deleted</h2><br>
		<h3>Details</h3>
		<p>ID: ${requestScope.memberDTO.id} </p>
		<p>Firstname: ${requestScope.memberDTO.firstname} </p>
		<p>Lastname: ${requestScope.memberDTO.lastname} </p>

		<form style="margin-top: 20px;" method="POST" action="${pageContext.request.contextPath}/library/searchmember">
			<input name="lastname" type="text" class="search rounded a1" placeholder="Insert member's lastname" autofocus style="display: none;" />
			<button class="search-btn rounded color-btn" type="submit">Return</button>
		</form>
	</div>
</main>
<footer>
	<jsp:include page="../footer.jsp" />
</footer>
</body>
</html>