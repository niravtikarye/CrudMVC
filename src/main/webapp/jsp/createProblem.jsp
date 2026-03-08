<%-- Document : createProblem Created on : 01-Mar-2026 Author : antigravity --%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout pageTitle="Create Problem">
    <!-- Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"
          integrity="sha256-p4NxAoJBhIIN+hmNHrzRCf9tD/miZyoHS5obTRR9BMY=" crossorigin="" />
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/createProblem.css">

    <div class="create-problem-container">
        <h2 class="page-title">Report an Issue</h2>

        <form action="saveProblem" method="post" onsubmit="return problemCreate()" class="create-form">

            <div class="form-grid">
                <!-- Left Column: Media & Details -->
                <div class="left-col">
                    <div class="form-group row-span-2">
                        <label>Upload Images (Max 5)</label>
                        <div class="upload-grid" id="upload-grid">
                            <!-- JS will dynamically render the 5 slots here (Empty dashed boxes with '+', or image previews with 'X') -->
                        </div>
                        <input type="file" id="hidden-input" name="images" multiple accept="image/*"
                               style="display:none;">
                    </div>

                    <div class="form-group">
                        <label for="category">Category</label>
                        <select id="category" name="category">
                            <option value="" disabled selected>Select issue category...</option>
                            <option value="Roads">Broken Roads / Potholes</option>
                            <option value="Water">Water Leakage / Supply Issue</option>
                            <option value="Electricity">Electricity Outage / Broken Lights</option>
                            <option value="Garbage">Garbage / Waste Accumulation</option>
                            <option value="Other">Other Civic Issue</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" id="title" name="title" placeholder="Brief issue description...">
                    </div>

                    <div class="form-group">
                        <label for="description">Detailed Description</label>
                        <textarea id="description" name="description" rows="5"
                                  placeholder="Provide more details about the problem here...">

                        </textarea>
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
                        <input type="text" id="address" name="address"
                               placeholder="e.g. Near Main Square, Sector 4...">
                    </div>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">Submit Issue</button>
            </div>
        </form>
    </div>

    <!-- Leaflet JS -->
    <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"
    integrity="sha256-20nQCchB9co0qIjJZRGuk2/Z9VM+kNiyxNV1lvTlZBo=" crossorigin=""></script>
    <!-- Custom JS -->
    <script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/createProblem.js"></script>
</t:layout>