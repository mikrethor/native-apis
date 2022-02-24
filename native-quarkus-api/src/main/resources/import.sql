CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- schema.sql
CREATE TABLE IF NOT EXISTS ACTOR (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    full_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS MOVIE (
    id UUID NOT NULL DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    year smallint NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS ACTOR_MOVIE_MAPPING (
                                                   actor_id UUID NOT NULL,
                                                   movie_id UUID NOT NULL,
                                                   CONSTRAINT fk_actor
                                                   FOREIGN KEY(actor_id)
    REFERENCES ACTOR(id),
    CONSTRAINT fk_movie
    FOREIGN KEY(movie_id)
    REFERENCES MOVIE(id)
    );


DELETE FROM actor_movie_mapping;
DELETE FROM actor;
DELETE FROM movie;

INSERT INTO actor (id, full_name) VALUES ('9376cfa9-b7f2-4519-82b7-8f3b9248ef24', 'David Sandberg');
INSERT INTO actor (id, full_name) VALUES ('28de2c7a-ac2b-4a7c-88d8-d637ad3e027a', 'David Hasselhoff');
INSERT INTO actor (id, full_name) VALUES ('b8b3564f-89f3-405f-859d-e4d5e54617d8', 'Arnold Schwarzenegger');
INSERT INTO actor (id, full_name) VALUES ('179d3230-9a7f-4d92-b04a-f1b1d164058d', 'Michael Fassbender');

INSERT INTO movie (id, title, year) VALUES ('a2c31765-8958-4862-9675-b5f853e6e299', 'Kung Fury', 2015);
INSERT INTO movie (id, title, year) VALUES ('4ce61940-dc8c-421d-84e0-0ebeecff980d', 'Kung Fury 2', 2022);

--Kung Fury
INSERT INTO actor_movie_mapping (actor_id, movie_id) VALUES ('9376cfa9-b7f2-4519-82b7-8f3b9248ef24', 'a2c31765-8958-4862-9675-b5f853e6e299');
INSERT INTO actor_movie_mapping (actor_id, movie_id) VALUES ('28de2c7a-ac2b-4a7c-88d8-d637ad3e027a', 'a2c31765-8958-4862-9675-b5f853e6e299');

--Kung Fury 2
INSERT INTO actor_movie_mapping (actor_id, movie_id) VALUES ('9376cfa9-b7f2-4519-82b7-8f3b9248ef24', '4ce61940-dc8c-421d-84e0-0ebeecff980d');
INSERT INTO actor_movie_mapping (actor_id, movie_id) VALUES ('b8b3564f-89f3-405f-859d-e4d5e54617d8', '4ce61940-dc8c-421d-84e0-0ebeecff980d');
INSERT INTO actor_movie_mapping (actor_id, movie_id) VALUES ('179d3230-9a7f-4d92-b04a-f1b1d164058d', '4ce61940-dc8c-421d-84e0-0ebeecff980d');