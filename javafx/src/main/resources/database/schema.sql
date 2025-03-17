-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler version: 1.1.6
-- PostgreSQL version: 17.0
-- Project Site: pgmodeler.io
-- Model Author: Jakob

DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- object: public.u_user | type: TABLE --
-- DROP TABLE IF EXISTS public.u_user CASCADE;
CREATE TABLE public.u_user (
	user_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	username varchar(50) NOT NULL,
	email varchar(200) NOT NULL,
	password char(100) NOT NULL,
	profile_picture bytea,
	CONSTRAINT u_user_pk PRIMARY KEY (user_id)
);
-- ddl-end --
ALTER TABLE public.u_user OWNER TO helpr;
-- ddl-end --

-- object: public.task | type: TABLE --
-- DROP TABLE IF EXISTS public.task CASCADE;
CREATE TABLE public.task (
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
ALTER TABLE public.task OWNER TO helpr;
-- ddl-end --

-- object: author_u_user_fk | type: CONSTRAINT --
-- ALTER TABLE public.task DROP CONSTRAINT IF EXISTS author_u_user_fk CASCADE;
ALTER TABLE public.task ADD CONSTRAINT author_u_user_fk FOREIGN KEY (author_id)
REFERENCES public.u_user (user_id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.application | type: TABLE --
-- DROP TABLE IF EXISTS public.application CASCADE;
CREATE TABLE public.application (
	user_id bigint NOT NULL,
	task_id bigint NOT NULL,
	created_at timestamp DEFAULT now()

);
-- ddl-end --
ALTER TABLE public.application OWNER TO helpr;
-- ddl-end --

-- object: u_user_fk | type: CONSTRAINT --
-- ALTER TABLE public.application DROP CONSTRAINT IF EXISTS u_user_fk CASCADE;
ALTER TABLE public.application ADD CONSTRAINT u_user_fk FOREIGN KEY (user_id)
REFERENCES public.u_user (user_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --

-- object: task_fk | type: CONSTRAINT --
-- ALTER TABLE public.application DROP CONSTRAINT IF EXISTS task_fk CASCADE;
ALTER TABLE public.application ADD CONSTRAINT task_fk FOREIGN KEY (task_id)
REFERENCES public.task (task_id) MATCH FULL
ON DELETE CASCADE ON UPDATE CASCADE;
-- ddl-end --


