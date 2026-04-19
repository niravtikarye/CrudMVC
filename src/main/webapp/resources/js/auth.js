// script for login and register interactions

function handleLogin(event) {
    event.preventDefault();
    
    const form = document.getElementById('loginForm');
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    ajaxCall('POST', window.APP_CONTEXT + '/api/auth/login', params, null, false, function(err, responseText) {
        if (!err) {
            window.location.href = window.APP_CONTEXT + '/';
        } else {
            alert(responseText || "Invalid credentials. Please try again.");
        }
    });
}

function initRegister() {
    const orgSelect = document.getElementById('organizationId');
    if (orgSelect) {
        ajaxCall('GET', window.APP_CONTEXT + '/api/master/organizations', null, null, false, function(err, responseText) {
            if (!err) {
                const data = JSON.parse(responseText);
                data.forEach(org => {
                    if (org.organizationId === 1 || org.organizationName === 'VMC Official') return;
                    let option = document.createElement('option');
                    option.value = org.organizationId;
                    option.textContent = org.organizationName;
                    orgSelect.appendChild(option);
                });
                
                let newOption = document.createElement('option');
                newOption.value = 'new';
                newOption.textContent = '-- Add New Organization --';
                orgSelect.appendChild(newOption);
            } else {
                console.error("Error fetching Organizations:", err);
            }
        });
    }
}

function toggleOrganizationField() {
    const role = document.getElementById('role').value;
    const orgGroup = document.getElementById('organization-group');
    const orgSelect = document.getElementById('organizationId');
    const newOrgGroup = document.getElementById('new-organization-group');

    if (role === 'ngo') {
        orgGroup.style.display = 'block';
        orgSelect.required = true;
        handleOrgSelectChange();
    } else {
        orgGroup.style.display = 'none';
        orgSelect.required = false;
        orgSelect.value = "";
        if (newOrgGroup) {
            newOrgGroup.style.display = 'none';
            document.getElementById('newOrganizationName').required = false;
        }
    }
}

function handleOrgSelectChange() {
    const orgSelect = document.getElementById('organizationId');
    const newOrgGroup = document.getElementById('new-organization-group');
    if (!newOrgGroup) return;

    if (orgSelect.value === 'new') {
        newOrgGroup.style.display = 'block';
        document.getElementById('newOrganizationName').required = true;
    } else {
        newOrgGroup.style.display = 'none';
        document.getElementById('newOrganizationName').required = false;
    }
}

function handleRegister(event) {
    event.preventDefault();
    
    const form = document.getElementById('registerForm');
    
    const orgSelect = document.getElementById('organizationId');
    const wasNew = (orgSelect && orgSelect.value === 'new');
    if (wasNew) {
        orgSelect.disabled = true;
    }

    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    if (wasNew) {
        orgSelect.disabled = false;
    }

    ajaxCall('POST', window.APP_CONTEXT + '/api/auth/register', params, null, false, function(err, responseText) {
        if (!err) {
            alert("Registration successful! Please log in.");
            window.location.href = window.APP_CONTEXT + '/login';
        } else {
            alert(responseText || "Registration failed. Please try again.");
        }
    });
}
