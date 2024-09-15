-- Ensure the uuid-ossp extension is available for UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE product (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- Generate UUID automatically
    reference VARCHAR(255) NOT NULL UNIQUE,
    published_by UUID,  -- Admin user who published
    loaned_by UUID,  -- Client user who loaned
    is_available BOOLEAN DEFAULT TRUE,
    published_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Automatically set published_on
    loaned_on TIMESTAMP,
    product_model_id UUID,
    CONSTRAINT fk_product_model FOREIGN KEY (product_model_id) REFERENCES product_model(id)
);
