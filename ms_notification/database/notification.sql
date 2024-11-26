CREATE TABLE push_notification (
  iduser INT PRIMARY KEY,
  token  TEXT NOT NULL
);

CREATE TABLE notification (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  iduser INT NOT NULL,
  redirection TEXT,
  body TEXT NOT NULL,
  icon TEXT
);

