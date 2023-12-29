<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library App</title>
  <style>
    main {
      margin: 50px auto 0;
      min-width: 200px;
      width: 400px;
      height: auto;
      padding: 20px;
      padding-bottom: 50px;
      border-radius: 2px;
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
    table {
      border-collapse: collapse;
      margin-top: -40px;
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
      align-items: center;
      text-align: center;
    }

    td {
      border: 1px solid black;
      padding: 5px;
      min-width: 50px;
      align-items: center;
      text-align: center;
    }
  </style>
</head>
<body>
<header>
  <jsp:include page="../header.jsp" />
</header>
<main>
  <div>
    <table>
      <tr>
        <th>ID</th><br><th>Title</th><br><th>Author</th><br><th>Loaned</th><br><th>Delete</th><br><th>Update</th>
      </tr>
      <c:forEach var="book" items="${requestScope.books}">
        <tr>
          <td>${book.id}</td>
          <td>${book.title}</td>
          <td>${book.author}</td>
          <td>${book.isLoaned() ? 'Yes' : 'No'}</td>
          <td>
            <c:choose>
              <c:when test="${book.isLoaned()}">
                Loaned Cannot Delete
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/library/deletebook?id=${book.id}"
                   onclick="return confirm('Are you sure you want to delete this book?')">Delete</a>
              </c:otherwise>
            </c:choose>
          </td>
          <td>
            <c:choose>
              <c:when test="${book.isLoaned()}">
                Loaned Cannot Update
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/library/static/templates/book/bookupdate.jsp?id=${book.id}&title=${book.title}&author=${book.author}">Update</a>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
  <a class="a1" href="${pageContext.request.contextPath}/library/static/templates/book/booksmenu.jsp">Return</a>
  <div>
    <c:if test="${requestScope.deleteAPIError}">
      <p>${requestScope.message}</p>
    </c:if>
  </div>

  <div>
    <c:if test="${requestScope.updateAPIError}">
      <p>Something went wrong in Update</p>
    </c:if>
  </div>
  <div style="text-align: center; padding-top: 20px;">
    <p>${requestScope.error}</p>
  </div>
</main>
<footer>
  <jsp:include page="../footer.jsp" />
</footer>
</body>
</html>
