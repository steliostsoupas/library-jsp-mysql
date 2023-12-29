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
      padding-bottom: 50px;
      padding: 20px;
      border-radius: 2px;
      background-color: #fff;
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

    /* table style */
    table {
      border-collapse: collapse;
      margin-top: -40px;
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
<main>
  <div>
    <table>
      <tr>
        <th>ID</th><br><th>Firstname</th><br><th>Lastname</th><br><th>Delete</th><br><th>Update</th>
      </tr>
      <c:forEach var = "member" items = "${requestScope.members}">
        <tr>
          <td>${member.id}</td>
          <td>${member.firstname}</td>
          <td>${member.lastname}</td>
          <td><a href="${pageContext.request.contextPath}/library/deletemember?id=${member.id}&firstname=${member.firstname}&lastname=${member.lastname}"
                 onclick="return confirm('Are you sure you want to delete this member?')">Delete</a></td>

          <td><a href="${pageContext.request.contextPath}/library/static/templates/member/memberupdate.jsp?id=${member.id}&firstname=${member.firstname}&lastname=${member.lastname}">Update</a></td>
        </tr>
      </c:forEach>
    </table>
  </div>
  <a class="a1" href="${pageContext.request.contextPath}/library/static/templates/member/membersmenu.jsp">Return</a>

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
</main>
<footer>
  <jsp:include page="../footer.jsp" />
</footer>
</body>
</html>
