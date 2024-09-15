-- Ensure the uuid-ossp extension is available for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE files (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- Generate UUID automatically
    type VARCHAR(255),
    file_path VARCHAR(255)
);
