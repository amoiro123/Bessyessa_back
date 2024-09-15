-- Ensure the uuid-ossp extension is available for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create table for the User entity
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- UUID primary key with auto-generation
    creation_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Creation timestamp
    update_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Update timestamp
    username VARCHAR(255) UNIQUE NOT NULL,  -- Unique and not null username
    password VARCHAR(255) NOT NULL,  -- Password field, not null
    email VARCHAR(255) UNIQUE NOT NULL,  -- Unique and not null email
    role VARCHAR(255),  -- Enum role
    active VARCHAR(255),  -- Enum active
    first_name VARCHAR(255),  -- UserDetails fields
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    country VARCHAR(255),
    city VARCHAR(255),
    address VARCHAR(255),
    postal_code VARCHAR(255),
    about_me TEXT,
    profile_picture VARCHAR(255),
    CONSTRAINT role_check CHECK (role IN ('ADMIN', 'USER')),  -- Assuming 'ADMIN' and 'USER' are possible values
    CONSTRAINT active_check CHECK (active IN ('ACTIVE', 'INACTIVE'))  -- Assuming 'ACTIVE' and 'INACTIVE' are possible values
);
