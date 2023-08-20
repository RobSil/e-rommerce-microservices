alter table users drop column roles;

create table roles(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    name varchar(32),
    code varchar(32),
    authorities jsonb
);

create index roles_code_idx on roles(code);

create table users_roles(
    user_id bigint,
    role_id bigint,
    primary key (user_id, role_id),
    foreign key (user_id) references users(id),
    foreign key (role_id) references roles(id)
);
