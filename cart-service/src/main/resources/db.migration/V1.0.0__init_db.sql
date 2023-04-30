create table carts
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    user_id bigint
);

create index carts_user_id_idx on carts(user_id);

create table cart_items
(
    id bigserial primary key,
    created_date timestamp with time zone,
    last_modified_date timestamp with time zone,
    version bigint,

    cart_id bigint,
    product_id bigint,

    quantity numeric(9, 3),
    price numeric (11, 9),

    foreign key (cart_id) references carts(id)
);

create index cart_items_product_id_idx on cart_items(product_id);
