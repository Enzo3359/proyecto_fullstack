CREATE TABLE IF NOT EXISTS reproducciones (
id             BIGINT   NOT NULL AUTO_INCREMENT,
usuario_id     BIGINT   NOT NULL,
contenido_id   BIGINT   NOT NULL,
segundo_actual INT      NOT NULL DEFAULT 0,
completado     BOOLEAN  NOT NULL DEFAULT FALSE,
ultima_vez     DATETIME,
PRIMARY KEY (id),
UNIQUE KEY uk_usuario_contenido (usuario_id, contenido_id)
);