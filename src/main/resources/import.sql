INSERT INTO tematica (nombre) VALUES ('Programación');
INSERT INTO tematica (nombre) VALUES ('Bases de Datos');
INSERT INTO tematica (nombre) VALUES ('Desarrollo Web');
INSERT INTO tematica (nombre) VALUES ('Herramientas');

INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Java es un lenguaje compilado e interpretado', true, 1);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Python usa llaves para definir bloques', false, 1);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Spring Boot está basado en Java', true, 1);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'JavaScript y Java son lo mismo', false, 1);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Un bucle for se usa para iterar', true, 1);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'SQL significa Structured Query Language', true, 2);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'MySQL es un motor de base de datos relacional', true, 2);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'NoSQL usa tablas y relaciones', false, 2);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Una clave primaria identifica unívocamente un registro', true, 2);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'SELECT * FROM borra todos los datos', false, 2);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'HTML es un lenguaje de programación', false, 3);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'CSS sirve para dar estilo a páginas web', true, 3);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Thymeleaf es un motor de plantillas para Java', true, 3);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Bootstrap es un framework de CSS', true, 3);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'HTTP es un protocolo de transferencia de hipertexto', true, 3);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Docker es un navegador web', false, 4);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Git sirve para control de versiones', true, 4);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'JPA es una especificación de persistencia en Java', true, 4);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'Maven es una herramienta de construcción', true, 4);
INSERT INTO pregunta (tipo, enunciado, correcto, tematica_id) VALUES ('VF', 'JUnit se usa para pruebas unitarias', true, 4);

-- SU (Selección única)
INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Cuál de los siguientes lenguajes se ejecuta en la JVM?', 0, 1);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (21, 'Java');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (21, 'Python');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (21, 'JavaScript');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (21, 'C++');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué comando de Git crea una nueva rama?', 2, 4);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (22, 'git commit');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (22, 'git merge');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (22, 'git branch');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (22, 'git push');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué motor de base de datos es NoSQL?', 1, 2);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (23, 'MySQL');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (23, 'MongoDB');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (23, 'PostgreSQL');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (23, 'MariaDB');

-- SM (Selección múltiple)
INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Cuáles son frameworks de CSS?', 3);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (24, 'Bootstrap');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (24, 'Tailwind CSS');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (24, 'Django');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (24, 'Bulma');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (24, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (24, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (24, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué herramientas son sistemas de control de versiones?', 4);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (25, 'Git');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (25, 'Docker');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (25, 'Subversion (SVN)');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (25, 'Kubernetes');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (25, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (25, 2);
