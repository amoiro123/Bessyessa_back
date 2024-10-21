-- Add columns added_on added_by to the table price
ALTER TABLE price
ADD COLUMN added_by UUID,
ADD COLUMN added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;  -- Automatically set added_on