--
-- Create the role for the db
--
CREATE ROLE wow_user
  NOSUPERUSER
  NOCREATEDB
  NOCREATEROLE
  NOINHERIT
  LOGIN
  NOREPLICATION
  NOBYPASSRLS
  PASSWORD
  NULL
;
