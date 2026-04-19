let currentCitizenImages = [];
let currentCitizenIndex = 0;
let activeProbId = null;
let currentHypeCount = 0;

function openProblemInfo(element) {
    // Get data from attributes
    const title = element.getAttribute('data-title');
    const desc = element.getAttribute('data-desc');
    const hipe = element.getAttribute('data-hipe');
    const status = element.getAttribute('data-status');
    const isHyped = element.getAttribute('data-is-hyped') === 'true';
    const areaName = element.getAttribute('data-area-name') || '';
    const addressDesc = element.getAttribute('data-address') || '';
    const solverDesc = element.getAttribute('data-solver-desc') || '';
    
    // Arrays
    const rawCitizen = element.getAttribute('data-citizen-images');
    currentCitizenImages = rawCitizen ? rawCitizen.split('|||').filter(s => s.trim() !== '') : [];
    const rawSolver = element.getAttribute('data-solver-images');
    const solverImages = rawSolver ? rawSolver.split('|||').filter(s => s.trim() !== '') : [];
    
    // Auth & Meta
    activeProbId = element.getAttribute('data-prob-id');
    const problemUserId = element.getAttribute('data-user-id');
    const problemSolverId = element.getAttribute('data-solver-id');
    const loggedInId = window.USER_ID;
    const loggedInRole = window.USER_ROLE;

    // Populate modal fields
    document.getElementById('pi-title').textContent = title;
    document.getElementById('pi-desc').textContent = desc;
    currentHypeCount = parseInt(hipe) || 0;
    document.getElementById('pi-hipe-val').textContent = currentHypeCount + ' Hypes';
    
    const areaValEl = document.getElementById('pi-area-val');
    if(areaValEl) areaValEl.textContent = areaName ? ('📍 ' + areaName) : '';
    
    const addressValEl = document.getElementById('pi-address-val');
    if(addressValEl) addressValEl.textContent = 'Address : '+addressDesc;

    document.getElementById('pi-status-text').textContent = 'Status: ' + status;

    // Set hype button appearance accurately from the fetched state
    const hypeBtn = document.getElementById('pi-btn-hype');
    if (hypeBtn) {
        if (isHyped) {
            hypeBtn.innerHTML = '<span class="btn-text">💔 Un-Hype</span>';
        } else {
            hypeBtn.innerHTML = '<span class="btn-text">❤ Hype It!</span>';
        }
    }

    // Slider Initialization
    currentCitizenIndex = 0;
    document.getElementById('pi-image').src = currentCitizenImages.length > 0 ? currentCitizenImages[0] : 'https://i.pinimg.com/736x/00/0d/9c/000d9c727330e506be6d8ee2497cde54.jpg';
    
    document.getElementById('pi-slider-prev').style.display = currentCitizenImages.length > 1 ? 'block' : 'none';
    document.getElementById('pi-slider-next').style.display = currentCitizenImages.length > 1 ? 'block' : 'none';

    // Right Panel Setup (Solver Image)
    const rightPanel = document.getElementById('solverPanel');
    const mobileBtn = document.getElementById('pi-btn-mobile-solver');
    if (solverImages.length > 0 || (solverDesc && (status === 'RESOLVED' || status === 'VERIFIED'))) {
        rightPanel.style.display = 'flex';
        mobileBtn.style.display = 'block';
        
        const solverImgEl = document.getElementById('pi-solver-image');
        if (solverImages.length > 0) {
            solverImgEl.src = solverImages[0];
            solverImgEl.style.display = 'block';
        } else {
            solverImgEl.style.display = 'none';
        }
        
        const solverDescEl = document.getElementById('pi-solver-desc');
        if (solverDescEl) {
            if (solverDesc) {
                solverDescEl.textContent = '✔️ ' + solverDesc;
                solverDescEl.style.display = 'block';
            } else {
                solverDescEl.style.display = 'none';
            }
        }
    } else {
        rightPanel.style.display = 'none';
        mobileBtn.style.display = 'none';
    }

    // --- Dynamic Action Buttons ---
    const solveBtn = document.getElementById('pi-btn-solve');
    const rejectBtn = document.getElementById('pi-btn-reject');
    const assignBtn = document.getElementById('pi-btn-assign');
    const editBtn = document.getElementById('pi-btn-edit');
    const deleteBtn = document.getElementById('pi-btn-delete');
    const verifyGroup = document.getElementById('pi-verify-group');

    // Reset visibility
    [solveBtn, rejectBtn, assignBtn, editBtn, deleteBtn, verifyGroup].forEach(b => b.style.display = 'none');

    // Only configure actions if logged in
    if (loggedInId) {
        if (loggedInRole !== 'citizen') {
            // Unassigned -> Assign to me
            if (!problemSolverId || problemSolverId === '0') {
               assignBtn.style.display = 'block';
            } 
            // Assigned to me && IN_PROGRESS -> Solve / Reject
            else if (status === 'IN_PROGRESS' && problemSolverId === loggedInId) {
               solveBtn.style.display = 'block';
               rejectBtn.style.display = 'block';
            }
        } 
        else if (loggedInRole === 'citizen') {
            // Creator Actions
            if (problemUserId === loggedInId) {
                editBtn.style.display = 'block';
                deleteBtn.style.display = 'block';
                
                // Verify Actions
                if (status === 'RESOLVED' || status === 'SOLVED') {
                    verifyGroup.style.display = 'flex';
                }
            }
        }
    }

    // Show modal
    const modal = document.getElementById('problem-info-modal');
    modal.style.display = 'flex';
    // Small delay to allow display:flex to apply before adding class for transition
    setTimeout(() => {
        modal.classList.add('show');
    }, 10);

    // Push state to History API
    const newUrl = new URL(window.location);
    newUrl.searchParams.set('problem', encodeURIComponent(title));
    window.history.pushState({ modalOpen: true, title: title }, '', newUrl);
}

// SLIDER LOGIC
function piPrevImage(e) {
    if(e) e.stopPropagation();
    if(currentCitizenImages.length <= 1) return;
    currentCitizenIndex = (currentCitizenIndex - 1 + currentCitizenImages.length) % currentCitizenImages.length;
    document.getElementById('pi-image').src = currentCitizenImages[currentCitizenIndex];
}

function piNextImage(e) {
    if(e) e.stopPropagation();
    if(currentCitizenImages.length <= 1) return;
    currentCitizenIndex = (currentCitizenIndex + 1) % currentCitizenImages.length;
    document.getElementById('pi-image').src = currentCitizenImages[currentCitizenIndex];
}

// ACTION WRAPPERS
function actionToggleHype() {
    if (!activeProbId) return;

    if (!window.USER_ID) {
        alert("You must be logged in to upvote.");
        return;
    }

    const btn = document.getElementById('pi-btn-hype');
    btn.disabled = true;

    ajaxCall('POST', window.APP_CONTEXT + '/api/problems/' + activeProbId + '/hype', null, null, false, function(err, responseText) {
        btn.disabled = false;
        if (!err) {
            if (responseText.includes("added")) {
                currentHypeCount++;
                document.getElementById('pi-hipe-val').textContent = currentHypeCount + ' Hypes';
                btn.innerHTML = '<span class="btn-text">💔 Un-Hype</span>';
                updateFeedCardHypeCount(activeProbId, currentHypeCount, true);
            } else if (responseText.includes("removed")) {
                currentHypeCount = Math.max(0, currentHypeCount - 1);
                document.getElementById('pi-hipe-val').textContent = currentHypeCount + ' Hypes';
                btn.innerHTML = '<span class="btn-text">❤ Hype It!</span>';
                updateFeedCardHypeCount(activeProbId, currentHypeCount, false);
            } else {
                alert(responseText);
            }
        } else {
            alert(responseText || "Failed to toggle hype.");
        }
    });
}

function updateFeedCardHypeCount(probId, count, isHypedNow) {
    const cards = document.querySelectorAll('.explore-card');
    cards.forEach(card => {
        if (card.getAttribute('data-prob-id') == probId) {
            card.setAttribute('data-hipe', count);
            card.setAttribute('data-is-hyped', isHypedNow ? 'true' : 'false');
            const overlayContent = card.querySelector('.overlay-content span:first-child');
            if (overlayContent) {
                overlayContent.textContent = '❤ ' + count + '️ ';
            }
        }
    });
}

function actionSolve() {
    if (activeProbId) {
        openSolveModal(activeProbId);
        // Do NOT close the parent ProblemInfo modal, since the solve modal overlay is nested inside it!
    }
}
function actionReject() {
    if (activeProbId) {
        rejectProblem(activeProbId);
        closeProblemInfo();
    }
}
function actionAssign() {
    if (activeProbId) {
        assignProblem(null, activeProbId);
        closeProblemInfo();
    }
}
function actionEdit() {
    if (activeProbId) {
        window.location.href = window.APP_CONTEXT + "/createProblem?editProbId=" + activeProbId;
    }
}
function actionDelete() {
    if (activeProbId) {
        if(confirm("Are you sure you want to permanently delete this issue and all associated images?")) {
            ajaxCall('POST', window.APP_CONTEXT + '/api/problems/' + activeProbId + '/delete', null, null, false, function(err, responseText) {
                if(!err) {
                    alert("Issue deleted successfully.");
                    window.location.reload();
                } else {
                    alert("Failed to delete issue.");
                }
            });
        }
    }
}
function actionVerifyAccept() {
    if (activeProbId) {
        if (confirm("Are you sure this problem is fully resolved? It will be permanently marked as VERIFIED.")) {
            verifyProblem(activeProbId, 'VERIFIED');
            closeProblemInfo();
        }
    }
}
function actionVerifyReject() {
    if (activeProbId) {
        if (confirm("Are you sure this problem is not genuinely resolved? It will be REOPENED and sent back for further work.")) {
            verifyProblem(activeProbId, 'REOPENED');
            closeProblemInfo();
        }
    }
}

function closeProblemInfo(isFromHistory = false) {
    const modal = document.getElementById('problem-info-modal');
    modal.classList.remove('show');

    // Wait for transition to finish
    setTimeout(() => {
        modal.style.display = 'none';
    }, 300);

    // If it was closed by clicking the button (not the back button), update history
    if (!isFromHistory) {
        const newUrl = new URL(window.location);
        newUrl.searchParams.delete('problem');
        window.history.pushState({}, '', newUrl);
    }
}

// Handle Back Button natively via window
window.onpopstate = function(event) {
    if (!event.state || !event.state.modalOpen) {
        closeProblemInfo(true);
    }
};

// Check if loaded with a problem in URL (called via body onload)
function initExplore() {
    const params = new URLSearchParams(window.location.search);
    const probTitle = params.get('problem');

    if (probTitle) {
        const decodedTitle = decodeURIComponent(probTitle);
        // Find the card with this title and click it programmatically
        const cards = document.querySelectorAll('.explore-card');
        for (let card of cards) {
            if (card.getAttribute('data-title') === decodedTitle) {
                // Prevent duplicate pushState on initial load by overriding history inside the click or just calling logic inline
                // Here we call the logic directly to avoid pushState
                document.getElementById('pi-title').textContent = card.getAttribute('data-title');
                document.getElementById('pi-desc').textContent = card.getAttribute('data-desc');

                const modal = document.getElementById('problem-info-modal');
                // Use the main initializer function to reliably fire slider/logic
                openProblemInfo(card);
                
                // Replace state so the initial load is recognized correctly
                window.history.replaceState({ modalOpen: true, title: decodedTitle }, '', window.location.href);
                break;
            }
        }
    } else {
        // Initial state
        window.history.replaceState({ modalOpen: false }, '', window.location.href);
    }
}

function toggleSolverPanel() {
    const panel = document.getElementById('solverPanel');
    if (panel.classList.contains('active')) {
        panel.classList.remove('active');
    } else {
        panel.classList.add('active');
    }
}


// Mobile Description Toggle
function toggleDesc(event) {
    if (event) {
        event.stopPropagation();
    }
    console.log("event : ",event);
    const leftPanel = document.querySelector('.problem-left');
    if (leftPanel) {
        leftPanel.classList.toggle('show-desc');
    }
}

