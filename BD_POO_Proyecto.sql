CREATE SCHEMA `videojuegos` ;

CREATE TABLE `videojuegos`.`juego` (
  `id_juego` INT NOT NULL AUTO_INCREMENT,
  `titulo` MEDIUMTEXT NOT NULL,
  `anio` MEDIUMTEXT NULL,
  `desarrollador` MEDIUMTEXT NOT NULL,
  `codigo_juego` MEDIUMTEXT NULL,
  `plataforma` MEDIUMTEXT NOT NULL,
  `resenia` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id_juego`));
  
  
INSERT INTO juego ( titulo, anio,desarrollador,codigo_juego,plataforma,resenia) VALUES
('La leyenda de Zelda','2017','Nintendo Entertainment Analysis & Development', '1234567890','Nintendo','Es un juego de aventuras en el cual tendras que buscar un tesoro'),
('Super Mario Bros 3','1995','Nintendo Entertainment Analysis & Development', '1478523690','Nintendo','Mario, un personaje que se encuentra'),
('Grand Theft Auto V','2015','Rockstar Games', '3698521470','PLay Station','Franklin, Trevor y Michael, tres personajes con los cuales realizaras atracos muy interesantes'),
('Halo Reach','2010','Bungie Studios', '9876543210','Xbox','Juego de disparos en el cual tendras que matar unos cuantos extraterrestres'),
('Resident Evil V','2009','Biohazard 5', '1593574560','PC','Juego de terror, donde tienes que matar zombies y esta ambientado en africa'),
('Fornite','2011','Epic Games', '9871234565','PC','Juego en linea, tipo todos contra todos, en el cual podras jugar en equipo o solo'),
('Smash Bros','2001','Bandai Namco', '1234567898','Nintendo','Juego de peleas muy entretenido, de manera en linea y multijugador'),
('Dragon Ball Z fighters','2018','Arc System Works', '1234657809','Xbox','Juego de peleas multijugador en linea, de la franquicia de Dragon Ball'),
('Uncharted 5','2022','Sony', '7654321890','Play Station','Juego de aventuras en, de una franquicia reconocida y muy querida por los fans'),
('God of War','2018','Jetpack Interactive', '3214511800','Play Station','Juego no apto para menores de 15, juego de aventura y pelea.');



