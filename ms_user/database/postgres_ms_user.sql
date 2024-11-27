
CREATE TABLE "users" (
    iduser SERIAL PRIMARY KEY,        -- Identifiant utilisateur généré automatiquement
    role VARCHAR(10) NOT NULL,       -- Type d'utilisateur (user, admin, host)
    lastname VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL, -- (unique)
    phone VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    biographie TEXT
);

