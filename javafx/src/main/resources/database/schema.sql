-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 1.1.6
-- PostgreSQL version: 17.0
-- Project Site: pgmodeler.io
-- Model Author: Jakob
-- -- object: app | type: ROLE --
-- -- DROP ROLE IF EXISTS app;
-- CREATE ROLE app WITH
-- 	SUPERUSER
-- 	 PASSWORD 'app';
-- -- ddl-end --
--

-- Database creation must be performed outside a multi lined SQL file.
-- These commands were put in this file only as a convenience.
--
-- -- object: helpr | type: DATABASE --
-- -- DROP DATABASE IF EXISTS helpr;
-- CREATE DATABASE helpr
-- 	OWNER = app;
-- -- ddl-end --
--

-- object: helpr | type: SCHEMA --
DROP SCHEMA IF EXISTS helpr CASCADE;
CREATE SCHEMA helpr;
-- ddl-end --
ALTER SCHEMA helpr OWNER TO app;
-- ddl-end --

SET search_path TO pg_catalog,public,helpr;
-- ddl-end --

-- object: helpr.u_user | type: TABLE --
-- DROP TABLE IF EXISTS helpr.u_user CASCADE;
CREATE TABLE helpr.u_user (
	user_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	username varchar(50) NOT NULL,
	email varchar(200) NOT NULL,
	password char(100) NOT NULL,
	profile_picture bytea,
	CONSTRAINT u_user_pk PRIMARY KEY (user_id)
);
-- ddl-end --
ALTER TABLE helpr.u_user OWNER TO app;
-- ddl-end --

-- object: helpr.task | type: TABLE --
-- DROP TABLE IF EXISTS helpr.task CASCADE;
CREATE TABLE helpr.task (
	task_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	author_id bigint NOT NULL,
	title varchar(200) NOT NULL,
	description varchar(4096) NOT NULL,
	reward smallint NOT NULL,
	effort smallint NOT NULL,
	location varchar(100) NOT NULL,
	created_at timestamp DEFAULT now(),
	CONSTRAINT task_pk PRIMARY KEY (task_id)
);
-- ddl-end --
ALTER TABLE helpr.task OWNER TO app;
-- ddl-end --

-- object: author_u_user_fk | type: CONSTRAINT --
-- ALTER TABLE helpr.task DROP CONSTRAINT IF EXISTS author_u_user_fk CASCADE;
ALTER TABLE helpr.task ADD CONSTRAINT author_u_user_fk FOREIGN KEY (author_id)
REFERENCES helpr.u_user (user_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: helpr.application | type: TABLE --
-- DROP TABLE IF EXISTS helpr.application CASCADE;
CREATE TABLE helpr.application (
	user_id bigint NOT NULL,
	task_id bigint NOT NULL,
	created_at timestamp DEFAULT now()

);
-- ddl-end --
ALTER TABLE helpr.application OWNER TO app;
-- ddl-end --

-- object: u_user_fk | type: CONSTRAINT --
-- ALTER TABLE helpr.application DROP CONSTRAINT IF EXISTS u_user_fk CASCADE;
ALTER TABLE helpr.application ADD CONSTRAINT u_user_fk FOREIGN KEY (user_id)
REFERENCES helpr.u_user (user_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: task_fk | type: CONSTRAINT --
-- ALTER TABLE helpr.application DROP CONSTRAINT IF EXISTS task_fk CASCADE;
ALTER TABLE helpr.application ADD CONSTRAINT task_fk FOREIGN KEY (task_id)
REFERENCES helpr.task (task_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: helpr.image | type: TABLE --
-- DROP TABLE IF EXISTS helpr.image CASCADE;
CREATE TABLE helpr.image (
	image_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	task_id bigint NOT NULL,
	path varchar(100) NOT NULL,
	"order" smallint DEFAULT 0

);
-- ddl-end --
ALTER TABLE helpr.image OWNER TO app;
-- ddl-end --

-- object: task_fk | type: CONSTRAINT --
-- ALTER TABLE helpr.image DROP CONSTRAINT IF EXISTS task_fk CASCADE;
ALTER TABLE helpr.image ADD CONSTRAINT task_fk FOREIGN KEY (task_id)
REFERENCES helpr.task (task_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: pg_trgm | type: EXTENSION --
-- DROP EXTENSION IF EXISTS pg_trgm CASCADE;
CREATE EXTENSION pg_trgm
WITH SCHEMA helpr;
-- ddl-end --


