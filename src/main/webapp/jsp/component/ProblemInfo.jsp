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
                <div class="image-wrapper" onclick="toggleDesc(event)" style="position:relative; display:flex; align-items:center; justify-content:center;">
                    <button id="pi-slider-prev" onclick="piPrevImage(event)" style="position:absolute; left:10px; background:rgba(0,0,0,0.5); border:none; color:white; padding:10px; border-radius:50%; z-index:10; cursor:pointer; display:none;">❮</button>
                    <img id="pi-image" class="main-problem-img"
                        src="https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg"
                        alt="problem-image">
                    <button id="pi-slider-next" onclick="piNextImage(event)" style="position:absolute; right:10px; background:rgba(0,0,0,0.5); border:none; color:white; padding:10px; border-radius:50%; z-index:10; cursor:pointer; display:none;">❯</button>
                    <div class="overlay-gradient"></div>
                </div>

                <div class="uploader-profile-left">
                    <div class="uploader-details" style="padding-left:15px; width:100%; padding-right:15px;">
                        <span class="upload-time" id="pi-status-text" style="font-size:14px; font-weight:bold; color:white;">Status: OPEN</span>
                        <div style="font-size:13px; color:white; margin-top:5px;" id="pi-hipe-val">0 Hypes</div>
                        <div style="font-size:13px; color:white; margin-top:5px; font-weight:500;" id="pi-area-val">📍 </div>
                        <div style="font-size:12px; color:white; margin-top:2px; word-break:break-word;" id="pi-address-val"></div>
                    </div>
                </div>

                <!-- INTERACTIVE ACTION BUTTONS -->
                <div class="problem-actions-left" id="pi-action-buttons" style="display:flex; flex-wrap:wrap; gap:10px; padding: 0 15px;">
                    <!-- Universal Hype Button -->
                    <button id="pi-btn-hype" class="action-btn" style="background:#ff4d4d; color:white; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;" onclick="actionToggleHype()">
                        <span class="btn-text">❤ Hype It!</span>
                    </button>

                    <!-- Solver Actions -->
                    <button id="pi-btn-solve" class="action-btn" style="display:none; background:#3b82f6; color:white; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;" onclick="actionSolve()">
                        <span class="btn-text">Solve</span>
                    </button>
                    <button id="pi-btn-reject" class="action-btn" style="display:none; background:#dc3545; color:white; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;" onclick="actionReject()">
                         <span class="btn-text">Reject</span>
                    </button>
                    <button id="pi-btn-assign" class="action-btn" style="display:none; background:#f59e0b; color:white; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;" onclick="actionAssign()">
                        <span class="btn-text">Assign to Me</span>
                    </button>

                    <!-- Citizen Actions -->
                    <button id="pi-btn-edit" class="action-btn" style="display:none; background:#475569; color:white; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;" onclick="actionEdit()">
                        <span class="btn-text">Edit</span>
                    </button>
                    <button id="pi-btn-delete" class="action-btn" style="display:none; background:#dc3545; color:white; border:none; padding:8px 16px; border-radius:6px; cursor:pointer;" onclick="actionDelete()">
                        <span class="btn-text">Delete</span>
                    </button>

                    <!-- Citizen Verify Block -->
                    <div id="pi-verify-group" style="display:none; align-items:center; gap:10px; background:#f1f5f9; padding:8px 12px; border-radius:6px; width:100%;">
                        <span style="font-size:0.9rem; font-weight:600; color:#333;">Is this issue fixed?</span>
                        <button style="background:#28a745; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;" onclick="actionVerifyAccept()">Yes</button>
                        <button style="background:#dc3545; color:white; border:none; padding:6px 12px; border-radius:4px; cursor:pointer;" onclick="actionVerifyReject()">No</button>
                    </div>

                    <!-- MOBILE ONLY: SOLVER VIEW TOGGLE -->
                    <button id="pi-btn-mobile-solver" class="action-btn solver-btn mobile-only" onclick="toggleSolverPanel()" style="display:none;">
                        <span class="btn-icon">📸</span>
                        <span class="btn-text">View Result</span>
                    </button>
                </div>

                <!-- DESCRIPTION OVERLAY ON HOVER -->
                <div class="problem-description-bottom" id="mobile-desc-panel">
                    <div
                        style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; margin-top: 12px;flex-shrink: 0;">
                        <h3 id="pi-title" style="margin-bottom: 0; color: #fff;">Problem Description</h3>
                        <button class="mobile-only" onclick="toggleDesc(event)"
                            style="background: rgba(255,255,255,0.1); border: 1px solid rgba(255,255,255,0.2); color: #fff; border-radius: 12px; padding: 4px 12px; font-size: 12px; font-weight: bold; cursor: pointer;">✕
                            Close</button>
                    </div>

                    <div class="desc-scroll-area" style="overflow-y: auto; flex: 1; padding-right: 8px;">
                        <p class="description-text" id="pi-desc" style="color: rgba(255,255,255,0.95);">
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consequuntur soluta quas
                            dignissimos
                            officiis necessitatibus cumque ea ab quasi, distinctio quisquam sequi! A numquam, quia
                            dignissimos
                            porro velit eveniet consectetur ab?
                        </p>
                       
                    </div>
                </div>
            </div>

            <!-- RIGHT SIDE DETAILS (SLIDING PANEL ON MOBILE) -->
            <div class="problem-right" id="solverPanel" style="display:none;">

                <!-- MOBILE CLOSE BUTTON -->
                <button class="close-panel-btn mobile-only" onclick="toggleSolverPanel()">✕ Close</button>

                <!-- SOLUTION REUPLOAD CARD -->
                <div class="card solution-card" style="height:100%; display:flex; flex-direction:column; margin:0;">
                    <div class="card-header">
                        <h4 style="margin:0;">Resolution Proof</h4>
                    </div>
                    <div class="card-body solution-details" style="flex:1; display:flex; flex-direction:column; padding:0;">
                        <div class="solution-thumbnail-wrapper" style="flex:1; display:flex; flex-direction:column; height: 100%;">
                            <img id="pi-solver-image" src="" alt="Solution Thumbnail" class="solution-img" style="flex:1; width:100%; object-fit:cover; min-height: 0;">
                            <p id="pi-solver-desc" style="display:none; font-size:13px; color:#333; padding: 10px; margin:0; background: #f8fafc; border-top: 1px solid #e2e8f0;"></p>
                        </div>
                    </div>
                </div>

            </div>
        </div>

<!-- Universal Solve Modal -->
<div id="solve-modal-global" class="modal-overlay" style="display:none; align-items:center; justify-content:center; z-index: 10000; position: fixed; top: 0; left: 0; width: 100vw; height: 100vh; background: rgba(0, 0, 0, 0.75);">
    <div class="problem-info-container" style="max-width:500px; height:auto; padding:24px; text-align:left; background: #fff; border-radius: 16px; box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.3); position: relative; width: 90%;">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px;">
            <h3 style="font-size: 20px; font-weight: 700; color: #0f172a; margin: 0;">Submit Resolution</h3>
            <button type="button" class="close-btn" onclick="closeSolveModal()" style="background:transparent; border:none; font-size:20px; cursor:pointer; color:#64748b;">✖</button>
        </div>
        <form id="solve-form-global" onsubmit="solveProblem(event)" enctype="multipart/form-data" style="display:flex; flex-direction:column; gap:20px;">
            <div>
                <label style="display:block; margin-bottom:8px; font-weight:600; font-size: 14px; color: #475569;">Proof Image (Required)</label>
                <input type="file" name="proofImage" accept="image/*" required style="width:100%; border:1px solid #cbd5e1; border-radius:8px; padding:10px; font-size: 14px; background: #f8fafc;"/>
            </div>
            <div>
                <label style="display:block; margin-bottom:8px; font-weight:600; font-size: 14px; color: #475569;">Description / Details</label>
                <textarea name="description" rows="4" placeholder="Explain what was done to resolve this problem..." style="width:100%; border:1px solid #cbd5e1; border-radius:8px; padding:10px; font-size: 14px; background: #f8fafc; resize: vertical;"></textarea>
            </div>
            <div style="display:flex; justify-content:flex-end; gap: 10px; margin-top: 10px;">
                <button type="button" onclick="closeSolveModal()" style="background:#e2e8f0; color:#475569; border:none; padding:10px 20px; border-radius:8px; font-weight:600; cursor:pointer;">Cancel</button>
                <button type="submit" style="background:linear-gradient(135deg, #10b981 0%, #059669 100%); color:white; border:none; padding:10px 24px; border-radius:8px; font-weight:600; cursor:pointer; box-shadow: 0 4px 6px -1px rgba(16, 185, 129, 0.3);">Problem Solved</button>
            </div>
        </form>
    </div>
</div>