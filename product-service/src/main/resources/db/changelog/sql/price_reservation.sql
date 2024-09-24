-- 1. Remove loanedOn column from Product table
-- ==================================================
-- ChangeSet: remove-loanedOn-from-product
-- ==================================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
ALTER TABLE product
DROP COLUMN loaned_on;


-- 2. Create Price table and add currentPrice to Product table
-- ==================================================
-- ChangeSet: create-price-table
-- ==================================================
CREATE TABLE price (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- Generate UUID automatically
    amount DOUBLE PRECISION,
    currency VARCHAR(10)
);

-- Add current_price_id column to Product table
ALTER TABLE product
ADD current_price_id UUID;

-- Add foreign key constraint for current_price_id in Product table
ALTER TABLE product
ADD CONSTRAINT fk_product_price FOREIGN KEY (current_price_id) REFERENCES price(id);


-- 3. Create Reservation table and add relationships
-- ==================================================
-- ChangeSet: create-reservation-table
-- ==================================================
CREATE TABLE reservation (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),  -- Generate UUID automatically
    loaned_on TIMESTAMP NOT NULL,
    loaned_from TIMESTAMP NOT NULL,
    loaned_until TIMESTAMP NOT NULL,
    user_id UUID,
    price_id UUID,
    product_id UUID,
    CONSTRAINT fk_reservation_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_reservation_price FOREIGN KEY (price_id) REFERENCES price(id),
    CONSTRAINT fk_reservation_product FOREIGN KEY (product_id) REFERENCES product(id)
);
