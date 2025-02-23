create table "task" (
    "id" BIGSERIAL not null primary key,
    "title" VARCHAR(100) not null,
    "description" VARCHAR(3000) not null,
    "status" INTEGER not null,
    "location" POINT not null,
    "estimated_effort" INTEGER not null,
    "created_at" TIMESTAMP default now() not null
);
comment on table "task" is 'null';

