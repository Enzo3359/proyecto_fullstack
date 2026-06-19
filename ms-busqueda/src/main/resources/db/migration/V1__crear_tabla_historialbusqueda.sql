CREATE TABLE historial_busqueda (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    termino VARCHAR(100) NOT NULL,
                                    tipo_contenido VARCHAR(50),
                                    genero_id BIGINT,
                                    fecha_busqueda TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    resultados_encontrados INT
);