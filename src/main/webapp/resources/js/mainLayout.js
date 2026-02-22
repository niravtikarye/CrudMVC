const sidebar = document.getElementById("sidebar");
const overlay = document.getElementById("overlay");

function toggleSidebar() {
    if (window.innerWidth <= 768) {
        sidebar.classList.toggle("active");
        overlay.classList.toggle("active");
    } else {
        sidebar.classList.toggle("closed");
    }
}

function closeMobileSidebar() {
    sidebar.classList.remove("active");
    overlay.classList.remove("active");
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

// Initialize buttons on page load
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".slider-container").forEach(container => {
        const slider = container.querySelector(".slider");
        const images = slider.querySelectorAll(".post-img");

        slider.dataset.index = 0;
        updateButtons(container, 0, images.length);
    });
});