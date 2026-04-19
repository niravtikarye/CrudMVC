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
    let overflow = false;

    for (let file of newFiles) {
        if (selectedFiles.length < maxImages && file.type.startsWith('image/')) {
            selectedFiles.push(file);
        } else if (selectedFiles.length >= maxImages) {
            overflow = true;
        }
    }
    
    if (overflow) {
        alert("Maximum 5 images allowed; extra selections discarded.");
    }

    updateHiddenInput();
    renderGrid();
}

async function resolveImageToFile(url, idx) {
    try {
        if (url.startsWith('data:')) {
            let arr = url.split(',');
            let mime = 'image/jpeg';
            let match = arr[0].match(/:(.*?);/);
            if (match) mime = match[1];
            let bstr = atob(arr[1]);
            let n = bstr.length;
            let u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], "edited_image_" + idx + ".jpg", { type: mime });
        } else {
            let fetchUrl = url;
            if (!fetchUrl.startsWith('http')) {
                let prefix = window.APP_CONTEXT || '';
                // prevent duplicated slashes
                if (fetchUrl.startsWith('/')) {
                    fetchUrl = fetchUrl.substring(1);
                }
                if (!prefix.endsWith('/')) {
                    prefix += '/';
                }
                fetchUrl = prefix + fetchUrl;
            }
            const response = await fetch(fetchUrl);
            const blob = await response.blob();
            return new File([blob], "edited_image_" + idx + ".jpg", { type: blob.type || "image/jpeg" });
        }
    } catch(e) {
        console.error("Failed to hydrate image:", e);
        return null;
    }
}

function initCreateProblem() {
    // 0. Read Edit Configuration securely from DOM
    const editConfigEl = document.getElementById('editConfig');
    if (editConfigEl) {
        window.EDIT_MODE = editConfigEl.getAttribute('data-mode') === 'true';
        window.EDIT_AREA_ID = editConfigEl.getAttribute('data-area');
        window.EDIT_CATEGORY_ID = editConfigEl.getAttribute('data-category');
        window.EDIT_SUBCATEGORY_ID = editConfigEl.getAttribute('data-subcategory');
    }

    window.EDIT_CITIZEN_IMAGES = [];
    const imgDataSpans = document.querySelectorAll('.citizen-img-data');
    imgDataSpans.forEach(span => {
        const text = span.textContent.trim();
        if (text) {
            window.EDIT_CITIZEN_IMAGES.push(text);
        }
    });



    if (window.EDIT_MODE && window.EDIT_CITIZEN_IMAGES && window.EDIT_CITIZEN_IMAGES.length > 0) {
        Promise.all(window.EDIT_CITIZEN_IMAGES.map((url, idx) => resolveImageToFile(url, idx)))
            .then(files => {
                files.forEach(f => {
                    if (f && selectedFiles.length < maxImages) {
                        selectedFiles.push(f);
                    }
                });
                updateHiddenInput();
                renderGrid();
            });
    } else {
        renderGrid();
    }

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
            if (window.EDIT_AREA_ID) {
                Array.from(areaSelect.options).forEach(opt => {
                    if (String(opt.value) === String(window.EDIT_AREA_ID)) {
                        opt.selected = true;
                    }
                });
            }
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
            if (window.EDIT_CATEGORY_ID) {
                Array.from(catSelect.options).forEach(opt => {
                    if (String(opt.value) === String(window.EDIT_CATEGORY_ID)) {
                        opt.selected = true;
                    }
                });
                fetchSubCategories();
            }
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
            if (window.EDIT_SUBCATEGORY_ID) {
                Array.from(subCatSelect.options).forEach(opt => {
                    if (String(opt.value) === String(window.EDIT_SUBCATEGORY_ID)) {
                        opt.selected = true;
                    }
                });
            }
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