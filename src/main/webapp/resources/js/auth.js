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
                    let option = document.createElement('option');
                    option.value = org.organizationId;
                    option.textContent = org.organizationName;
                    orgSelect.appendChild(option);
                });
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

    if (role === 'ngo' || role === 'vmc') {
        orgGroup.style.display = 'block';
        orgSelect.required = true;
    } else {
        orgGroup.style.display = 'none';
        orgSelect.required = false;
        orgSelect.value = ""; 
    }
}

function handleRegister(event) {
    event.preventDefault();
    
    const form = document.getElementById('registerForm');
    const formData = new FormData(form);
    const params = new URLSearchParams(formData);

    ajaxCall('POST', window.APP_CONTEXT + '/api/auth/register', params, null, false, function(err, responseText) {
        if (!err) {
            alert("Registration successful! Please log in.");
            window.location.href = window.APP_CONTEXT + '/login';
        } else {
            alert(responseText || "Registration failed. Please try again.");
        }
    });
}
