CREATE TABLE contenido(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    tipo VARCHAR(20) NOT NULL,
    duracion_min INT NOT NULL,
    anio_estreno INT NOT NULL,
    clasificacion VARCHAR(10) NOT NULL,
    disponible BOOLEAN NOT NULL,
    genero_id BIGINT NOT NULL,
    CONSTRAINT fk_genero
    FOREIGN KEY(genero_id)
    REFERENCES genero(genero_id)
);