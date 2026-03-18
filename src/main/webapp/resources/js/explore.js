function openProblemInfo(element) {
    // Get data from attributes
    const title = element.getAttribute('data-title');
    const desc = element.getAttribute('data-desc');
    const hipe = element.getAttribute('data-hipe');
    const status = element.getAttribute('data-status');
    const image = element.getAttribute('data-image');

    // Populate modal fields
    document.getElementById('pi-title').textContent = title;
    document.getElementById('pi-desc').textContent = desc;
    document.getElementById('pi-hipe-val').textContent = hipe + ' Likes';
    document.getElementById('pi-status-val').textContent = 'Status: ' + status;
    document.getElementById('pi-image').src = image;

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
                document.getElementById('pi-hipe-val').textContent = card.getAttribute('data-hipe');
                document.getElementById('pi-status-val').textContent = card.getAttribute('data-status');
                document.getElementById('pi-image').src = card.getAttribute('data-image');

                const modal = document.getElementById('problem-info-modal');
                modal.style.display = 'flex';
                setTimeout(() => { modal.classList.add('show'); }, 10);

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

