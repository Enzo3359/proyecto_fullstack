CREATE TABLE IF NOT EXISTS suscripciones (
id     BIGINT      NOT NULL AUTO_INCREMENT,
usuario_id         BIGINT      NOT NULL,
plan               VARCHAR(20) NOT NULL,
fecha_inicio       DATE        NOT NULL,
fecha_vencimiento  DATE        NOT NULL,
activa             BOOLEAN     NOT NULL DEFAULT TRUE,
PRIMARY KEY (id),
INDEX idx_suscripciones_usuario (usuario_id)
);