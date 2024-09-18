INSERT INTO authors (id, name, date_of_birth, description, validated)
VALUES
    (1, 'J.K. Rowling', '1965-07-31', 'British author best known for the Harry Potter series.', true),
    (2, 'George R.R. Martin', '1948-09-20', 'American novelist and short story writer, known for A Song of Ice and Fire series.', true),
    (3, 'Eoin Colfer', '1965-05-14', 'Irish author, best known for the Artemis Fowl series.', true);

INSERT INTO books (id, title, author_id, description, amount_of_pages, price, validated)
VALUES
    (1, 'Harry Potter and the Philosophers Stone', 1, 'The first book in the Harry Potter series.', 223, 19.99, true),
(2, 'Harry Potter and the Chamber of Secrets', 1, 'The second book in the Harry Potter series.', 251, 19.99, true),
(3, 'Harry Potter and the Prisoner of Azkaban', 1, 'The third book in the Harry Potter series.', 317, 19.99, true),
(4, 'Harry Potter and the Goblet of Fire', 1, 'The fourth book in the Harry Potter series.', 636, 19.99, true),
(5, 'A Game of Thrones', 2, 'The first book in A Song of Ice and Fire series.', 694, 29.99, true),
(6, 'A Clash of Kings', 2, 'The second book in A Song of Ice and Fire series.', 768, 29.99, true),
(7, 'A Storm of Swords', 2, 'The third book in A Song of Ice and Fire series.', 973, 29.99, true),
(8, 'A Feast for Crows', 2, 'The fourth book in A Song of Ice and Fire series.', 753, 29.99, true),
(9, 'Artemis Fowl', 3, 'The first book in the Artemis Fowl series.', 280, 12.99, true),
(10, 'Artemis Fowl: The Arctic Incident', 3, 'The second book in the Artemis Fowl series.', 272, 12.99, true),
(11, 'Artemis Fowl: The Eternity Code', 3, 'The third book in the Artemis Fowl series.', 320, 12.99, true),
(12, 'Artemis Fowl: The Opal Deception', 3, 'The fourth book in the Artemis Fowl series.', 342, 12.99, true);

INSERT INTO reviews (id, reviewer, review_title, book_id, text, score)
VALUES
    (1, 'Alice Johnson', 'Magical Start!', 1, 'This book is a wonderful introduction to a magical world. I could not put it down!', 5),
    (2, 'Mark Smith', 'A Great Sequel', 2, 'A fantastic follow-up with more twists and excitement than the first!', 4),
    (3, 'Sarah Lee', 'A Dark Turn', 3, 'The story takes a darker turn, but it’s brilliantly written. A must-read!', 5),
    (4, 'Tom Brown', 'Epic Adventure', 4, 'An epic journey full of adventure and magic. Highly recommend!', 5),
    (5, 'Emma Wilson', 'A Classic Tale', 5, 'A gripping start to a classic series. I was hooked from the first page!', 4),
    (6, 'James Taylor', 'Intense and Engaging', 6, 'The plot thickens in this sequel! It’s intense and hard to put down.', 5),
    (7, 'Olivia Martin', 'Not My Favorite', 9, 'While I loved the concept, it didn’t capture my interest as much as I hoped.', 3),
    (8, 'David Thompson', 'Fantastic Series!', 10, 'An exciting continuation of Artemis’s adventures. Can’t wait for more!', 4);

INSERT INTO comments (id, commenter, message, date_posted, review_id)
VALUES
    (1, 'Alice Johnson', 'I totally agree with this review! The book was amazing!', '2024-09-18', 1),
    (2, 'Bob Smith', 'I found some parts a bit slow, but overall a great read.', '2024-09-19', 1),
    (3, 'Charlie Brown', 'This is one of my favorite series! Thanks for the review!', '2024-09-20', 1),
    (4, 'Diana Prince', 'I think this book could have been better. Not my cup of tea.', '2024-09-21', 7),
    (5, 'Ethan Hunt', 'I loved the characters and the plot twists!', '2024-09-22', 8);
