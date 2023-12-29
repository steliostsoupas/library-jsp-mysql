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
      margin: 0 auto;
      min-width: 412px;
      width: 600px;
      height: 600px;
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
  </style>
</head>
<body>
<header>
  <jsp:include page="../header.jsp" />
</header>
<main>

  <div class="center">
    <div class="search-wrapper">
      <div class="bot-gap1">
        <span class="title">Members Search</span>
      </div>
      <div class="bot-gap">
        <form method="POST" action="${pageContext.request.contextPath}/library/searchmember">
          <input name="lastname" type="text" class="search rounded" placeholder="Insert members's lastname"
                 autofocus/>
          <br><br>
          <button class="search-btn rounded color-btn" type="submit">Search</button>
        </form>
      </div>
    </div>

    <div class="insert-wrapper">
      <div class="bot-gap1">
        <span class="title">Members Insert</span>
      </div>
      <div class="bot-gap">
        <form method="POST" action="${pageContext.request.contextPath}/library/insertmember">
          <input name="firstname" type="text" value="${requestScope.insertedmember.firstname}"
                 class="insert rounded" placeholder="Firstname" autofocus/>
          <input name="lastname" type="text" value="${requestScope.insertedmember.lastname}"
                 class="insert rounded" placeholder="Lastname" autofocus/><br>
          <br><br>
          <button class="search-btn rounded color-btn" type="submit">Insert</button>
        </form>
      </div>
    </div>
  </div>

  <div class="center">
    <c:if test="${requestScope.membersNotFound}">
      <p>No members found</p>
    </c:if>

    <p>${requestScope.error}</p>
  </div>

  <div class="center">
    <c:if test="${requestScope.sqlError}">
      <p>${requestScope.message}</p>
    </c:if>
  </div>
</main>
<footer>
  <jsp:include page="../footer.jsp" />
</footer>
</body>
</html>

