CREATE TABLE IF NOT EXISTS usuario (
id BIGINT     NOT NULL AUTO_INCREMENT,
nombre         VARCHAR(100) NOT NULL,
email          VARCHAR(150) NOT NULL UNIQUE,
contrasena     VARCHAR(255) NOT NULL,
fecha_registro DATE         NOT NULL,
activo         BOOLEAN      NOT NULL DEFAULT TRUE,
PRIMARY KEY (id)
);