-- Ensure the uuid-ossp extension is available for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE product_model (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- Generate UUID automatically
    name VARCHAR(255) NOT NULL,
    description TEXT,
    added_by UUID,  -- Storing UUID but not generating it here
    added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Automatically set added_on
    brand_id UUID NOT NULL,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brand(id)
);
