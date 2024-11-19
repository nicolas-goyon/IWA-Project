CREATE TABLE notification (
  id SERIAL PRIMARY KEY,
  icon TEXT NOT NULL,
  title TEXT NOT NULL,
  iduser INT NOT NULL,
  redirection TEXT NOT NULL
);
