function hypeProblem(element, probId) {
    if(element.classList.contains("hyped")) return; // Prevent spam clicking visually

    const userId = window.USER_ID;
    if (userId == 0 || !userId) {
        alert("Please log in to upvote problems!");
        window.location.href = window.APP_CONTEXT + '/login';
        return;
    }

    const url = window.APP_CONTEXT + '/api/problems/' + probId + '/hype?userId=' + userId;
    
    ajaxCall('POST', url, null, null, false, function(err, responseText) {
        if (!err) {
            // Change UI color
            element.classList.add("hyped");
            element.style.color = "var(--primary-color)"; 
            
            // Increment number smoothly
            let countSpan = document.getElementById('hype-count-' + probId);
            let currentCount = parseInt(countSpan.innerText) || 0;
            countSpan.innerText = currentCount + 1;
        } else if(err === 409) {
            alert("You have already hyped this problem!");
        } else {
            console.error("Error hyping problem:", responseText);
        }
    });
}

function assignProblem(element, probId) {
    if(element.classList.contains("assigned")) return; // Prevent re-assigning

    const solverId = window.USER_ID;
    const role = window.USER_ROLE;
    
    if (solverId == 0 || !solverId) {
        alert("Please log in to assign problems!");
        window.location.href = window.APP_CONTEXT + '/login';
        return;
    }

    if (role === 'citizen') {
        alert("Citizens cannot assign problems. This action is reserved for Solvers.");
        return;
    }

    // --- NEW POPUP LOGIC ---
    // Populate hidden ID and reset form
    document.getElementById('assign-prob-id').value = probId;
    document.getElementById('assign-estimate').value = "";
    document.getElementById('assign-notes').value = "";

    const modal = document.getElementById('assign-modal');
    modal.style.display = 'flex';
    setTimeout(() => modal.classList.add('show'), 10);
}

function closeAssignModal() {
    const modal = document.getElementById('assign-modal');
    modal.classList.remove('show');
    setTimeout(() => modal.style.display = 'none', 300);
}

function submitAssignForm(event) {
    event.preventDefault();

    const probId = document.getElementById('assign-prob-id').value;
    const hours = document.getElementById('assign-estimate').value;
    const notes = document.getElementById('assign-notes').value;
    const solverId = window.USER_ID;

    const formData = new FormData();
    formData.append("solverId", solverId);
    formData.append("assignedBy", solverId);
    formData.append("estimatedTime", hours + " hours");
    formData.append("notes", notes);

    const url = window.APP_CONTEXT + '/api/problems/' + probId + '/assign';

    ajaxCall('POST', url, formData, null, false, function(err, responseText) {
        if (!err) {
            alert("Problem successfully assigned to you!");
            closeAssignModal();
            location.reload(); // Refresh to show IN_PROGRESS status
        } else {
            alert(responseText || "Failed to assign problem.");
        }
    });
}

function solveProblem(event, probId) {
    event.preventDefault();
    const form = document.getElementById("solve-form-" + probId);
    const formData = new FormData(form);

    const url = window.APP_CONTEXT + '/api/problems/' + probId + '/solve';

    ajaxCall('POST', url, formData, null, false, function(err, responseText) {
        if (!err) {
            alert("Proof uploaded successfully. Problem marked as Resolved!");
            location.reload(); // Refresh to update status natively via server
        } else {
            alert(responseText || "Failed to submit resolution.");
        }
    });
}

function verifyProblem(probId, status) {
    const formData = new FormData();
    formData.append("status", status);

    const url = window.APP_CONTEXT + '/api/problems/' + probId + '/verify';

    ajaxCall('POST', url, formData, null, false, function(err, responseText) {
        if (!err) {
            alert(status === 'VERIFIED' ? "Thanks for verifying! Ticket closed." : "Ticket rejected and re-opened for review.");
            location.reload(); // Refresh to update UI
        } else {
            alert(responseText || "Action failed.");
        }
    });
}
