-- Alter the existing Price table to add product_id
-- ==================================================
-- ChangeSet: alter-price-table-add-product-id
-- ==================================================
ALTER TABLE price
ADD COLUMN product_id UUID;

-- Add foreign key constraint to link Price to Product
ALTER TABLE price
ADD CONSTRAINT fk_price_product FOREIGN KEY (product_id) REFERENCES product(id);