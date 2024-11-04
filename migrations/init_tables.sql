INSERT INTO author (name, lastname, birth_year, nationality, biography, birth_place) VALUES
('Gabriel García', 'Márquez', 1927, 'Colombiano', 'Novelista y periodista colombiano, uno de los mayores exponentes del realismo mágico.', 'Aracataca'),
('Julio Cortázar', 'Cortázar', 1914, 'Argentino', 'Escritor y traductor argentino, figura clave del boom latinoamericano.', 'Bruselas');

INSERT INTO book (title, genre, pages, public_year, editorial) VALUES
('Cien años de soledad', 'Novela', 417, 1967, 'Editorial Sudamericana');

INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),
(1, 2);
