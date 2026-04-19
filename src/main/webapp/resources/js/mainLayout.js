const sidebar = document.getElementById("sidebar");
const overlay = document.getElementById("overlay");

// Toggle button
function toggleSidebar() {
    if (window.innerWidth <= 768) {
        // MOBILE
        sidebar.classList.toggle("open");
        overlay.classList.toggle("show");
    } else {
        // DESKTOP
        sidebar.classList.toggle("closed");
    }
}

// Close when clicking overlay (called via onclick on #overlay)
function closeMobileSidebar() {
    sidebar.classList.remove("open");
    overlay.classList.remove("show");
}

function updateButtons(container, index, total) {
    const prevBtn = container.querySelector(".prev-btn");
    const nextBtn = container.querySelector(".next-btn");

    // Disable Prev if first image
    prevBtn.disabled = (index === 0);

    // Disable Next if last image
    nextBtn.disabled = (index === total - 1);
}

function nextSlide(button) {
    const container = button.closest(".slider-container");
    const slider = container.querySelector(".slider");
    const images = slider.querySelectorAll(".post-img");

    let currentIndex = slider.dataset.index ? parseInt(slider.dataset.index) : 0;

    if (currentIndex < images.length - 1) {
        currentIndex++;
        slider.style.transform = `translateX(-${currentIndex * 100}%)`;
        slider.dataset.index = currentIndex;
    }

    updateButtons(container, currentIndex, images.length);
}

function prevSlide(button) {
    const container = button.closest(".slider-container");
    const slider = container.querySelector(".slider");
    const images = slider.querySelectorAll(".post-img");

    let currentIndex = slider.dataset.index ? parseInt(slider.dataset.index) : 0;

    if (currentIndex > 0) {
        currentIndex--;
        slider.style.transform = `translateX(-${currentIndex * 100}%)`;
        slider.dataset.index = currentIndex;
    }

    updateButtons(container, currentIndex, images.length);
}

// Initialize buttons and active states on page load (called via body onload)
function initLayout() {
    document.querySelectorAll(".slider-container").forEach(container => {
        const slider = container.querySelector(".slider");
        const images = slider.querySelectorAll(".post-img");

        slider.dataset.index = 0;
        updateButtons(container, 0, images.length);
    });

    // Sidebar navigation link highlighter
    const currentUrl = window.location.pathname;
    const contextPath = window.APP_CONTEXT || '';

    let route = currentUrl;
    if (contextPath && currentUrl.startsWith(contextPath)) {
        route = currentUrl.substring(contextPath.length);
    }
    if (route === '') route = '/';

    function isLinkActive(linkHref) {
        try {
            const linkPath = new URL(linkHref).pathname;
            let linkRoute = linkPath;
            if (contextPath && linkPath.startsWith(contextPath)) {
                linkRoute = linkPath.substring(contextPath.length);
            }
            if (linkRoute === '') linkRoute = '/';

            if (linkRoute === '/') {
                return route === '/';
            }
            return route.startsWith(linkRoute);
        } catch (e) {
            return false;
        }
    }

    const menuLinks = document.querySelectorAll('.menu a');
    menuLinks.forEach(link => {
        if (link.getAttribute('href') === "#" || !link.getAttribute('href')) return;
        if (isLinkActive(link.href)) {
            if (link.closest('li')) link.closest('li').classList.add('active');
        } else {
            if (link.closest('li')) link.closest('li').classList.remove('active');
        }
    });

    const mobileLinks = document.querySelectorAll('.mobile-bottom-nav a');
    mobileLinks.forEach(link => {
        if (link.getAttribute('href') === "#" || !link.getAttribute('href')) return;
        if (isLinkActive(link.href)) {
            link.classList.add('active');
        } else {
            link.classList.remove('active');
        }
    });
}