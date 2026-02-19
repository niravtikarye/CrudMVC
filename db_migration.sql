-- Add role column to users table (crud)
ALTER TABLE crud
    ADD COLUMN IF NOT EXISTS role VARCHAR(20) DEFAULT 'CITIZEN';

-- Create problems table
CREATE TABLE IF NOT EXISTS problems (
    problem_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_by INTEGER NOT NULL,
    assigned_to INTEGER,
    status VARCHAR(50) DEFAULT 'OPEN',
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);

-- Optional: add foreign keys if you maintain referential integrity
-- ALTER TABLE problems ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES crud(user_id);
-- ALTER TABLE problems ADD CONSTRAINT fk_assigned_to FOREIGN KEY (assigned_to) REFERENCES crud(user_id);
