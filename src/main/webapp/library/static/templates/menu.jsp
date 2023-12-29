<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library App</title>
  <style>
    main {
      margin: -50px auto;
      min-width: 412px;
      width: 600px;
      height: 600px;
      display: flex;
      justify-content: center;
      align-items: center;
      text-align: center;
      flex-direction: column;
    }
  </style>
</head>
<body>
<header>
  <jsp:include page="./header.jsp" />
</header>
<main>
  <h2>Welcome to my Library App. This app supports essential CRUD operations for library management.</h2>
</main>
  <footer>
    <jsp:include page="./footer.jsp" />
  </footer>
</body>
</html>

