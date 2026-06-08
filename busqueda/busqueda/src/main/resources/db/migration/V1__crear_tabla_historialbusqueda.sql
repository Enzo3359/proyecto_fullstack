CREATE TABLE IF NOT EXISTS historial_busqueda (
id BIGINT       NOT NULL AUTO_INCREMENT,
termino               VARCHAR(255) NOT NULL,
tipo_contenido        VARCHAR(50),
genero_id             BIGINT,
fecha_busqueda        DATETIME,
resultados_encontrados INT,
PRIMARY KEY (id),
INDEX idx_busqueda_termino (termino),
INDEX idx_busqueda_tipo (tipo_contenido)
);