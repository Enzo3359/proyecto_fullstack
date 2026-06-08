CREATE TABLE IF NOT EXISTS descargas (
id           BIGINT NOT NULL AUTO_INCREMENT,
usuario_id   BIGINT NOT NULL,
contenido_id BIGINT NOT NULL,
estado       VARCHAR(20) NOT NULL,
calidad      VARCHAR(10),
fecha_descarga DATETIME,
PRIMARY KEY (id),
INDEX idx_descargas_usuario (usuario_id)
);