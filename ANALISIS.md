# Análisis del código — Trabajo Final

## 🔴 CRÍTICOS / BUGS

### 1. NullPointerException en `PreguntaController.editarPregunta()`
- **Archivo:** `src/main/java/.../controller/PreguntaController.java:64-66`
- **Problema:** `preguntaServiceImpl.obtenerPregunta(id)` puede devolver `null` y se accede a `p.getId()` sin control.
- **Solución:** Añadir control `if (pregunta == null) { return "redirect:/pregunta/listar"; }` o similar.

### 2. Bug de lógica en `mediaCorrectas()`
- **Archivo:** `src/main/java/.../service/preguntas/PreguntaServiceImpl.java:64-69`
- **Problema:** Divide `countVFCorrectas()` (solo VF correctas) entre el total de TODAS las preguntas. Si hay 1 VF correcta + 1 VF incorrecta + 8 SU, da 10% en vez de 50%.
- **Solución:** Debería dividir solo entre el total de preguntas VF, o cambiar la fórmula para contar aciertos de todos los tipos.

---

## 🟠 DUPLICACIONES

### 3. Patrón `instanceof` triplicado (mapeo entidad → DTO)
- **Archivos:** `JuegoController.java:56-64`, `PreguntaApiController.java:134-142`, `PreguntaController.java:71-78`
- **Problema:** El mismo `if (p instanceof PreguntaVerdaderoFalso) / else if (p instanceof PreguntaSeleccionUnica) / else if (p instanceof PreguntaSeleccionMultiple)` aparece 3 veces.
- **Solución:** Extraer a un método estático `PreguntaMapper.toResponse(Pregunta)`.

### 4. Switch de construcción duplicado
- **Archivos:** `PreguntaApiController.java:148-176` y `PreguntaController.java:106-128`
- **Problema:** Misma lógica VF/SU/SM para construir entidades desde DTO, repetida en REST y MVC.
- **Solución:** Unificar en `PreguntaMapper.buildEntity(PreguntaRequest/PreguntaForm)`.

### 5. Validación de registro duplicada
- **Archivos:** `AuthPageController.java:39-46` y `AuthController.java:55-60`
- **Problema:** Las comprobaciones `existsByUsername` / `existsByEmail` con mensajes hardcodeados están en ambos controladores.
- **Solución:** Mover la validación a `UsuarioService.registrar()` y lanzar excepción.

### 6. Normalización de filtros duplicada
- **Archivos:** `PreguntaApiController.java:52-53` y `PreguntaController.java:42-43`
- **Problema:** `if (tipo != null && tipo.isEmpty()) tipo = null` repetido.
- **Solución:** Mover a un método helper o al servicio.

### 7. `InternalResourceViewResolver` duplicado en tests
- **Archivos:** `AuthPageControllerTest.java:40-45`, `PreguntaControllerTest.java:39-44`, `JuegoControllerTest.java:54-59`, `HomeControllerTest.java:35-40`
- **Problema:** 6 líneas idénticas en 4 tests.
- **Solución:** Clase base abstracta o método factory.

---

## 🟡 CÓDIGO MUERTO / NO USADO

### 8. Excepciones personalizadas nunca lanzadas
- **Archivos:** `exceptions/PreguntaNoEncontradaException.java`, `exceptions/TematicaNoEncontradaException.java`, `exceptions/GlobalExceptionHandler.java:10-22`
- **Problema:** Las excepciones existen y tienen handler, pero ningún servicio o controlador las lanza. Los servicios devuelven `null`.
- **Solución:** Eliminar las 3 clases, o mejor: refactorizar servicios para lanzarlas y eliminar el null-checking manual.

### 9. `PreguntaService.listarPreguntas(Pageable)` nunca usado
- **Archivo:** `service/preguntas/PreguntaService.java:13`
- **Problema:** Definido en la interfaz e implementado, pero ningún controlador lo llama.

### 10. `PreguntaRepository.findByTematicaId(Long, Pageable)` nunca usado
- **Archivo:** `repository/PreguntaRepository.java:16`
- **Problema:** Declarado pero nunca invocado; se usa la versión sin Pageable.

### 11. `totalDisponible` en modelo nunca usado en template
- **Archivo:** `JuegoController.java:71`
- **Problema:** Se añade `totalDisponible` al modelo pero `juego.html` nunca lo referencia.

### 12. `Tematica.setPreguntas()` y `Tematica.setId()` nunca llamados
- **Archivo:** `models/Tematica.java:35-37, 51-53`
- **Problema:** Getters/setters que nunca se invocan.

### 13. `sortable.min.js` (53 KB) nunca referenciado
- **Archivo:** `static/js/sortable.min.js`
- **Problema:** No hay `th:src` ni `<script src>` que lo cargue. El drag & drop del formulario usa HTML5 nativo.
- **Solución:** Eliminar el archivo.

### 14. Clases CSS no usadas
- **Archivo:** `static/css/styles.css`
- **Selectores:** `.form-card .form-check-input`, `.form-card .form-check-label`, `#campoOpcionCorrectaSU`, `#campoOpcionesCorrectasSM`
- **Problema:** Definen estilos para elementos que no existen en ningún template.

### 15. Clase `logout-link` usada pero no definida
- **Archivo:** `templates/fragments/header.html:52`
- **Problema:** El botón tiene `class="logout-link"` pero no hay `.logout-link { }` en `styles.css`. Funciona solo por los estilos inline.

### 16. `main { flex: 1; }` duplicado en CSS
- **Archivo:** `static/css/styles.css:49-52` y `:54-56`
- **Problema:** El segundo bloque solo repite `flex: 1` y sobreescribe `position: relative; z-index: 1` del primero.

### 17. Ficheros `:Zone.Identifier` (48 archivos)
- **Ubicación:** `static/bootstrap-5.3.8/**/*:Zone.Identifier`
- **Problema:** Metadatos de Windows que no sirven en Linux.
- **Solución:** Eliminarlos.

### 18. Variantes no usadas de Bootstrap JS/CSS
- **Ubicación:** `static/bootstrap-5.3.8/js/` y `static/bootstrap-5.3.8/css/`
- **Problema:** Solo se cargan `bootstrap.min.css` y `bootstrap.bundle.min.js`. El resto (~40 archivos, ~1 MB) sobra.

---

## 🟢 MEJORAS Y OPTIMIZACIONES

### 19. Inconsistencia en inyección de dependencias
- **Problema:** Algunas clases usan `@Autowired` en campos (`PreguntaServiceImpl`, controladores REST/MVC), otras usan constructor (`UsuarioServiceImpl`, `AuthController`). Spring recomienda constructor injection.

### 20. `PreguntaService` interfaz eludida
- **Problema:** Los 4 controladores inyectan `PreguntaServiceImpl` directamente, no la interfaz `PreguntaService`. Viola el principio de inversión de dependencias.

### 21. Búsqueda ineficiente de temática en `JuegoController`
- **Archivo:** `JuegoController.java:46-48`
- **Problema:** Obtiene TODAS las temáticas y filtra con stream en vez de buscar por ID directamente.

### 22. OSIV activado (`spring.jpa.open-in-view=true`)
- **Archivo:** `application.properties:4`
- **Problema:** Mantiene EntityManager abierto durante el renderizado de vistas. Anti-patrón que puede causar problemas de rendimiento y lazy loading inesperados.

### 23. `@Column(name = "tipo")` redundante
- **Archivo:** `models/Pregunta.java:36`
- **Problema:** El nombre del campo ya es `tipo`, JPA lo mapearía igual.

### 24. `PreguntaForm` sin Lombok
- **Archivo:** `dto/PreguntaForm.java`
- **Problema:** El resto de DTOs usan Lombok `@Data`; este tiene getters/setters escritos a mano.

### 25. `opcionesCorrectas` como `String` en `PreguntaForm`
- **Archivo:** `dto/PreguntaForm.java:26`
- **Problema:** Es un `String` que luego se parte con coma en el controlador. Debería ser `List<Integer>` para consistencia.

### 26. Silenciamiento de excepciones en `GlobalModelAdvice`
- **Archivo:** `config/GlobalModelAdvice.java`
- **Problema:** Todos los métodos envuelven la lógica en `try { ... } catch (Exception e) { }` vacío, ocultando errores reales.

### 27. `Environment env` → `@Value` en `GlobalModelAdvice`
- **Archivo:** `config/GlobalModelAdvice.java:15-16`
- **Problema:** Se puede sustituir `env.getProperty("spring.profiles.active")` por `@Value("${spring.profiles.active:dev}")`, más simple y sin try-catch.

### 28. `cantidad` negativa tratada como "Todas"
- **Archivo:** `JuegoController.java:41`
- **Problema:** Si `cantidad = -5`, la condición `cantidad > 0` es falsa, y devuelve todas las preguntas. Debería validarse como error.

### 29. Sin archivo `messages.properties`
- **Problema:** Todos los textos de la UI están hardcodeados en español en los templates. No hay internacionalización ni externalización.

### 30. `opcionCorrecta` como `int` primitivo (valor por defecto 0)
- **Archivos:** `models/PreguntaSeleccionUnica.java:21`, `dto/PreguntaRequest.java:31`
- **Problema:** 0 es un índice válido (primera opción). Si no se establece, podría indicar una opción incorrecta sin detectarlo. Debería ser `Integer` (nullable).

### 31. `@AllArgsConstructor` en `PreguntaRequest` frágil
- **Archivo:** `dto/PreguntaRequest.java:14`
- **Problema:** El constructor generado tiene parámetros posicionales. Si se añade/cambia un campo, las llamadas existentes compilan pero pueden intercambiar valores.

### 32. Query nativa frágil con nombre de tabla hardcodeado
- **Archivo:** `repository/PreguntaRepository.java:27-28`
- **Problema:** La `@Query(nativeQuery = true, ...)` usa `pregunta` hardcodeado. Si la tabla se renombra, falla en runtime.

### 33. Validación JWT sin distinción entre expirado y malformado
- **Archivo:** `security/JwtTokenProvider.java:51-60`
- **Problema:** Ambos casos devuelven `false`; no se puede responder con un mensaje específico.

### 34. Animación escalonada limitada a 9 tarjetas
- **Archivo:** `static/css/styles.css:1573-1581`
- **Problema:** `:nth-child(1)` a `:nth-child(9)`. Con tamaño de página > 9, la tarjeta 10+ no tiene animación.

### 35. Tests sin `verify()` en controladores MockMvc
- **Archivos:** Todos los `*ControllerTest.java`
- **Problema:** Ningún test de controlador verifica que los métodos del servicio fueron llamados con los argumentos esperados.

### 36. Tests faltantes para API endpoints
- **Archivo:** `PreguntaApiControllerTest.java`
- **Problema:** No hay tests para `POST`, `PUT` ni `DELETE` de la API REST.

### 37. Comparación exacta de `double` sin tolerancia
- **Archivo:** `PreguntaServiceTest.java:133`
- **Problema:** `isEqualTo(50.0)` para un double; mejor usar `isCloseTo(50.0, within(0.01))`.

### 38. IDs hardcodeados como `999L` / `99L`
- **Archivos:** `PreguntaServiceTest.java:97,125`, `PreguntaApiControllerTest.java:114`, `JuegoControllerTest.java:172`
- **Problema:** Usar constantes como `NON_EXISTENT_ID = -1L` sería más claro.

### 39. Solapamiento de rutas en `SecurityConfig`
- **Archivo:** `config/SecurityConfig.java:69-70`
- **Problema:** Las rutas específicas (`/pregunta/nueva`, `/pregunta/guardar`) están antes que el comodín (`/pregunta/**`). Funciona pero es frágil si se reordenan.

### 40. `<a>` envolviendo `<div>` en `jugar.html`
- **Archivo:** `templates/jugar.html:38-51`
- **Problema:** HTML inválido (elemento inline contiene bloque). Los navegadores lo manejan, pero no es estándar.

---

## Resumen de acciones recomendadas

| Prioridad | Acción |
|-----------|--------|
| 🔴 Inmediata | Fix NPE en `PreguntaController.editarPregunta()` (#1) |
| 🔴 Inmediata | Fix bug `mediaCorrectas()` (#2) |
| 🟠 Próximo | Extraer mapeo instanceof a `PreguntaMapper` (#3) |
| 🟠 Próximo | Eliminar excepciones/código muerto (#8-#18) |
| 🟠 Próximo | Unificar validación de registro en servicio (#5) |
| 🟡 Mejora | Refactorizar inyección a constructor (#19) |
| 🟡 Mejora | Usar interfaz `PreguntaService` en controladores (#20) |
| 🟡 Mejora | Añadir `verify()` en tests (#35) |
| 🟡 Mejora | Tests para POST/PUT/DELETE API (#36) |
| 🟢 Optativo | Externalizar strings a `messages.properties` (#29) |
| 🟢 Optativo | Eliminar `sortable.min.js` y variantes Bootstrap no usadas (#13, #18) |
| 🟢 Optativo | Eliminar archivos `:Zone.Identifier` (#17) |
