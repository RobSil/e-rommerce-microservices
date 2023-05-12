
create table if not exists users
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    first_name varchar(64) not null,
    last_name varchar(64) not null,
    date_of_birth timestamp with time zone,
    gender varchar(32) not null,
    email varchar(255) not null,
    email_confirmed boolean,
    password varchar(255),
    is_enabled boolean,
    roles jsonb
);

create unique index if not exists users_email_idx on users(email);
