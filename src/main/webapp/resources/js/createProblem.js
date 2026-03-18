const maxImages = 5;
let selectedFiles = []; // Array to hold File objects

function renderGrid() {
    const grid = document.getElementById('upload-grid');
    if (!grid)
        return;
    grid.innerHTML = '';

    for (let i = 0; i < maxImages; i++) {
        const slot = document.createElement('div');
        slot.className = 'upload-slot';

        if (i < selectedFiles.length) {
            // Render Uploaded Image
            const file = selectedFiles[i];
            const img = document.createElement('img');
            img.src = URL.createObjectURL(file);

            const removeBtn = document.createElement('button');
            removeBtn.className = 'remove-btn';
            removeBtn.innerHTML = '×';
            removeBtn.onclick = (e) => {
                e.preventDefault(); // Prevent form submission
                selectedFiles.splice(i, 1);
                updateHiddenInput();
                renderGrid();
            };

            slot.appendChild(img);
            slot.appendChild(removeBtn);
        } else if (i === selectedFiles.length) {
            // Render the active '+' add box
            slot.className = 'upload-slot active-empty';
            slot.innerHTML = '<span class="plus-icon">+</span><span class="slot-text">Add</span>';
            slot.onclick = () => document.getElementById('hidden-input').click();
        } else {
            // Render inactive empty slot
            slot.className = 'upload-slot inactive-empty';
        }

        grid.appendChild(slot);
    }
}

function updateHiddenInput() {
    const dataTransfer = new DataTransfer();
    selectedFiles.forEach(file => dataTransfer.items.add(file));
    document.getElementById('hidden-input').files = dataTransfer.files;
}

function handleImageUploadChange(input) {
    const newFiles = Array.from(input.files);

    for (let file of newFiles) {
        if (selectedFiles.length < maxImages && file.type.startsWith('image/')) {
            selectedFiles.push(file);
        }
    }

    updateHiddenInput();
    renderGrid();
}

function initCreateProblem() {
    // 1. Initialize Leaflet Map
    var map = L.map('map').setView([20.5937, 78.9629], 5);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    var marker;

    // 2. Handle Map Clicks
    map.on('click', function (e) {
        var lat = e.latlng.lat;
        var lng = e.latlng.lng;

        // Set inputs
        document.getElementById('latitude').value = lat.toFixed(6);
        document.getElementById('longitude').value = lng.toFixed(6);

        // Place or move marker
        if (marker) {
            marker.setLatLng(e.latlng);
        } else {
            marker = L.marker(e.latlng).addTo(map);
        }

        // 3. Optional: Reverse Geocoding (Getting rough address from Lat/Lng using Nominatim API)
        fetch(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`)
                .then(response => response.json())
                .then(data => {
                    if (data && data.display_name) {
                        document.getElementById('addressDescription').value = data.display_name;
                    }
                })
                .catch(error => {
                    console.error("Error fetching address:", error);
                });
    });

    // Initial render of empty boxes
    renderGrid();

    // Fetch Areas
    ajaxCall('GET', window.APP_CONTEXT + '/api/master/areas', null, null, false, function (err, responseText) {
        if (!err) {
            const data = JSON.parse(responseText);
            const areaSelect = document.getElementById('areaId');
            data.forEach(area => {
                let option = document.createElement('option');
                option.value = area.areaId;
                option.textContent = area.areaName + ' (' + area.pincode + ')';
                areaSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching Areas:", err);
        }
    });

    // Fetch Categories
    ajaxCall('GET', window.APP_CONTEXT + '/api/master/categories', null, null, false, function (err, responseText) {
        if (!err) {
            const data = JSON.parse(responseText);
            const catSelect = document.getElementById('categoryId');
            data.forEach(cat => {
                let option = document.createElement('option');
                option.value = cat.categoryId;
                option.textContent = cat.categoryName;
                catSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching Categories:", err);
        }
    });
}

function fetchSubCategories() {
    const categoryId = document.getElementById('categoryId').value;
    const subCatSelect = document.getElementById('subcategoryId');

    subCatSelect.innerHTML = '<option value="" disabled selected>Select specific issue...</option>';

    ajaxCall('GET', window.APP_CONTEXT + '/api/master/subcategories/' + categoryId, null, null, false, function (err, responseText) {
        if (!err) {
            const data = JSON.parse(responseText);
            data.forEach(sub => {
                let option = document.createElement('option');
                option.value = sub.subcategoryId;
                option.textContent = sub.subcategoryName;
                subCatSelect.appendChild(option);
            });
        } else {
            console.error("Error fetching Subcategories:", err);
        }
    });
}

function problemCreate() {
    console.log("problemCreate function triggered.");
//    const form = document.querySelector('.create-form');
//    let formData = new FormData(form);
    const form = document.getElementById('createForm');
    const formData = new FormData(form);
    console.log("Submitting formData");
        ajaxCall('POST', window.APP_CONTEXT + '/saveProblem', formData, null, false, function(err, responseText) {
            if (!err) {
                alert("Problem logged successfully!");
                window.location.href = window.APP_CONTEXT + '/'; 
            } else {
                alert("Submission failed. Error: " + responseText);
            }
        });

    return false;
}