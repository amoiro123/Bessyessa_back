-- Ensure the uuid-ossp extension is available for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE brand (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- Generate UUID automatically
    name VARCHAR(255) NOT NULL,
    description TEXT,
    added_by UUID,  -- Storing UUID but not generating it here
    product_type VARCHAR(255)
);
