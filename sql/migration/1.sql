--
-- Initialize the database for the first time
--
-- NOTE: Run the create_role script seperatly if the db does not have the wow_user role yet
--

SET search_path TO public;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS wow_app;
SET search_path TO wow_app, public;

ALTER ROLE wow_user SET search_path TO wow_app;

REVOKE ALL ON ALL TABLES IN SCHEMA PUBLIC FROM wow_user;
REVOKE ALL ON ALL TABLES IN SCHEMA wow_app FROM wow_user;
REVOKE ALL ON SCHEMA wow_app FROM wow_user;

GRANT USAGE ON SCHEMA wow_app TO wow_user;
ALTER DEFAULT PRIVILEGES FOR ROLE wow_user IN SCHEMA wow_app GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO wow_user;

\i sql/init/account.sql
\i sql/init/category.sql
\i sql/init/toon.sql
\i sql/init/task.sql
\i sql/init/task_assignment.sql
\i sql/init/task_category.sql

\i sql/init/test.sql

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA wow_app TO wow_user;
SET search_path TO DEFAULT;

