drop table if exists product ;
create table product(
product_id integer primary key,
product_name varchar(200),
quantity integer,
price decimal,
expiry_Date date
);