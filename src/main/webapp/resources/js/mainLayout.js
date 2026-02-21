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
}

function prevSlide(button) {
    const container = button.closest(".slider-container");
    const slider = container.querySelector(".slider");

    let currentIndex = slider.dataset.index ? parseInt(slider.dataset.index) : 0;

    if (currentIndex > 0) {
        currentIndex--;
        slider.style.transform = `translateX(-${currentIndex * 100}%)`;
        slider.dataset.index = currentIndex;
    }
}