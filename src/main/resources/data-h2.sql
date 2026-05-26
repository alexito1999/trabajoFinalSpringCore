INSERT INTO usuarios (username, email, password, rol) VALUES ('admin', 'admin@email.com', '$2a$10$X96RyLm7Bke/CCruwI4Z2e0r7jD5QHsArms3Yy8P8QmYj.eVd1Q3m', 'ROLE_ADMIN');
INSERT INTO usuarios (username, email, password, rol) VALUES ('user', 'user@email.com', '$2a$10$I2W0EIDpHcG37TdRRZoAt.7lhNwtOMY5b0b3OaIKrlIFS26uIimpa', 'ROLE_USER');


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

--Adicionales de SU

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué etiqueta HTML se usa para crear un enlace?', 1, 3);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (26, 'img');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (26, 'a');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (26, 'div');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (26, 'p');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué protocolo usa HTTPS?', 2, 3);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (27, 'FTP');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (27, 'SMTP');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (27, 'SSL/TLS');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (27, 'SSH');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Cuál es una base de datos relacional?', 0, 2);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (28, 'PostgreSQL');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (28, 'MongoDB');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (28, 'Redis');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (28, 'Cassandra');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué herramienta se usa para contenerización?', 3, 4);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (29, 'Git');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (29, 'Maven');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (29, 'JUnit');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (29, 'Docker');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué lenguaje se ejecuta principalmente en el navegador?', 2, 1);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (30, 'Java');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (30, 'Python');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (30, 'JavaScript');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (30, 'C#');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué comando SQL obtiene datos?', 1, 2);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (31, 'DELETE');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (31, 'SELECT');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (31, 'DROP');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (31, 'UPDATE');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué framework pertenece a Java?', 0, 1);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (32, 'Spring Boot');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (32, 'Laravel');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (32, 'Express');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (32, 'Flask');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué herramienta compila y gestiona dependencias en Java?', 2, 4);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (33, 'Git');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (33, 'Docker');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (33, 'Maven');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (33, 'Postman');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué lenguaje da estilo a páginas web?', 1, 3);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (34, 'HTML');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (34, 'CSS');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (34, 'SQL');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (34, 'Java');

INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué herramienta sirve para pruebas unitarias?', 3, 4);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'Hibernate');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'NodeJS');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'Bootstrap');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'JUnit');

--ADICIONALES DE SM


INSERT INTO pregunta (tipo, enunciado, opcion_correcta, tematica_id) VALUES ('SU', '¿Qué herramienta sirve para pruebas unitarias?', 3, 4);
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'Hibernate');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'NodeJS');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'Bootstrap');
INSERT INTO su_opciones (pregunta_id, opcion) VALUES (35, 'JUnit');

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Cuáles son lenguajes de programación?', 1);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (36, 'Java');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (36, 'Python');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (36, 'HTML');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (36, 'C++');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (36, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (36, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (36, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué tecnologías son de frontend?', 3);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (37, 'HTML');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (37, 'CSS');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (37, 'JavaScript');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (37, 'MySQL');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (37, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (37, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (37, 2);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Cuáles son bases de datos relacionales?', 2);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (38, 'MySQL');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (38, 'PostgreSQL');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (38, 'MongoDB');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (38, 'MariaDB');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (38, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (38, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (38, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué herramientas sirven para testing?', 4);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (39, 'JUnit');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (39, 'Mockito');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (39, 'Docker');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (39, 'Selenium');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (39, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (39, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (39, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué herramientas son de control de versiones?', 4);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (40, 'Git');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (40, 'SVN');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (40, 'Docker');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (40, 'Mercurial');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (40, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (40, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (40, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué son frameworks backend?', 1);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (41, 'Spring Boot');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (41, 'Laravel');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (41, 'Bootstrap');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (41, 'Django');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (41, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (41, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (41, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué tecnologías usan Java?', 1);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (42, 'Spring');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (42, 'Hibernate');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (42, 'JPA');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (42, 'React');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (42, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (42, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (42, 2);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué etiquetas son HTML?', 3);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (43, 'div');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (43, 'span');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (43, 'SELECT');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (43, 'p');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (43, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (43, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (43, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué herramientas son de despliegue o contenedores?', 4);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (44, 'Docker');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (44, 'Kubernetes');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (44, 'Git');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (44, 'Podman');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (44, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (44, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (44, 3);

INSERT INTO pregunta (tipo, enunciado, tematica_id) VALUES ('SM', '¿Qué son sistemas gestores de bases de datos?', 2);
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (45, 'MySQL');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (45, 'PostgreSQL');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (45, 'MongoDB');
INSERT INTO sm_opciones (pregunta_id, opcion) VALUES (45, 'VSCode');
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (45, 0);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (45, 1);
INSERT INTO sm_opciones_correctas (pregunta_id, indice) VALUES (45, 2);