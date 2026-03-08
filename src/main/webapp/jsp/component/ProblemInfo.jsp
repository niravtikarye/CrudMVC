<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/problem-info.css">

        <div class="problem-container" style="position: relative;">
            <!-- MODAL CLOSE BUTTON -->
            <button class="close-btn" onclick="closeProblemInfo()"
                style="position: absolute; top: 15px; right: 15px; z-index: 1000; background: rgba(0,0,0,0.5); border:none;border-radius:50%;color:#fff;width:30px;height:30px;cursor:pointer;display:flex;align-items:center;justify-content:center;">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none"
                    stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                    class="lucide lucide-x">
                    <path d="M18 6 6 18" />
                    <path d="m6 6 12 12" />
                </svg>
            </button> <!-- LEFT SIDE IMAGE & UPLOADER -->
            <div class="problem-left">
                <div class="image-wrapper" onclick="toggleDesc(event)">
                    <img id="pi-image" class="main-problem-img"
                        src="https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg"
                        alt="problem-image">
                    <div class="overlay-gradient"></div>
                </div>

                <!-- UPLOADER INFO MOVED TO LEFT -->
                <div class="uploader-profile-left">
                    <img src="https://i.pinimg.com/736x/44/a6/75/44a675e91a0d6fc44251137a8989f707.jpg"
                        alt="Uploader Avatar" class="avatar-lg">
                    <div class="uploader-details">
                        <h2 class="uploader-name">Naruto Uzumaki</h2>
                        <span class="upload-time">Posted at 12:00 AM • Problem Uploader</span>
                    </div>
                </div>

                <!-- INTERACTIVE ACTION BUTTONS -->
                <div class="problem-actions-left">
                    <button class="action-btn like-btn">
                        <span class="btn-icon">❤️</span>
                        <span class="btn-text" id="pi-hipe-val">600 Likes</span>
                    </button>
                    <button class="action-btn discuss-btn">
                        <span class="btn-icon">💬</span>
                        <span class="btn-text">Discussion</span>
                    </button>
                    <!-- MOBILE ONLY: SOLVER BUTTON -->
                    <button class="action-btn solver-btn mobile-only" onclick="toggleSolverPanel()">
                        <span class="btn-icon">🧑‍💻</span>
                        <span class="btn-text">Solver Info</span>
                    </button>
                </div>

                <!-- DESCRIPTION OVERLAY ON HOVER -->
                <div class="problem-description-bottom" id="mobile-desc-panel">
                    <div
                        style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; flex-shrink: 0;">
                        <h3 id="pi-title" style="margin-bottom: 0;">Problem Description</h3>
                        <button class="mobile-only" onclick="toggleDesc(event)"
                            style="background: rgba(255,255,255,0.1); border: 1px solid rgba(255,255,255,0.2); color: #fff; border-radius: 12px; padding: 4px 12px; font-size: 12px; font-weight: bold; cursor: pointer;">✕
                            Close</button>
                    </div>

                    <div class="desc-scroll-area" style="overflow-y: auto; flex: 1; padding-right: 8px;">
                        <p class="description-text" id="pi-desc">
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consequuntur soluta quas
                            dignissimos
                            officiis necessitatibus cumque ea ab quasi, distinctio quisquam sequi! A numquam, quia
                            dignissimos
                            porro velit eveniet consectetur ab?
                        </p>
                        <div class="problem-tags">
                            <span class="tag">Math</span>
                            <span class="tag">Logic</span>
                            <span class="tag">Urgent</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- RIGHT SIDE DETAILS (SLIDING PANEL ON MOBILE) -->
            <div class="problem-right" id="solverPanel">

                <!-- MOBILE CLOSE BUTTON -->
                <button class="close-panel-btn mobile-only" onclick="toggleSolverPanel()">✕ Close</button>

                <!-- HEADER / STATUS -->

                <!-- SOLVER DETAILS CARD AT TOP -->
                <div class="card solver-card">
                    <div class="card-header">
                        <h4>Assigned Solver Details</h4>
                        <div class="status-badge solved" id="pi-status-val">Status: Solved</div>
                    </div>
                    <!-- <c:if test="${problem.assigned}"> -->
                    <div class="card-body solver-info">
                        <img src="https://i.pinimg.com/736x/a8/82/c7/a882c78583244f99785547e6c6463c90.jpg"
                            alt="Solver Avatar" class="avatar-md">
                        <div class="solver-meta">
                            <p class="solver-name">kr0ni</p>
                            <span class="time-block">Assigned Time: <span class="highlight">01:00 PM</span></span>
                        </div>
                    </div>
                    <!-- </c:if> -->
                </div>

                <!-- SOLUTION REUPLOAD CARD -->
                <!-- <c:if test="${problem.solved}"> -->
                <div class="card solution-card">
                    <div class="card-header">
                        <h4>Solution Reuploaded Info</h4>
                        <span class="solve-time">Solved at: 2:00 PM</span>
                    </div>
                    <div class="card-body solution-details">
                        <div class="solution-thumbnail-wrapper">
                            <img src="https://i.pinimg.com/736x/86/ed/70/86ed70501526cbe5ba36d15e2758891e.jpg"
                                alt="Solution Thumbnail" class="solution-img">
                            <div class="solution-overlay">
                                <button class="btn-primary">View Full Solution</button>
                            </div>
                        </div>
                        <div class="solution-notes">
                            <h5>Solver's Notes:</h5>
                            <p>The problem required a careful breakdown of the equations to resolve the deadlock. I've
                                reuploaded the complete step-by-step resolution.</p>
                        </div>
                    </div>
                </div>
                <!-- </c:if> -->

            </div>
        </div>