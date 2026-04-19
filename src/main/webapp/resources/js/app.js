/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function ajaxCall(method, url, data, destination, isHtml, callback) {
    let xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        if (this.readyState === 4) {
            if (this.status >= 200 && this.status < 300) {
                if (destination && document.getElementById(destination)) {
                    if (isHtml) {
                        document.getElementById(destination).innerHTML = this.responseText;
                    } else {
                        document.getElementById(destination).value = this.responseText;
                    }
                }
                if (callback) callback(null, this.responseText);
            } else {
                if (callback) callback(this.status, this.responseText);
            }
        }
    };
    xhttp.open(method, url, true);
    
    // Only set the header if the data is NOT a FormData object 
    // (FormData handles its own boundaries and Content-Type automatically)
    if (data && !(data instanceof FormData)) {
        xhttp.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
    }
    
    xhttp.send(data);
}

//function processOperation(process) {
//    alert(process);
//    ajaxCall("POST", "user/selectProcess", "process=" + process, "ajax", true);
//}
//// Insert user using AJAX
//function insertUser() {
//    let name = document.getElementById("name").value;
//    let email = document.getElementById("email").value;
//    let password = document.getElementById("password").value;
//    let mobile = document.getElementById("mobile").value;
//    let address = document.getElementById("address").value;
//
//    // Prepare POST data for ModelAttribute
//    let data = 'name=' + encodeURIComponent(name) +
//            '&email=' + encodeURIComponent(email) +
//            '&password=' + encodeURIComponent(password) +
//            '&mobile=' + encodeURIComponent(mobile) +
//            '&address=' + encodeURIComponent(address);
//
//    ajaxCall("POST", "user/save", data, "insertD", true);
//}
//
//function openUpdate(element, selectProcess) {
//    let id = element.getAttribute("Did");
//    let data = 'process=' + encodeURIComponent(selectProcess) +
//        '&userId=' + encodeURIComponent(id);;
//    ajaxCall("POST", "user/selectProcess", data, "ajax", true);
//}
//
//// Update user
//function updateUser() {
//    let userId = document.getElementById("userId").value;
//    let name = document.getElementById("name").value;
//    let email = document.getElementById("email").value;
//    let password = document.getElementById("password").value;
//    let mobile = document.getElementById("mobile").value;
//    let address = document.getElementById("address").value;
//
//    let data = 'userId=' + encodeURIComponent(userId) +
//            '&name=' + encodeURIComponent(name) +
//            '&email=' + encodeURIComponent(email) +
//            '&password=' + encodeURIComponent(password) +
//            '&mobile=' + encodeURIComponent(mobile) +
//            '&address=' + encodeURIComponent(address);
//
//    ajaxCall("POST", "user/update", data, "updateD", true);
//}
//
//// Delete user
//function deleteUser(element) {
//    let con = confirm("Are you sure you want to delete this user?");
//    if (con) {
//        let id = element.getAttribute("Did");
//        let data = 'userId=' + encodeURIComponent(id);
//
//        ajaxCall("POST", "user/delete", data, "delteRow", true);
//
//        if (document.getElementById("delteRow").innerHTML.trim() === "1") {
//            alert("Deleted Successfully");
//            processOperation("view"); // reload table
//        }
//    }
//}

const userTypeRadios = document.querySelectorAll('input[name="userType"]');
const solverDiv = document.getElementById('solverType');
const form = document.getElementById('regForm');

// Show/Hide solver type
userTypeRadios.forEach(r => {
    r.addEventListener('change', () => {
        if (solverDiv) {
            if (r.value === "solver" && r.checked) {
                solverDiv.style.display = "flex";
                document.querySelectorAll('input[name="solver"]').forEach(s => s.required = true);
            } else if (r.value === "user" && r.checked) {
                solverDiv.style.display = "none";
                document.querySelectorAll('input[name="solver"]').forEach(s => s.required = false);
            }
        }
    });
});

// Password match validation
if (form) {
    form.addEventListener('submit', function (e) {
        const pass = document.getElementById('pass');
        const confirm = document.getElementById('confirmPass');

        if (pass && confirm && pass.value !== confirm.value) {
            alert("Passwords do not match");
            e.preventDefault();
        }
    });
}

function goToRegeistration() {
//    let con = confirm("Are you sure you want to delete this user?");
//    if (con) {
//        let id = element.getAttribute("Did");
////        let data = 'userId=' + encodeURIComponent(id);

    ajaxCall("POST", "Solve/Registration", null, "mainPage", true);

}

function goToLoginPage() {
    window.location.href = 'http://localhost:8080/CrudMVC/';
}

function loginBtn() {
    console.log("log btn");
//        window.locaiton.href="login_Page.jsp";
    window.location.href = "Solve/login";
}
