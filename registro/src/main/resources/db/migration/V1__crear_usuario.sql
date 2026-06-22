CREATE TABLE usuario (
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre         VARCHAR(100)  NOT NULL,
    email          VARCHAR(150)  NOT NULL UNIQUE,
    contraseña      VARCHAR(255)  NOT NULL,
    fecha_registro DATE          NOT NULL,
    activo         BOOLEAN       NOT NULL DEFAULT TRUE
);
