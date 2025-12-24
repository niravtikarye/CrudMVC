/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function ajaxCall(method, url, data, destination, isHtml) {
    let xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (isHtml) {
                document.getElementById(destination).innerHTML = this.responseText;
            } else {
                document.getElementById(destination).value = this.responseText;
            }
        }
    };
    xhttp.open(method, url, false);
    xhttp.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
    xhttp.send(data);
}

function processOperation(process) {
    alert(process);
    ajaxCall("POST", "user/selectProcess", "process=" + process, "ajax", true);
}
// Insert user using AJAX
function insertUser() {
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let mobile = document.getElementById("mobile").value;
    let address = document.getElementById("address").value;

    // Prepare POST data for ModelAttribute
    let data = 'name=' + encodeURIComponent(name) +
            '&email=' + encodeURIComponent(email) +
            '&password=' + encodeURIComponent(password) +
            '&mobile=' + encodeURIComponent(mobile) +
            '&address=' + encodeURIComponent(address);

    ajaxCall("POST", "user/save", data, "insertD", true);
}

function openUpdate(element, selectProcess) {
    let id = element.getAttribute("Did");
    let data = 'process=' + encodeURIComponent(selectProcess) +
        '&userId=' + encodeURIComponent(id);;
    ajaxCall("POST", "user/selectProcess", data, "ajax", true);
}

// Update user
function updateUser() {
    let userId = document.getElementById("userId").value;
    let name = document.getElementById("name").value;
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let mobile = document.getElementById("mobile").value;
    let address = document.getElementById("address").value;

    let data = 'userId=' + encodeURIComponent(userId) +
            '&name=' + encodeURIComponent(name) +
            '&email=' + encodeURIComponent(email) +
            '&password=' + encodeURIComponent(password) +
            '&mobile=' + encodeURIComponent(mobile) +
            '&address=' + encodeURIComponent(address);

    ajaxCall("POST", "user/update", data, "updateD", true);
}

// Delete user
function deleteUser(element) {
    let con = confirm("Are you sure you want to delete this user?");
    if (con) {
        let id = element.getAttribute("Did");
        let data = 'userId=' + encodeURIComponent(id);

        ajaxCall("POST", "user/delete", data, "delteRow", true);

        if (document.getElementById("delteRow").innerHTML.trim() === "1") {
            alert("Deleted Successfully");
            processOperation("view"); // reload table
        }
    }
}