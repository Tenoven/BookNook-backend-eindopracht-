
INSERT INTO authors (id, name, date_of_birth, description, validated)
VALUES
    (1, 'J.K. Bowling', '1965-07-31', 'British author best known for the Harry Potter series.', false),
    (2, 'George R.R. Martin', '1948-09-20', 'American novelist and short story writer, known for A Song of Ice and Fire series.', true);

INSERT INTO books (id, isbn, title, author_id, description, amount_of_pages, price, validated)
VALUES
    (1, '1234567890','Barry Potter and the Philosophers Stone', 1, 'The first book in the Harry Potter series.', 223, 19.99, false),
    (2, '1123456789','Barry Potter and the Chamber of Secrets', 1, 'The second book in the Harry Potter series.', 251, 19.99, true);
