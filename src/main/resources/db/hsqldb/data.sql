-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');
-- One owner user, named owner1 with passwor Carles
INSERT INTO users(username,password,enabled) VALUES ('guipavvar','Carles',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'guipavvar','owner');
-- Owner ivacarmen
INSERT INTO users(username,password,enabled) VALUES ('ivacarmen','ivacarmen',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'ivacarmen','owner');
--Owner luibelzan
INSERT INTO users(username,password,enabled) VALUES ('luibelzan','luibelzan',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'luibelzan','owner');
--Owner luibelzan
INSERT INTO users(username,password,enabled) VALUES ('marta','marta',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'marta','owner');

-- Probando 

INSERT INTO users(username,password,enabled) VALUES ('antmorgon4','password',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (20,'antmorgon4','owner');

--INSERT INTO productos(id, nombre, precio,videojuego_id, cliente_id, merchandasing_id, pelicula_id) VALUES(1, 'Titanic', 12,null, 2, null,null );
--INSERT INTO peliculas(id, agno, director, duracion, edicion, formato, producto_id) VALUES(1, 1997, 'James Cameron', 4, 1, 1,1);

--añado peliculas
--INSERT INTO PELICULAS VALUES(1, 'Titanic', 12,2012,'Julian Fellowes',3,1, 'BLURAY','https://static.filmin.es/images/media/684/3/poster_0_3_256x0.webp', 'El hundimiento del barco, según el creador de "Downton Abbey". "Titanic" es una extraordinaria narración de este viaje condenado al fracaso, hábilmente tejido con parcelas de acción, misterio.', 2);
--INSERT INTO PELICULAS VALUES(2, 'Valerian y la ciudad de los mil planetas', 12,2017,'Luc Besson',2,1, 'BLURAY','https://static.filmin.es/images/media/20818/1/poster_0_3_720x0.webp', 'Luc Besson da la vuelta a la épica espacial con esta película inspirada en ‘Valérian et Laureline’ la serie de cómics que influyó enormemente en la estética de la "Star Wars" original.', 2);
--INSERT INTO PELICULAS VALUES(3, 'La casa torcida', 12,2017,'Gilles Paquet-Brenner',5,1, 'BLURAY','https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp', 'Adaptación cinematográfica de la enigmática novela de Agatha Christie. Protagonizada por la ganadora de dos Globos de Oro, Glenn Close y la detective del fenómeno televisivo “Expediente X” Gillian Anderson.', 2);
--INSERT INTO PELICULAS VALUES(4, 'Fight Girl', 12,2018,'Johan Timmers',5,1, 'BLURAY','https://static.filmin.es/images/media/31442/1/poster_0_3_720x0.webp', 'Ganadora del Premio del Público Joven de los Premios EFA, una historia vitalista y con carisma sobre una joven luchadora de kickboxing.', 2);

--

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
--owner ivacarmen
INSERT INTO owners VALUES (11, 'Ivan', 'Cardenas', 'Calle Random', 'Sevilla', '123456789', 'ivacarmen');
INSERT INTO owners VALUES (16, 'Guillermo', 'Pavón', '110 W. Carles St.', 'Carles', '6085551056', 'guipavvar');
INSERT INTO owners VALUES (20, 'Javier', 'Moreno', 'Calle Tarfia 45 10', 'Sevilla', '601326967', 'antmorgon4');
INSERT INTO owners VALUES (21, 'Luis Miguel', 'Bellido', 'Avd. Reina Mercedes', 'Sevilla', '112345678', 'luibelzan');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);
--mascota ivacarmen
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Vinci', '2018-12-05', 2, 11);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17, 'Aziro', '2020-06-08', 1, 16);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18, 'Qwerty', '2020-04-10', 1, 21);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (50, 'Gato', '2001-9-11', 1, 20);


INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

--Cliente

INSERT INTO CLIENTES(APELLIDOS ,DNI ,EMAIL ,FECHA_NACIMIENTO ,NOMBRE ,TELEFONO ,CARTERA ,CIUDAD ,CODIGO_POSTAL ,DIRECCION ,TARJETA_CREDITO ) VALUES ('MORENO GONZÁLEZ' , '32097886Y', 'ANTMORGON4@ALUM.US.ES', '1997-05-16', 'ANTONIO JAVIER' , '601326967' , 100.0 , 'SEVILLA', '41012', 'CALLE TARFIA 45 10', '1234 1234 1234 1234');

INSERT INTO CLIENTES(APELLIDOS ,DNI ,EMAIL ,FECHA_NACIMIENTO ,NOMBRE ,TELEFONO ,CARTERA ,CIUDAD ,CODIGO_POSTAL ,DIRECCION ,TARJETA_CREDITO ) VALUES ('DOE' , '49552340J', 'JANEDOE@ALUM.US.ES', '1990-01-01', 'JANE' , '600102030' , 500.0 , 'NOWHERE', '111111', 'NOWHERE', '4321 4321 4321 4321');

--Pedido

--INSERT INTO pedido(id,estado,cliente_id,vendedor_id) VALUES (1,2,1,null);

--Oferta

--INSERT INTO oferta(id,conservacion,precio,pedido_id,producto_id,vendedor_id) VALUES (1,1,40,1,null,null);
--INSERT INTO oferta(id,conservacion,precio,pedido_id,producto_id,vendedor_id) VALUES (2,1,20,1,null,null);


--Plataforma

INSERT INTO PLATAFORMA ( ID , NOMBRE , DESCRIPCION ) VALUES ( 4, 'DVD','El viejo amigo que te ayudaba a ver tus pelis allá por el 2000');
INSERT INTO PLATAFORMA ( ID , NOMBRE , DESCRIPCION ) VALUES ( 3, 'WII','La clásica consola de Nintendo');
INSERT INTO PLATAFORMA ( ID , NOMBRE , DESCRIPCION ) VALUES ( 2, 'XBOX SERIE X','La nueva consola de Microsoft');
INSERT INTO PLATAFORMA ( ID , NOMBRE , DESCRIPCION ) VALUES ( 1, 'PS5','La nueva consola de Sony');

