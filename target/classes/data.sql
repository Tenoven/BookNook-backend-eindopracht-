INSERT INTO authors ( name, date_of_birth, description, validated)
VALUES
    ( 'J.K. Rowling', '1965-07-31', 'British author best known for the Harry Potter series.', false),
    ( 'George R.R. Martin', '1948-09-20', 'American novelist and short story writer, known for A Song of Ice and Fire series.', true),
    ( 'Eoin Colfer', '1965-05-14', 'Irish author, best known for the Artemis Fowl series.', true);

INSERT INTO books ( title, author_id, description, amount_of_pages, price, validated)
VALUES
    ('Harry Potter and the Philosophers Stone', 1, 'The first book in the Harry Potter series.', 223, 19.99, false),
('Harry Potter and the Chamber of Secrets', 1, 'The second book in the Harry Potter series.', 251, 19.99, true),
('Harry Potter and the Prisoner of Azkaban', 1, 'The third book in the Harry Potter series.', 317, 19.99, true),
('Harry Potter and the Goblet of Fire', 1, 'The fourth book in the Harry Potter series.', 636, 19.99, true),
('A Game of Thrones', 2, 'The first book in A Song of Ice and Fire series.', 694, 29.99, true),
('A Clash of Kings', 2, 'The second book in A Song of Ice and Fire series.', 768, 29.99, true),
('A Storm of Swords', 2, 'The third book in A Song of Ice and Fire series.', 973, 29.99, true),
('A Feast for Crows', 2, 'The fourth book in A Song of Ice and Fire series.', 753, 29.99, true),
('Artemis Fowl', 3, 'The first book in the Artemis Fowl series.', 280, 12.99, true),
('Artemis Fowl: The Arctic Incident', 3, 'The second book in the Artemis Fowl series.', 272, 12.99, true),
('Artemis Fowl: The Eternity Code', 3, 'The third book in the Artemis Fowl series.', 320, 12.99, true),
('Artemis Fowl: The Opal Deception', 3, 'The fourth book in the Artemis Fowl series.', 342, 12.99, true);

INSERT INTO reviews (reviewer, review_title, book_id, text, score)
VALUES
    ('Alice Johnson', 'Magical Start!', 1, 'This book is a wonderful introduction to a magical world. I could not put it down!', 5),
    ('Mark Smith', 'A Great Sequel', 2, 'A fantastic follow-up with more twists and excitement than the first!', 4),
    ('Sarah Lee', 'A Dark Turn', 3, 'The story takes a darker turn, but it’s brilliantly written. A must-read!', 5),
    ('Tom Brown', 'Epic Adventure', 4, 'An epic journey full of adventure and magic. Highly recommend!', 5),
    ('Emma Wilson', 'A Classic Tale', 5, 'A gripping start to a classic series. I was hooked from the first page!', 4),
    ('James Taylor', 'Intense and Engaging', 6, 'The plot thickens in this sequel! It’s intense and hard to put down.', 5),
    ('Olivia Martin', 'Not My Favorite', 9, 'While I loved the concept, it didn’t capture my interest as much as I hoped.', 3),
    ('David Thompson', 'Fantastic Series!', 10, 'An exciting continuation of Artemis’s adventures. Can’t wait for more!', 4);

INSERT INTO comments (commenter, message, date_posted, review_id)
VALUES
    ('Alice Johnson', 'I totally agree with this review! The book was amazing!', '2024-09-18', 1),
    ('Bob Smith', 'I found some parts a bit slow, but overall a great read.', '2024-09-19', 1),
    ('Charlie Brown', 'This is one of my favorite series! Thanks for the review!', '2024-09-20', 1),
    ('Diana Prince', 'I think this book could have been better. Not my cup of tea.', '2024-09-21', 7),
    ('Ethan Hunt', 'I loved the characters and the plot twists!', '2024-09-22', 8);

INSERT INTO users (username, password)
VALUES
    ('PietPrecies', '$2a$12$XsnFKXpNhG6YRgtZDiD.5uHoPg2L4N1dxjK2sGkcYD13sIiPhGBpK'),
    ('Admin', '$2a$12$QJIYtGJpIZ0m6pAErvyh3OeLXMmYBf1UK7KREgseUDGRvcERXmEMS');

INSERT INTO authorities (username, authority)
VALUES
    ('PietPrecies', 'ROLE_USER'),
    ('Admin', 'ROLE_USER'),
    ('Admin', 'ROLE_ADMIN');