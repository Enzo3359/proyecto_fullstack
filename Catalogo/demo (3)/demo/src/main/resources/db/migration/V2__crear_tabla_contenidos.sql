CREATE TABLE IF NOT EXISTS contenido (
 id            BIGINT       NOT NULL AUTO_INCREMENT,
titulo        VARCHAR(200) NOT NULL,
descripcion   TEXT,
 tipo          VARCHAR(20)  NOT NULL,
duracion_min  INT,
anio_estreno  INT,
clasificacion VARCHAR(10)  NOT NULL,
disponible    BOOLEAN      NOT NULL DEFAULT TRUE,
genero_id     BIGINT,
PRIMARY KEY (id),
CONSTRAINT fk_contenido_genero
FOREIGN KEY (genero_id)
REFERENCES genero (genero_id)
);