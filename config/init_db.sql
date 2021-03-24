create table if not exists resume
(
    uuid char(36) not null constraint resume_pk primary key,
    full_name text
    );

create table if not exists contact
(
    id serial primary key ,
    type text not null,
    value text not null,
    resume_uuid char(36) not null references resume on delete cascade
    );
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

create table if not exists section
(
    id serial primary key,
    type text not null,
    value text not null,
    resume_uuid char(36) not null references resume on delete cascade
);
CREATE UNIQUE INDEX section_idx
    ON section (resume_uuid, type);

