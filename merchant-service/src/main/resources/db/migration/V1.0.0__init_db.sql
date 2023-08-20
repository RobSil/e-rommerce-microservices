create table merchant_requests
(
    id                 bigserial primary key,
    created_date       timestamp with time zone,
    last_modified_date timestamp with time zone,
    version            bigint,

    first_name         varchar(32),
    last_name          varchar(32),
    phone_number       varchar(16),
    email              varchar(64),
    password           varchar(255),
    status             varchar(32),
    token              varchar(64) unique,
    user_id            bigint unique,
    expired_at         timestamp without time zone,
    confirmed_at       timestamp without time zone,
    decisioned_at      timestamp without time zone

);

create index merchant_requests_token_idx on merchant_requests (token);
create index merchant_requests_status_idx on merchant_requests (status);

create table merchants
(
    id                 bigserial primary key,
    created_date       timestamp with time zone,
    last_modified_date timestamp with time zone,
    version            bigint,

    request_id         bigint,
    first_name         varchar(32),
    last_name          varchar(32),
    phone_number       varchar(16) unique,
    email              varchar(64) unique,
    password           varchar(255),
    is_not_blocked     boolean,
    user_id            bigint unique,
    foreign key (request_id) references merchant_requests (id)
);

create index merchants_user_id_idx on merchants (user_id);

create table merchant_stores
(
    id                 bigserial primary key,
    created_date       timestamp with time zone,
    last_modified_date timestamp with time zone,
    version            bigint,

    name               varchar(32) unique,
    merchant_id        bigint,
    contacts           jsonb,
    is_not_blocked     boolean,
    foreign key (merchant_id) references merchants (id)
);
