<%-- 
    Document   : home
    Created on : 03-Jan-2024, 7:01:56 pm
    Author     : admin
--%>
<%@page errorPage="ErrorPage_404.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="ajax">
<c:if test="${process eq 'add'}">
    <h1>Add</h1>
    <div>
        <div>
            Enter Name <input type="text" id="name" name="name">
        </div>
        <div>
            Enter Email <input type="text" id="email" name="email">
        </div>
        <div>
            Enter Password <input type="text" id="password" name="password">
        </div>
        <div>
            Enter Mobile <input type="text" id="mobile" name="mobile">
        </div>
        <div>
            Enter Address <input type="text" id="address" name="address">
        </div>
        <div>
            <button onclick="insertUser()">Insert</button>
        </div>
        <div id="insertD"></div>
    </div>
</c:if>
<c:if test="${process eq 'view'}">
    <h1>View</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Password</th>
                <th>Mobile</th>
                <th>Address</th>
                <th>Operation</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="data" items="${data}">
                <tr>
                    <td>${data.user_id}</td>
                    <td>${data.uname}</td>
                    <td>${data.email}</td>
                    <td>${data.password}</td>
                    <td>${data.mobile}</td>
                    <td>${data.address}</td>
                    <td><button onclick="openUpdate(this,'update')" Did="${data.user_id}" >Edit</button>
                        <button onclick="deleteUser(this)" Did="${data.user_id}">Delete</button>
                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
    <div id="delteRow"></div>
</c:if>
    
<c:if test="${process eq 'update'}">
    <div id="delteRow"></div>
    <h1>Update User</h1>

    <input type="hidden" id="userId" value="${user.user_id}">

    Name: <input type="text" id="name" value="${user.uname}"><br>
    Email: <input type="text" id="email" value="${user.email}"><br>
    Password: <input type="text" id="password" value="${user.password}"><br>
    Mobile: <input type="text" id="mobile" value="${user.mobile}"><br>
    Address: <input type="text" id="address" value="${user.address}"><br>

    <button onclick="updateUser()">Update</button>
    <div id="updateRow" value="${Ddata}"></div>
</c:if>

<c:if test="${process eq 'delete'}">
    <h1>Delete</h1>
    <div id="delteRow" value="${Ddata}">Deleted ${Ddata}</div>
</c:if>
    </div>