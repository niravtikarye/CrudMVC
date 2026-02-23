<%-- 
    Document   : UserList
    Created on : 23-Feb-2026, 11:56:40 pm
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- User List -->
<div class="suggest-user">
    <div class="suggest-user-left">
        <img src="${user.image}">
        <div class="suggest-user-info">
            <div class="suggest-name">${user.userName}</div>
            <div class="suggest-sub-text">${user.name}</div>
        </div>
    </div>
    <div class="suggest-follow">Follow</div>
</div>