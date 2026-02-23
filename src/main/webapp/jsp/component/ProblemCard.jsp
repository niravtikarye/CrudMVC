<%-- 
    Document   : ProblemCard
    Created on : 18-Feb-2026, 11:30:27 pm
    Author     : admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="insta-card">
    <!-- Header -->
    <div class="insta-header">
        <div class="user-info">
            <img src="https://i.pinimg.com/736x/ca/c6/d8/cac6d852725aa673ffe24f9f955e6ba1.jpg" />
            <div>
                <span class="username">Rutik Rathod</span>
                <span class="time">3d</span>
            </div>
        </div>
        <div class="more">•••</div>
    </div>

    <!-- Problem Image -->
    <div class="slider-container">
        <c:if test="${fn:length(problem.image) > 1}">          
            <button class="prev-btn" onclick="prevSlide(this)">❮</button>
        </c:if>

        <div class="slider">
            <c:forEach var="imgUrl" items="${problem.image}">
                <img src="${imgUrl}" alt="${problem.title}" class="post-img">
            </c:forEach>
        </div>
        <c:if test="${fn:length(problem.image) > 1}">
            <button class="next-btn" onclick="nextSlide(this)">❯</button>
        </c:if>

    </div>

    <!-- Actions -->
    <div class="insta-actions">
        <div class="left-actions">
            <span>♡</span>
            <span>💬</span>
            <span>↗</span>
        </div>
        <span class="bookmark">🔖</span>
    </div>

    <!-- Likes -->
    <div class="insta-likes">
        1.4k likes
    </div>

    <!-- Caption -->
    <div class="insta-caption">
        <strong>Rutik Rathod</strong> ${problem.description}
    </div>

</div>