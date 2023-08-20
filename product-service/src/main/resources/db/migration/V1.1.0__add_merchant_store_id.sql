alter table products add column merchant_store_id bigint;
create index products_merchant_store_id_idx ON products (merchant_store_id);
