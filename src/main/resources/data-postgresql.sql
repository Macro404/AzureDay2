INSERT into product_group (id, name) values (1, 'Food') ON CONFLICT DO NOTHING;
INSERT into product_group (id, name) values (2, 'Toys') ON CONFLICT DO NOTHING;
INSERT into product_group (id, name) values (3, 'Sports Gear') ON CONFLICT DO NOTHING;

insert into product(id, name, description, price, product_group_id) values (1, 'Ferarri', 'A beautiful red machine', 10000.99, 2) ON CONFLICT DO NOTHING;
insert into product(id, name, description, price, product_group_id) values (2, 'Banana', 'Yellow fruit', 1.99, 1) ON CONFLICT DO NOTHING;
insert into product(id, name, description, price, product_group_id) values (3, 'Football boots', 'Copa mundial. Iconic', 100.99, 3) ON CONFLICT DO NOTHING;
