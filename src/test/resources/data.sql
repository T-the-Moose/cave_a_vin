INSERT INTO cav_colors (name)  VALUES ('Blanc');
INSERT INTO cav_colors (name)   VALUES ('Rouge');
INSERT INTO cav_colors (name)   VALUES (N'Rosé');

INSERT INTO cav_regions (name)  VALUES (N'Auvergne-Rhône-Alpes');
INSERT INTO cav_regions (name)   VALUES (N'Bourgogne-Franche-Comté');
INSERT INTO cav_regions (name)   VALUES ('Bretagne');
INSERT INTO cav_regions (name)   VALUES ('Centre-Val de Loire');
INSERT INTO cav_regions (name)   VALUES ('Corse');
INSERT INTO cav_regions (name)   VALUES ('Grand Est');
INSERT INTO cav_regions (name)   VALUES ('Hauts-de-France');
INSERT INTO cav_regions (name)   VALUES ('Ile-de-France');
INSERT INTO cav_regions (name)   VALUES ('Normandie');
INSERT INTO cav_regions (name)   VALUES ('Nouvelle-Aquitaine');
INSERT INTO cav_regions (name)   VALUES ('Occitanie');
INSERT INTO cav_regions (name)   VALUES ('Pays de la Loire');
INSERT INTO cav_regions (name)   VALUES (N'Provence-Alpes-Côte d''Azur');

/*Bouteille*/
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES ('DOMAINE WW, Muscat Vendanges Tardives','2017',0,1872,11,6,1);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES ('Domaine FF, BRUT pinot gris','2020',1,972,21.5,6,1);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES (N'Gilbert FF, Crémant d''Alsace rosé', '2022', 1, 1872, 19, 6, 3);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES ('Domaine LG, Pisse Vieille','2022',0,999,7.25,2,2);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES ('Domaine LG, Vieille Vigne','2021',0,1172,12,2,2);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES ('Domaine du B','2021',0,187,17.5,2,2);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES (N'Château Caché Cuvée L', '2018', 0, 1112, 9.9, 10, 2);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES (N'Bordeaux AOP, Rosé', '2021', 0, 2472, 7.5, 10, 3);
INSERT INTO cav_bottles (name, vintage, sparkling, quantity, price, region_id, color_id)  VALUES (N'Château du roi', '2013', 0, 272, 21, 10, 2);

/*Utilisateur*/
INSERT INTO cav_users ([login],[password],[last_name],[first_name],[authority], [role])   VALUES ('georgelucas@email.fr','{bcrypt}$2a$10$ArMK0JihvGcM.aECD/J8rezPcKfCi.wMxJaIVAy5WlLrU5etq/yKm','Lucas','George','ROLE_OWNER', 'owner');/*Password : Réalisateur&Producteur*/
INSERT INTO cav_users ([login],[password],[last_name],[first_name],[authority], [role])   VALUES ('natalieportman@email.fr','{bcrypt}$2a$10$6/QHy4Qiz8WB.Pfh9DwLEesCoBDlycGuTBMJQGBeIphVq2V/Jibou','Portman','Natalie','ROLE_CLIENT', 'client');/*Password : MarsAttacks!*/
INSERT INTO cav_users ([login],[password],[last_name],[first_name],[authority], [role])   VALUES ('bobeponge@email.fr','{bcrypt}$2a$10$5hVCTb4cq20CfCjLNzFUTervByeCEnjU314raNmeqi7SzH6zRD.8K','Eponge','Bob','ROLE_CLIENT', 'client');/*Password : carré*/
INSERT INTO cav_users ([login],[password],[last_name],[first_name],[authority], [role])   VALUES ('harrisonford@email.fr','{bcrypt}$2a$10$/5nEPZg4/BC/Jj0Q/yzEQ.0gGWzBu0ZmziWaeIAVtrtVj.VoAHPHS','Ford','Harrison','ROLE_CLIENT', 'client');/*Password : IndianaJones3*/

/*Proprio*/
INSERT INTO cav_owner ([login],[client_number]) VALUES ('georgelucas@email.fr', '12345678901234');

/*Client*/
/*Adresse*/
INSERT INTO cav_address ([street],[zip_code],[city]) values ('Hollywood Boulevard','44000','Nantes');
INSERT INTO cav_address ([street],[zip_code],[city]) values ('Sous la mer','35000','Rennes');
INSERT INTO cav_address ([street],[zip_code],[city]) values ('Rue du Temple','35000','Rennes');
INSERT INTO cav_client ([login],[address_id]) values ('natalieportman@email.fr',1);
INSERT INTO cav_client ([login],[address_id]) values ('bobeponge@email.fr',2);
INSERT INTO cav_client ([login],[address_id]) values ('harrisonford@email.fr',3);

/*Panier et LignePanier*/
INSERT INTO cav_shopping_cart ([client_id],[total_price],[order_number],[paid]) VALUES ('natalieportman@email.fr',1315,'natalieportman@email.fr_1',1);
INSERT INTO cav_shopping_cart ([client_id],[total_price],[paid]) VALUES ('natalieportman@email.fr',2300,0);
INSERT INTO cav_shopping_cart ([client_id],[total_price],[order_number],[paid]) VALUES ('bobeponge@email.fr',425,'bobeponge@email.fr_3',1);
INSERT INTO cav_shopping_cart ([client_id],[total_price],[paid]) VALUES ('bobeponge@email.fr',425,0);

INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (1,1,100,1100);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (1,2,10,215);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (2,1,100,1100);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (2,5,100,1200);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (3,2,10,215);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (3,9,10,210);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (4,2,10,215);
INSERT INTO cav_line ([shopping_cart_id],[bottle_id],[quantity],[price]) VALUES (4,9,10,210);