DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'users'
          AND column_name = 'picture_url'
          AND data_type = 'character varying'
    ) THEN
        ALTER TABLE users ALTER COLUMN picture_url TYPE TEXT;
    END IF;
END $$;
