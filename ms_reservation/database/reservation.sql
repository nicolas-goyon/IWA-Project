CREATE TYPE reservation_status AS ENUM ('VALIDATED', 'EXPIRED', 'PENDING');

CREATE TABLE reservation (
     id SERIAL PRIMARY KEY,              -- Identifiant unique pour la réservation
     idlocation INT NOT NULL,            -- Identifiant de la location
     idtraveler INT NOT NULL,            -- Identifiant du voyageur
     date_start DATE NOT NULL,           -- Date de début de la réservation
     date_end DATE NOT NULL,             -- Date de fin de la réservation
     status reservation_status DEFAULT 'PENDING'     -- Indique si la réservation est validée
);

CREATE TABLE review (
    id SERIAL PRIMARY KEY,          -- Identifiant unique pour l'évaluation
    id_reservation INT NOT NULL,    -- Identifiant de la réservation associée
    id_reviewer INT NOT NULL,       -- Identifiant de l'utilisateur qui laisse la note
    id_reviewed INT,-- Identifiant de l'utilisateur qui est noté (peut être nul)
    id_location INT,-- Identifiant de la location qui est noté (peut être nul)
    grade INT CHECK (grade >= 0 AND grade <= 5),  -- Note (exemple : sur 5, avec contrainte)
    comment TEXT NOT NULL,                   -- Commentaire facultatif sur la réservation
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Date de création de l'évaluation
);
