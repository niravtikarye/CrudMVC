<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>CivicSolve Dashboard</title>
    <link rel="stylesheet" href="/CrudMVC/resources/css/style.css" />
    <style>
        .container { max-width: 980px; margin: 24px auto; font-family: Arial, sans-serif; }
        header { display:flex; justify-content:space-between; align-items:center; }
        .card { border:1px solid #e0e0e0; padding:16px; border-radius:6px; margin-top:12px; }
        table { width:100%; border-collapse:collapse; }
        th, td { padding:8px; border-bottom:1px solid #eee; }
        .btn { padding:6px 10px; border-radius:4px; background:#1976d2; color:white; text-decoration:none; }
        .btn-muted { background:#6c757d; }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h1>CivicSolve — Dashboard</h1>
        <div>
            <a class="btn" href="#" onclick="showCreate()">New Problem</a>
        </div>
    </header>

    <section class="card">
        <h3>Problems</h3>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Assigned To</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="problems-body">
                <c:forEach var="p" items="${problems}">
                    <tr>
                        <td>${p.problemId}</td>
                        <td>${p.title}</td>
                        <td>${p.description}</td>
                        <td>${p.status}</td>
                        <td>${p.assignedTo}</td>
                        <td>
                            <a class="btn btn-muted" href="#" onclick="assign(${p.problemId})">Assign to me</a>
                            <a class="btn" href="#" onclick="deleteProblem(${p.problemId})">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>

    <section id="create-card" class="card" style="display:none;">
        <h3>Create Problem</h3>
        <form id="create-form" onsubmit="createProblem(event)">
            <div>
                <label>Title</label><br/>
                <input type="text" id="title" style="width:100%" required />
            </div>
            <div>
                <label>Description</label><br/>
                <textarea id="description" style="width:100%" rows="4" required></textarea>
            </div>
            <div style="margin-top:8px;">
                <button class="btn" type="submit">Create</button>
                <button type="button" class="btn btn-muted" onclick="hideCreate()">Cancel</button>
            </div>
        </form>
    </section>
</div>

<script>
    function showCreate(){ document.getElementById('create-card').style.display='block'; }
    function hideCreate(){ document.getElementById('create-card').style.display='none'; }

    async function refresh(){
        const res = await fetch('/CrudMVC/Solve/api/problems');
        const data = await res.json();
        const tbody = document.getElementById('problems-body');
        tbody.innerHTML = '';
        data.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `<td>${p.problemId}</td><td>${p.title}</td><td>${p.description}</td><td>${p.status}</td><td>${p.assignedTo || ''}</td><td><a class='btn btn-muted' href='#' onclick='assign(${p.problemId})'>Assign to me</a> <a class='btn' href='#' onclick='deleteProblem(${p.problemId})'>Delete</a></td>`;
            tbody.appendChild(tr);
        });
    }

    async function createProblem(e){
        e.preventDefault();
        const title = document.getElementById('title').value;
        const description = document.getElementById('description').value;
        await fetch('/CrudMVC/Solve/api/problems?creatorId=1', { method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({title, description}) });
        hideCreate();
        refresh();
    }

    async function assign(id){
        // assign to dummy solver id 2
        await fetch('/CrudMVC/Solve/api/problems/' + id + '/assign?solverId=2', { method:'POST' });
        refresh();
    }

    async function deleteProblem(id){
        await fetch('/CrudMVC/Solve/api/problems/' + id, { method:'DELETE' });
        refresh();
    }

    // initial load
    refresh();
</script>
</body>
</html>
