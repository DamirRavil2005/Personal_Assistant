-- Initial migration — placeholder
-- Real schema will be added in V2__create_users_table.sql
CREATE TABLE IF NOT EXISTS _flyway_init_marker (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);