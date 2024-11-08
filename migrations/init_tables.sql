INSERT INTO author (name, lastname, birth_year, nationality, biography, birth_place) VALUES
('Gabriel García', 'Márquez', 1927, 'Colombiano', 'Novelista y periodista colombiano, uno de los mayores exponentes del realismo mágico.', 'Aracataca'),
('Julio Cortázar', 'Cortázar', 1914, 'Argentino', 'Escritor y traductor argentino, figura clave del boom latinoamericano.', 'Bruselas'),
('Isabel', 'Allende', 1942, 'Chilena', 'Escritora chilena conocida por sus novelas de realismo mágico, entre ellas "La casa de los espíritus".', 'Lima'),
('Mario', 'Vargas Llosa', 1936, 'Peruano', 'Novelista y ensayista peruano, ganador del Premio Nobel de Literatura en 2010.', 'Arequipa'),
('Carlos', 'Fuentes', 1928, 'Mexicano', 'Novelista, ensayista y diplomático mexicano, figura importante del boom latinoamericano.', 'Ciudad de Panamá'),
('Jorge Luis', 'Borges', 1899, 'Argentino', 'Escritor, poeta y ensayista argentino, destacado por su obra de narrativa breve y sus ensayos filosóficos.', 'Buenos Aires'),
('Pablo', 'Neruda', 1904, 'Chileno', 'Poeta chileno, ganador del Premio Nobel de Literatura en 1971.', 'Parral'),
('Gabriela', 'Mistral', 1889, 'Chilena', 'Poeta chilena, primera mujer latinoamericana en recibir el Premio Nobel de Literatura.', 'Vicuña'),
('Octavio', 'Paz', 1914, 'Mexicano', 'Poeta, ensayista y diplomático mexicano, galardonado con el Premio Nobel de Literatura en 1990.', 'Ciudad de México'),
('Joaquim Maria', 'Machado de Assis', 1839, 'Brasileño', 'Escritor brasileño, pionero de la novela en América Latina y uno de los principales autores de su país.', 'Río de Janeiro'),
('Gabriel', 'Mistral', 1889, 'Chilena', 'Poeta y diplomática chilena, primera mujer latinoamericana en recibir el Premio Nobel de Literatura.', 'Vicuña'),
('Laura', 'Esquivel', 1950, 'Mexicana', 'Escritora mexicana famosa por su novela "Como agua para chocolate", un clásico del realismo mágico.', 'Ciudad de México');

INSERT INTO book (title, genre, pages, public_year, editorial) VALUES
('Cien años de soledad', 'Novela', 417, 1967, 'Editorial Sudamericana');

INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),
(1, 2);
