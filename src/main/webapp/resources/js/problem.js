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
    if(element && element.classList && element.classList.contains("assigned")) return; // Prevent re-assigning

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
    
    const url = window.APP_CONTEXT + '/api/problems/' + probId + '/assign?solverId=' + solverId ;

    ajaxCall('POST', url, null, null, false, function(err, responseText) {
        if (!err) {
            alert("Problem successfully assigned to you!");
            location.reload(); // Refresh to show IN_PROGRESS status
        } else {
            alert(responseText || "Failed to assign problem.");
        }
    });
}

function solveProblem(event, optionalProbId) {
    event.preventDefault();
    const form = event.target;
    const probId = optionalProbId || form.getAttribute("data-probid");
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

function rejectProblem(probId) {
    if (!confirm("Are you sure you want to reject and unassign this problem from yourself?")) return;
    
    const url = window.APP_CONTEXT + '/api/problems/' + probId + '/unassign';
    
    ajaxCall('POST', url, null, null, false, function(err, responseText) {
        if (!err) {
            alert("Problem rejected successfully!");
            location.reload();
        } else {
            alert(responseText || "Failed to reject problem.");
        }
    });
}

// Dropdown toggle logic
function toggleDropdown(dropdownId, event) {
    event.stopPropagation();
    const dropdown = document.getElementById(dropdownId);
    
    // Close other open dropdowns first
    document.querySelectorAll('.dropdown-content').forEach(el => {
        if(el.id !== dropdownId) el.style.display = 'none';
    });
    
    if (dropdown.style.display === "none" || dropdown.style.display === "") {
        dropdown.style.display = "block";
    } else {
        dropdown.style.display = "none";
    }
}

// Close dropdowns if clicked outside
document.addEventListener("click", function(event) {
    document.querySelectorAll('.dropdown-content').forEach(el => {
        if (el.style.display === "block") {
            el.style.display = "none";
        }
    });
});

function openSolveModal(probId) {
    // Attempt local modal first for backward compatibility (dashboard ProblemCard)
    let modal = document.getElementById("solve-modal-" + probId);
    if(modal) {
        modal.style.display = "flex";
        setTimeout(() => modal.classList.add("show"), 10);
        return;
    }
    // Otherwise open global overlay
    modal = document.getElementById("solve-modal-global");
    if(modal) {
        document.getElementById("solve-form-global").setAttribute("data-probid", probId);
        modal.style.display = "flex";
        setTimeout(() => modal.classList.add("show"), 10);
    }
}

function closeSolveModal(optionalProbId) {
    if (optionalProbId) {
        const localModal = document.getElementById("solve-modal-" + optionalProbId);
        if(localModal) {
            localModal.classList.remove("show");
            setTimeout(() => localModal.style.display = "none", 300);
        }
    }
    const globalModal = document.getElementById("solve-modal-global");
    if(globalModal) {
        globalModal.classList.remove("show");
        setTimeout(() => globalModal.style.display = "none", 300);
    }
}

// Placeholder for Creator UI Actions
function editProblem(probId) {
    alert("Edit problem feature coming soon! (ID: " + probId + ")");
    // Hook up location.href = ... or ajax logic here
}

function deleteProblem(probId) {
    if(!confirm("Are you sure you want to delete this problem?")) return;
    alert("Delete problem API not yet implemented for ID: " + probId);
    // Hook up ajaxCall to delete endpoint here
}
