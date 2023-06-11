create table categories
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    parent_id bigint,
    title varchar(64),

    foreign key (parent_id) references categories(id)
);

create index categories_parent_id_idx on categories(parent_id);

create table products
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    category_id bigint,
--     group_id bigint,
    sku varchar(256),
    name varchar(256),
    price numeric(19, 2),
    quantity numeric(19, 2),
    measure_unit varchar(32),
    status varchar(32),
    is_active boolean,

    properties jsonb,

    foreign key (category_id) references categories(id)
);

create unique index products_sku_idx on products(sku);
create index products_status_idx on products(status);
create index products_category_id_idx on products(category_id);
