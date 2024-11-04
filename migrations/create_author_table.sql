CREATE TABLE author (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL,
                        birth_year INT,
                        nationality VARCHAR(100),
                        biography TEXT,
                        birth_place VARCHAR(255)
);