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
		.form-group {
			margin-bottom: 15px;
		}
		label {
			display: block;
			margin-bottom: 5px;
		}
		input[type="text"] {
			padding: 8px;
			border: 1px solid #ccc;
			border-radius: 4px;
			min-width: 200px;
			box-sizing: border-box;
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


		.flex-container {
			display: flex;
			justify-content:center;
		}
		.middle {
			min-width: 400px;
		}
		.left, .right {
			display: flex;
			align-items: center;
			min-width: 600px;
			flex-direction: column;
		}
		table {
			border-collapse: collapse;
			align-items: center;
			text-align: center;
		}
		th {
			background-color: #3498db;
			border: 1px solid black;
			color: white;
			text-align: left;
			padding: 5px;
			min-width: 50px;
		}
		td {
			border: 1px solid black;
			padding: 5px;
			min-width: 50px;
		}

	</style>
</head>
<body>
<header>
	<jsp:include page="../header.jsp" />
</header>
<main class="flex-container">
	<div class="inline left">
		<h2>Books</h2>
		<table>
			<thead>
			<tr>
				<th>ID</th><br><th>Title</th><br><th>Author</th><br><th>Loaned</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="book" items="${books}">
				<tr>
					<td>${book.id}</td>
					<td>${book.title}</td>
					<td>${book.author}</td>
					<td>${book.isLoaned() ? 'Yes' : 'No'}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container2 inline middle">
		<form method="POST" action="${pageContext.request.contextPath}/library/updateloan">
			<div class="form-group">
				<label for="tid">ID</label>
				<input id="tid" type="text" name="id" value="${param.id}" readonly>
			</div>
			<div class="form-group">
				<label for="title">Book ID</label>
				<input id="title" type="text" name="bookid" value="${param.book}">
			</div>
			<div class="form-group">
				<label for="author">Member ID</label>
				<input id="author" type="text" name="memberid" value="${param.member}">
			</div>
			<button class="search rounded a1" type="submit">Update Book</button>
		</form>

		<form style="margin-top: 20px;" method="POST" action="${pageContext.request.contextPath}/library/searchloan">
			<input name="memberLastName" type="text" class="search rounded a1" placeholder="Insert loan's title" autofocus style="display: none;" />
			<button class="search-btn rounded color-btn" type="submit">Return</button>
		</form>

		<div style="text-align: center; padding-top: 20px;">
			<p>${requestScope.error}</p>
		</div>
	</div>

	<div class="inline right">

		<h2>Members</h2>
		<table>
			<thead>
			<tr>
				<th>ID</th>
				<th>Firstname</th>
				<th>Lastname</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="member" items="${members}">
				<tr>
					<td>${member.id}</td>
					<td>${member.firstname}</td>
					<td>${member.lastname}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>

</main>
<footer>
	<jsp:include page="../footer.jsp" />
</footer>
</body>
</html>