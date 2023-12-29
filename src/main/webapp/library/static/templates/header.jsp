<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  * {
    padding: 0;
    margin: 0;
  }
  .box {
    display: inline-block;
    width: 100px;
    height: 25px;
    background-color: #3498db;
    color: white;
    text-align: center;
    text-decoration: none;
    font-weight: bold;
    margin: 0px 2px;
  }
  .box:hover {
    background-color: #2980b9;
  }
  .container {
    display: flex;
    justify-content: flex-end;
    margin: 0 auto;
    width: 95%;
    min-width: 412px;
  }
  button {
    border: none;
    outline: none;
    background: none;
    cursor: pointer;
  }
</style>
<div class="container">
  <form method="POST" action="${pageContext.request.contextPath}/library/loan/menu"><input autofocus style="display: none;" /><button class="box" >Loan</button></form>
  <form method="POST" action="${pageContext.request.contextPath}/library/book/menu"><input autofocus style="display: none;" /><button class="box" >Book</button></form>
  <form method="POST" action="${pageContext.request.contextPath}/library/member/menu"><input autofocus style="display: none;" /><button class="box" >Member</button></form>
</div>
