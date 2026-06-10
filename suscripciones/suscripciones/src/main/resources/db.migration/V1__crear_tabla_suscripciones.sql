CREATE TABLE suscripciones (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               usuario_id BIGINT NOT NULL,
                               plan VARCHAR(20) NOT NULL,
                               fecha_inicio DATE NOT NULL,
                               fecha_vencimiento DATE NOT NULL,
                               activa BOOLEAN NOT NULL DEFAULT TRUE
);