<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="My Profile">
    <div style="padding: 20px; max-width: 600px; margin: 0 auto; padding-bottom: 60px;">
        <div style="display: flex; align-items: center; gap: 20px; background: var(--bg-color); padding: 20px; border-radius: 8px; border: 1px solid var(--border-color); margin-bottom: 20px;">
            <img src="https://ui-avatars.com/api/?name=${user.name}&background=random&color=fff" alt="Avatar" style="width:80px; height:80px; border-radius:50%; object-fit:cover;">
            <div>
                <h1 style="margin: 0 0 5px 0; font-size: 1.5rem;">${user.name}</h1>
                <p style="margin: 0; color: var(--text-secondary); text-transform: capitalize;">@${user.username} &bull; Role: ${user.role}</p>
            </div>
        </div>

        <h2 style="font-size: 1.2rem; margin-bottom: 15px; padding-left: 10px; border-left: 4px solid var(--primary-color);">${feedType}</h2>

        <div class="cards-section" style="display:flex; flex-direction:column; gap:20px;">
            <c:choose>
                <c:when test="${not empty problems}">
                    <c:forEach var="problem" items="${problems}">
                        <%@ include file="component/ProblemCard.jsp" %>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; color: var(--text-secondary); padding: 40px; background: var(--bg-color); border: 1px solid var(--border-color); border-radius: 8px;">
                        <svg style="width:50px; height:50px; color:var(--text-secondary); margin-bottom:10px;" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"></path></svg>
                        <p>No issues found here yet.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</t:layout>
