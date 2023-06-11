create table orders
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    user_id bigint,
    status varchar(32),
    details jsonb
);

create index orders_user_id_idx on orders(user_id);

create table order_items
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    product_id bigint,
    order_id bigint,

    quantity numeric(9, 3),

    foreign key (order_id) references orders(id)
);

create index order_items_product_id_idx on order_items(product_id);
