<%-- Document : createProblem Created on : 01-Mar-2026 Author : antigravity --%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="Create Problem" onload="initCreateProblem()">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin="" />
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/createProblem.css">

    <div id="editConfig" style="display:none;"
         data-mode="${editProblem != null ? 'true' : 'false'}"
         data-area="${editProblem != null ? editProblem.areaId : ''}"
         data-category="${editProblem != null ? editProblem.categoryId : ''}"
         data-subcategory="${editProblem != null ? editProblem.subcategoryId : ''}">
    </div>

    <div id="citizenImagesData" style="display:none;">
        <c:if test="${editProblem != null}">
            <c:forEach items="${editProblem.citizenImageUrls}" var="img">
                <span class="citizen-img-data"><c:out value="${img}" /></span>
            </c:forEach>
        </c:if>
    </div>
    <div class="create-problem-container">
        <h2 class="page-title">${editProblem != null ? 'Edit Issue' : 'Report an Issue'}</h2>

        <form action="saveProblem" method="post" onsubmit="return problemCreate()" id="createForm" class="create-form">
            <input type="hidden" name="probId" value="${editProblem != null ? editProblem.probId : ''}" />

            <div class="form-grid">
                <!-- Left Column: Media & Details -->
                <div class="left-col">
                    <div class="form-group row-span-2">
                        <label>Upload Images (Max 5)</label>
                        <div class="upload-grid" id="upload-grid">
                            <!-- JS will dynamically render the slots here -->
                        </div>
                        <input type="file" id="hidden-input" name="problemImages" multiple accept="image/*" style="display:none;" onchange="handleImageUploadChange(this)">
                    </div>

                    <!-- Grab User ID from the active Session -->
                    <!-- Hidden user ID fetched from loggedInUser request attribute -->
                    <input type="hidden" name="userId" value="${requestScope.loggedInUser != null ? requestScope.loggedInUser.userId : ''}" />

                    <div class="form-group">
                        <label for="areaId">Area / Ward</label>
                        <select id="areaId" name="areaId" required>
                            <option value="" disabled selected>Select Area...</option>
                            <!-- Fetched via AJAX from MasterDataController -->
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="categoryId">Category</label>
                        <select id="categoryId" name="categoryId" onchange="fetchSubCategories()" required>
                            <option value="" disabled selected>Select category...</option>
                            <!-- Fetched via AJAX from MasterDataController -->
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="subcategoryId">Specific Issue (Sub-Category)</label>
                        <select id="subcategoryId" name="subcategoryId" required>
                            <option value="" disabled selected>Select sub-category first...</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title" required placeholder="Brief issue description..." value="${editProblem != null ? editProblem.title : ''}">
                    </div>

                    <div class="form-group">
                        <label for="description">Detailed Description</label>
                        <textarea id="description" name="description" rows="5"
                                  placeholder="Provide more details about the problem here...">${editProblem != null ? editProblem.userDesc : ''}</textarea>
                    </div>
                </div>

                <!-- Right Column: Location -->
                <div class="right-col">
                    <div class="form-group">
                        <label>Location (Pinpoint on Map)</label>
                        <div id="map" class="map-container"></div>
                        <p class="map-hint">Click on the map to automatically set Latitude, Longitude, and
                            approximating Address.</p>
                    </div>

                    <div class="location-inputs">
                        <div class="form-group">
                            <label for="latitude">Latitude</label>
                            <input type="text" id="latitude" name="latitude" readonly required>
                        </div>
                        <div class="form-group">
                            <label for="longitude">Longitude</label>
                            <input type="text" id="longitude" name="longitude" readonly required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="address">Address / Landmark</label>
                        <input type="text" id="addressDescription" name="addressDescription" required
                               placeholder="e.g. Near Main Square, Sector 4..." value="${editProblem != null ? editProblem.addressDescription : ''}">
                    </div>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">${editProblem != null ? 'Update Issue' : 'Submit Issue'}</button>
            </div>
        </form>
    </div>

    <!-- Leaflet JS -->
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
    integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
    <!-- Custom JS -->
    <!-- app.js is already loaded via layout.tag -->
    <script src="${pageContext.request.contextPath}/resources/js/createProblem.js"></script>
</t:layout>