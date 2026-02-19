JWT integration and RBAC (CITIZEN / SOLVER)

Quick steps

1. Update secret: open `src/main/java/com/web/crudmvc/security/JwtUtil.java` and replace the `SECRET` constant with a secure, environment-sourced secret.
2. Apply DB migration: run the SQL in `db_migration.sql` against your Postgres database to add `role` (if missing) and create the `problems` table.
3. Build the project: `mvn clean package` and redeploy to Tomcat.

API endpoints (examples)

- Login (get JWT):

  ```bash
  curl -X POST 'http://localhost:8080/CrudMVC/api/auth/login' -d 'email=alice@example.com' -d 'password=secret'
  ```

  Response: JSON with `token` field.

- Public: list problems

  ```bash
  curl 'http://localhost:8080/CrudMVC/api/problems'
  ```

- Create problem (CITIZEN only)

  ```bash
  curl -X POST 'http://localhost:8080/CrudMVC/api/problems' \
    -H "Authorization: Bearer <TOKEN>" \
    -H "Content-Type: application/json" \
    -d '{"title":"Pothole","description":"Big pothole on 5th st"}'
  ```

- Update problem (owner only)

  ```bash
  curl -X PUT 'http://localhost:8080/CrudMVC/api/problems/1' \
    -H "Authorization: Bearer <TOKEN>" \
    -H "Content-Type: application/json" \
    -d '{"title":"Pothole fixed?","description":"update text"}'
  ```

- Assign (SOLVER only)

  ```bash
  curl -X POST 'http://localhost:8080/CrudMVC/api/problems/1/assign' \
    -H "Authorization: Bearer <TOKEN>"
  ```

Notes

- The servlet filter `com.web.crudmvc.security.AuthFilter` protects `/api/*` and sets `request` attributes `userId` and `role` when a valid JWT is provided.
- Controller methods read those attributes to enforce ownership and role checks.
- This is a minimal integration to demonstrate JWT + RBAC. For production use, move secrets to environment variables, add HTTPS, add robust error handling, and consider using Spring Security for a more complete solution.
