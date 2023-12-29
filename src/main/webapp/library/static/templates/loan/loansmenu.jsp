<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library App</title>
  <style>
    .rounded {
      border-radius: 12px;
      border: 1px solid black;
      width: 100px;
      height: 25px;
    }
    main {
      margin: 50px auto 0;
      min-width: 200px;
      width: 400px;
      height: auto;
      padding: 20px;
      padding-bottom: 100px;
      border-radius: 2px;
    }
    .center {
      text-align: center;
    }
    .search-wrapper, .insert-wrapper {
      width: 300px;
      margin: 20px auto;
      border: 1px solid black;
      padding: 20px;
    }
    .title {
      font-size: 28px;
      text-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
    }
    input[type=text].search {
      width: 200px;
      height: 50px;
      background-position: left;
      background-repeat: no-repeat;
      background-size: 45px 45px;
      padding-left: 45px;
    }
    .rounded {
      border-radius: 18px;
      border: 1.5px solid black;
    }
    .color-btn {
      background-color: lightblue;
    }
    input.search:focus {
      outline: none;
    }
    .search-btn {
      width: 200px;
      height: 50px;
      font-size: 17px;
      align-items: center;
    }
    .insert {
      padding-left: 15px;
      width: 200px;
      height: 30px;
    }
    .bot-gap1 {
      padding-bottom: 10px;
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

  <div class="inline middle">

    <div class="center">
      <div class="search-wrapper">
        <div class="bot-gap1">
          <span class="title">Search for Loans</span>
        </div>
        <div class="bot-gap">
          <form method="POST" action="${pageContext.request.contextPath}/library/searchloan">
            <input name="memberLastName" type="text" class="search rounded" placeholder="Insert member's Lastname"
                   autofocus/>
            <br><br>
            <button class="search-btn rounded color-btn" type="submit">Search</button>
          </form>
        </div>
      </div>

      <div class="insert-wrapper">
        <div class="bot-gap1">
          <span class="title">Insert a Loan</span>
        </div>
        <div class="bot-gap">
          <form method="POST" action="${pageContext.request.contextPath}/library/insertloan">
            <input name="bookid"   type="text" value="${requestScope.insertedloan.bookid}"
                   class="insert rounded" placeholder="Book ID" autofocus/><br>
            <input name="memberid" type="text" value="${requestScope.insertedloan.memberid}"
                   class="insert rounded" placeholder="Member ID" autofocus/>
            <input name="loanDate" type="text" value="${requestScope.insertedloan.loanDate}"
                   class="insert rounded" placeholder="Loan Date (dd/MM/yy)" autofocus/><br>
            <input name="returnDate" type="text" value="${requestScope.insertedloan.returnDate}"
                   class="insert rounded" placeholder="Return Date (dd/MM/yy)" autofocus/>
            <br><br>
            <button class="search-btn rounded color-btn" type="submit">Insert</button>
          </form>
        </div>
      </div>
    </div>

    <div class="center">
      <p>${requestScope.error}</p>
    </div>

    <div class="center">
      <c:if test="${requestScope.sqlError}">
        <p>${requestScope.message}</p>
      </c:if>
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

