INSERT INTO tb_user (first_name, last_name, email, password, active) VALUES ('Mateus', 'Vini', 'mviniciusdospassos@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'true');
INSERT INTO tb_user (first_name, last_name, email, password, active) VALUES ('Vini', 'Mateus', 'passos@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'true');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);

INSERT INTO tb_category (name, created_At) VALUES ('Movies', NOW());
INSERT INTO tb_category (name, created_At) VALUES ('Games', NOW());

INSERT INTO tb_subject (name, moment, description, img_url) VALUES ('The Lord of the Rings', TIMESTAMP WITH TIME ZONE '2020-07-13T20:50:07.12345Z', 'Test', 'https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg');

INSERT INTO tb_subject_category (subject_id, category_id) VALUES (1, 1);

INSERT INTO tb_review (description, rate, user_id) VALUES ('Nothing to say, just wow', 5, 1);

INSERT INTO tb_subject_review (subject_id, review_id) VALUES (1, 1);