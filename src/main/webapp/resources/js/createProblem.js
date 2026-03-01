document.addEventListener('DOMContentLoaded', function () {

    // 1. Initialize Leaflet Map
    // Setting default view to a central location (e.g., India context: New Delhi or user can let browser find them if we add Geolocation API later)
    var map = L.map('map').setView([20.5937, 78.9629], 5); // Default: Center of India

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
                    document.getElementById('address').value = data.display_name;
                }
            })
            .catch(error => {
                console.error("Error fetching address:", error);
            });
    });

    // 4. Handle 5-Box Incremental Image Uploads
    const maxImages = 5;
    let selectedFiles = []; // Array to hold File objects

    function renderGrid() {
        const grid = document.getElementById('upload-grid');
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
                // Render inactive empty slot (just dashes)
                slot.className = 'upload-slot inactive-empty';
            }

            grid.appendChild(slot);
        }
    }

    document.getElementById('hidden-input').addEventListener('change', function (e) {
        const newFiles = Array.from(e.target.files);

        for (let file of newFiles) {
            // Only add if we're under the limit and it's an image
            if (selectedFiles.length < maxImages && file.type.startsWith('image/')) {
                selectedFiles.push(file);
            }
        }

        updateHiddenInput();
        renderGrid();
    });

    function updateHiddenInput() {
        // Use DataTransfer to programmatically sync our array to the <input type="file"> so normal form submission works
        const dataTransfer = new DataTransfer();
        selectedFiles.forEach(file => dataTransfer.items.add(file));
        document.getElementById('hidden-input').files = dataTransfer.files;
    }

    // Initial render of empty boxes
    renderGrid();
});
