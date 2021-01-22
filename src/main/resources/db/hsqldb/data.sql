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
INSERT INTO authorities(id,username,authority) VALUES (6,'marta','cliente');
-- antmorgon4
INSERT INTO users(username,password,enabled) VALUES ('antmorgon4','password',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (20,'antmorgon4','cliente');

INSERT INTO users(username,password,enabled) VALUES ('vendedor1','vendedor1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (111,'vendedor1','vendedor');

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


INSERT INTO merchandasings(id,tipo,fabricante,nombre,precio) VALUES (1, 'FIGURA', 'Fabricante 1', 'Goku SSJ4', 33.33);
INSERT INTO merchandasings(id,tipo,fabricante,nombre,precio) VALUES (2, 'FIGURA', 'Bandai', 'Goku SSJ4', 33.33);

---------------------------------------------------------------------------------------------------------------------------

--Cliente
INSERT INTO CLIENTES VALUES (1, 'apellido','12345678X','email@email.com','2013-01-01','nombre','123456789',200000.0,'Sevilla','41000','dir prueba','2222 2222 2222 2222','marta');
--INSERT INTO clientes(id,nombre,apellidos,dni,email,direccion,ciudad,tarjeta_credito,fecha_nacimiento,cartera,telefono,codigo_postal,pedidos,username) VALUES (1, 'Ivan', 'Cardenas Meneses', '12345678X', 'ivancarmen@alum.us.es', 'Calle Hernandez','Sevilla','0000-1111-2222-6666', '1998-10-10', '100.0','123456789','41980','ivacarmen');
--INSERT INTO clientes(id,nombre,apellidos,dni,email,direccion,ciudad,tarjeta_credito,fecha_nacimiento,cartera,telefono,codigo_postal,username) VALUES (2, 'Marta', 'ef Meneses', '12345470X', 'marta@alum.us.es', 'Calle Hndez','Sevilla', '0450-1111-2222-6666', '1998-11-10', '100.0','987654321','41980', 'marta');
--INSERT INTO clientes(id,nombre,apellidos,dni,email,direccion,ciudad,tarjeta_credito,fecha_nacimiento,cartera,telefono,codigo_postal,username) VALUES (3, 'Javier', 'Moreno Gonzalez', '32097886Y', 'antmorgon4@alum.us.es', 'Calle Tarfia 45 10','Sevilla', '0000-1111-2222-3333', '1997-05-16', '100.0','601326967','41012', 'antmorgon4');

--Pedido

--INSERT INTO pedido(id, estado, precio_total, fecha, direccion_envio) VALUES (1,2,20,'2013-01-01','carles company');

--Oferta

--INSERT INTO oferta(id,conservacion,precio,pedido_id,producto_id,vendedor_id) VALUES (1,1,40,1,null,null);
--INSERT INTO oferta(id,conservacion,precio,pedido_id,producto_id,vendedor_id) VALUES (2,1,20,1,null,null);


--INSERT INTO productos(id, nombre, precio,videojuego_id, cliente_id, merchandasing_id, pelicula_id) VALUES(1, 'Titanic', 12,null, 2, null,null );
--INSERT INTO peliculas(id, agno, director, duracion, edicion, formato, producto_id) VALUES(1, 1997, 'James Cameron', 4, 1, 1,1);

INSERT INTO peliculas VALUES(1, 'El hundimiento del barco, según el creador de "Downton Abbey". "Titanic" es una extraordinaria narración de este viaje condenado al fracaso, hábilmente tejido con parcelas de acción, misterio.', '2013-01-01','https://static.filmin.es/images/media/684/3/poster_0_3_256x0.webp','Titanic', 12, 2012,'Julian Fellowes',3,1, 'BLURAY');
INSERT INTO peliculas VALUES(2, 'Luc Besson da la vuelta a la épica espacial con esta película inspirada en ‘Valérian et Laureline’ la serie de cómics que influyó enormemente en la estética de la "Star Wars" original.', '2013-01-01', 'https://static.filmin.es/images/media/20818/1/poster_0_3_720x0.webp', 'Valerian y la ciudad de los mil planetas', 12,2017,'Luc Besson',2,1, 'BLURAY' );
INSERT INTO peliculas VALUES(3, 'Adaptación cinematográfica de la enigmática novela de Agatha Christie. Protagonizada por la ganadora de dos Globos de Oro, Glenn Close y la detective del fenómeno televisivo “Expediente X” Gillian Anderson.', '2013-01-01', 'https://static.filmin.es/images/media/23729/2/poster_0_3_720x0.webp','La casa torcida', 12,2017,'Gilles Paquet-Brenner',5,1, 'BLURAY' );
INSERT INTO peliculas VALUES(4, 'Ganadora del Premio del Público Joven de los Premios EFA, una historia vitalista y con carisma sobre una joven luchadora de kickboxing.', '2013-01-01', 'https://static.filmin.es/images/media/31442/1/poster_0_3_720x0.webp', 'Fight Girl', 12,2018,'Johan Timmers',5,1, 'BLURAY');

INSERT INTO VIDEOJUEGOS VALUES(1, 'Vuelve el juego que te convierte en una estrella, vuelve Just dance con Just Dance 2021 para PlayStation 4, Xbox One, Nintendo Switch y PlayStation 5. Hazte con él en GAME y que empiece la fiesta.','2013-01-01', 'https://media.game.es/COVERV2/3D_L/182/182836.png', 'JUST DANCE 2021', 49.95,2021,'Film SA', 'NINTENDO_SWITCH');
INSERT INTO VIDEOJUEGOS VALUES(2, '¡Descubre los placeres de vivir en una isla desierta y crea tu hogar perfecto en Animal Crossing: New Horizons para Nintendo Switch!','2013-01-01','https://media.game.es/COVERV2/3D_L/169/169067.png', 'ANIMAL CROSSING: NEW HORIZONS', 49.95,2021,'Film SA', 'NINTENDO_SWITCH');
INSERT INTO VIDEOJUEGOS VALUES(3, 'Compra ahora el juego de PS4, consigue gratis el juego de PS5 el 4 de Diciembre y mantén tu progreso en Volta Football y FIFA Ultimate Team en la siguiente generación.','2013-01-01','https://media.game.es/COVERV2/3D_L/181/181108.png','FIFA 21', 39.95,2021,'Film SA', 'PS4');
INSERT INTO VIDEOJUEGOS VALUES(4, 'Adéntrate en una aventura compleja y desconocida en la que explorarás una impresionante versión alternativa de una historia clásica. ','2013-01-01','https://media.game.es/COVERV2/3D_L/173/173386.png','GOD OF WAR PS HITS', 9.95,2021,'Film SA', 'PS4');
INSERT INTO VIDEOJUEGOS VALUES(5, 'Tu nueva experiencia Minecraft está aquí, en esta nueva y completa edición de tu juego favorito se incluye contenido extra como parte de la Bedrock Edition, así podrás disfrutar de un Minecraft con un mundo más grande, mejor y mucho más bonito.','2013-01-01','https://media.game.es/COVERV2/3D_L/150/150363.png','MINECRAFT - NINTENDO SWITCH EDITION', 29.95,2021,'Film SA', 'NINTENDO_SWITCH');

--Reproductores

INSERT INTO REPRODUCTORES( ID , NOMBRE , DESCRIPCION ) VALUES ( 4, 'DVD','El viejo amigo que te ayudaba a ver tus pelis allá por el 2000');
INSERT INTO REPRODUCTORES( ID , NOMBRE , DESCRIPCION ) VALUES ( 3, 'WII','La clásica consola de Nintendo');
INSERT INTO REPRODUCTORES( ID , NOMBRE , DESCRIPCION ) VALUES ( 2, 'XBOX SERIE X','La nueva consola de Microsoft');
INSERT INTO REPRODUCTORES( ID , NOMBRE , DESCRIPCION ) VALUES ( 1, 'PS5','La nueva consola de Sony');

--Vendedor

INSERT INTO vendedor(id,first_name,last_name,vacaciones,valoracion,nombre_tienda,direccion_tienda,telefono,username) VALUES (1,'carles','santos',true,20,'tienda bonita','calle piruleta','6085558763','guipavvar');

