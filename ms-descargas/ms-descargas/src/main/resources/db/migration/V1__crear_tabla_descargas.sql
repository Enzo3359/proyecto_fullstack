CREATE TABLE descargas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    contenido_id BIGINT NOT NULL,
    estado VARCHAR(20) NOT NULL,
    calidad VARCHAR(10),
    fecha_descarga DATETIME DEFAULT CURRENT_TIMESTAMP
);