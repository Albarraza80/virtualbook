CREATE TABLE book (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      genre VARCHAR(100),
                      pages INT,
                      public_year INT NOT NULL,
                      editorial VARCHAR(255)
);

CREATE TABLE book_author (
                             book_id INT NOT NULL,
                             author_id INT NOT NULL,
                             PRIMARY KEY (book_id, author_id),
                             FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
                             FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);