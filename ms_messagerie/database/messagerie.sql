-- Création de la table 'conversation'
CREATE TABLE conversation (
      id SERIAL PRIMARY KEY,            -- Identifiant unique pour chaque conversation
      iduser1 BIGINT NOT NULL,          -- Identifiant du premier utilisateur
      iduser2 BIGINT NOT NULL           -- Identifiant du second utilisateur
);

-- Création de la table 'message'
CREATE TABLE message (
     id SERIAL PRIMARY KEY,                -- Identifiant unique pour chaque message
     idconversation BIGINT NOT NULL,       -- Référence à l'identifiant de la conversation
     iduser BIGINT NOT NULL,               -- Identifiant de l'utilisateur qui a envoyé le message
     text TEXT NOT NULL,                   -- Contenu du message
     date TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Date et heure d'envoi
);
