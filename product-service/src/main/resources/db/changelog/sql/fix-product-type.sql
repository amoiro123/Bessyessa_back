-- Step 1: Remove the 'product_type' column from the 'brand' table
ALTER TABLE brand DROP COLUMN IF EXISTS product_type;

-- Step 2: Add the 'product_type' column to the 'product_model' table
ALTER TABLE product_model ADD COLUMN product_type VARCHAR(255);

-- Step 3: Populate the 'product_type' column with the value 'CAMERA'
UPDATE product_model SET product_type = 'CAMERA';

-- Step 4: Add NOT NULL constraint to 'product_type' column after data has been populated
ALTER TABLE product_model ALTER COLUMN product_type SET NOT NULL;