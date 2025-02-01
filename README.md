# DOCUMENTACIÓN Y SEGUIMIENTO - PROYECTO FINAL

## Descripción 

## DESCRIPCIÓN DE LAS FUNCIONALIDADES DE LA APLICACIÓN 


# SEGUIMIENTO DE LOS COMMIT

## 1 y 2 Commit: J.Compose + Room | J.Compose + Room + NavController

El proyecto se empieza con un enfoque de investigación y aclaración de conceptos, en el funcionamiento 
de las implementaciones que se piden para complementar el proyecto en su finalización.

### Implementación de librerías, versiones y dependencias
    
Se comienza con la configuración de versiones y dependencias esenciales para la 
utilización de: Retrofit, Room, Jetpack Compose y NavController.

Por ejemplo en mi proyecto tengo esto en: 

```build.gradle.kts 
plugins {
    // Se aplican los plugins necesarios para el proyecto
    alias(libs.plugins.android.application) // Plugin para una aplicación Android
    alias(libs.plugins.kotlin.android) // Plugin para habilitar Kotlin en Android
    id("kotlin-kapt") // Plugin para habilitar Kotlin Annotation Processing Tool (KAPT)
}

android {
    // Configuración general del proyecto Android
    namespace = "com.torre.proyectofinal" // Espacio de nombres para el proyecto
    compileSdk = 35 // Versión del SDK que se utilizará para compilar el proyecto

    defaultConfig {
        // Configuración predeterminada para la aplicación
        applicationId = "com.torre.proyectofinal" // Identificador único de la aplicación
        minSdk = 26 // Versión mínima de Android requerida
        targetSdk = 35 // Versión del SDK con la que se ha probado la aplicación
        versionCode = 1 // Código de versión para el control de actualizaciones
        versionName = "1.0" // Nombre de la versión de la aplicación

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner para pruebas 
        vectorDrawables {
            useSupportLibrary = true // Habilita el uso de la librería de soporte para vectores
        }
    }

    buildTypes {
        // Tipos de compilación, como Debug o Release
        release {
            isMinifyEnabled = false // No se habilita la minimización del código en la versión release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), // Archivo Proguard predet.
                "proguard-rules.pro" // Archivo Proguard adicional para reglas personalizadas
            )
        }
    }

    compileOptions {
        // Configura las opciones de compatibilidad para la compilación
        sourceCompatibility = JavaVersion.VERSION_1_8 // Compatibilidad con Java 8
        targetCompatibility = JavaVersion.VERSION_1_8 // Compatibilidad con Java 8
    }

    kotlinOptions {
        // Configuración de las opciones de Kotlin
        jvmTarget = "1.8" // Objetivo de la JVM, se utiliza Kotlin 1.8
    }

    buildFeatures {
        // Habilita características adicionales para el proyecto
        compose = true // Habilita Jetpack Compose para la UI
    }

    composeOptions {
        // Configuración adicional para Jetpack Compose
        kotlinCompilerExtensionVersion = "1.5.1" // Versión del compilador de extensión para Compose
    }

    packaging {
        // Configuración de empaquetado de recursos
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}" // Excluye ciertas licencias de recursos
        }
    }
}

dependencies {
    // Dependencias principales de Jetpack y AndroidX
    implementation(libs.androidx.core.ktx) // Librería que agrega funcionalidades de extensión para la API de Android
    implementation(libs.androidx.lifecycle.runtime.ktx) // Librería que extiende la API de Lifecycle para Kotlin
    implementation(libs.androidx.activity.compose) // Soporte para integrar Compose con actividades de Android
    implementation(platform(libs.androidx.compose.bom)) // BOM (Bill of Materials) para las dependencias de Compose
    implementation(libs.androidx.ui) // Dependencia principal de Jetpack Compose UI
    implementation(libs.androidx.ui.graphics) // Dependencia para gráficos en Compose
    implementation(libs.androidx.ui.tooling.preview) // Herramientas para previsualizar UI en Compose
    implementation(libs.androidx.material3) // Material Design 3 para Compose

    // Dependencias de Room (Base de datos)
    implementation(libs.androidx.room.runtime) // Librería principal para usar Room
    implementation(libs.androidx.navigation.compose) // Librería para navegación con Compose
    implementation(libs.androidx.runtime.livedata) // Soporte para LiveData en Room
    //implementation(libs.androidx.ui.test.android) // (Comentada) Dependencia para pruebas de UI en Android
    kapt(libs.androidx.room.compiler) // Procesador de anotaciones para Room (KAPT)
    implementation("androidx.room:room-ktx:2.5.2") // KTX para Room, proporciona extensiones para Room en Kotlin

    // Dependencias de Coroutines (para operaciones asincrónicas)
    implementation(libs.kotlinx.coroutines.core) // Coroutines Core, para operaciones asincrónicas generales
    implementation(libs.kotlinx.coroutines.android) // Coroutines Android, extensión para trabajar en el hilo principal

    // Dependencias de Lifecycle (para gestionar ciclo de vida y datos en la UI)
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // KTX para ViewModel, que ayuda a gestionar el ciclo de vida
    implementation(libs.androidx.lifecycle.livedata.ktx) // KTX para LiveData, para gestionar datos reactivos

    // Dependencias para pruebas unitarias y de UI
    testImplementation(libs.junit) // Dependencia para pruebas unitarias con JUnit
    androidTestImplementation(libs.androidx.junit) // Dependencia para pruebas instrumentadas con JUnit
    androidTestImplementation(libs.androidx.espresso.core) // Dependencia para pruebas de UI con Espresso

    // Dependencias adicionales para pruebas de Compose
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM para pruebas con Compose

    //Dependencias habilitadas por imposibilidad de versión
    //androidTestImplementation(libs.androidx.ui.test.junit4) // (Comentada) Dependencia para pruebas de UI en Compose
    //debugImplementation(libs.androidx.ui.tooling) // (Comentada) Herramientas de depuración para Compose
    //debugImplementation(libs.androidx.ui.test.manifest) // (Comentada) Manifesto de prueba para Compose
}

kapt {
    correctErrorTypes = true // Configura KAPT para corregir tipos de error en la anotación
}
```

La elección de las librerías y dependencias dependerá del tipo de proyecto y las funcionalidades
que se implementen, pero configurar correctamente las versiones y dependencias es un paso 
fundamental para garantizar el correcto funcionamiento del proyecto.

### Investigación previa sobre el funcionamiento de Jetpack Compose + Room + NavController

##### Jetpack Compose
Jetpack Compose es un framework moderno para construir interfaces de usuario en Android de manera
declarativa. En lugar de utilizar XML, Compose permite construir la UI directamente en código
mediante anotaciones @Composable, lo que simplifica la creación y gestión de interfaces.

Jetpack Compose vs Forma Tradicional de Crear Interfaces

Una diferencia clave con la forma tradicional de crear interfaces (usando XML y un patrón como 
MVVM) es que, en Jetpack Compose, la UI se define completamente en el código Kotlin. Esto hace 
que el proceso sea más ágil y fluido, sin necesidad de gestionar archivos XML, facilitando la 
modularidad y reutilización del código.

Conclusión:

Tras investigar y aplicar Jetpack Compose, concluyo que esta metodología es más eficiente y
controlada en comparación con el enfoque tradicional. Además, facilita la creación de interfaces 
más limpias y escalables.

##### Room

Room es una librería de persistencia de datos en Android que proporciona una abstracción sobre
SQLite. Facilita la creación de bases de datos locales utilizando objetos Kotlin y maneja
automáticamente las operaciones SQL.

 * Clases implementadas:

      - AppDatabase: Es una clase abstracta que extiende RoomDatabase y actúa como el punto de 
        acceso principal a la base de datos. Define las entidades (tablas) de la base de datos y
        proporciona acceso a los DAOs correspondientes.

      - UserDao: Es una interfaz que define las operaciones de acceso a los datos en la base de
        datos (como insertar, eliminar, modificar y consultar).

      - User: Es una clase de datos (data class) que define la estructura de la base de datos.
        Está anotada con @Entity, lo que indica que es una tabla en la base de datos.

##### NavController

NavController es un componente de Jetpack Navigation que facilita la gestión de la navegación entre
pantallas (fragments) en una aplicación. Utiliza un archivo de navegación (nav_graph) para
definir los destinos y las acciones de navegación.

      - NavGraph: Es un grafo que contiene los destinos y las acciones de navegación entre ellos.

Conclusión:

La implementación de NavController mejora la gestión de la navegación, ya que elimina la necesidad 
de declarar explícitamente las pantallas en el AndroidManifest.xml. Además, facilita el control de 
las transiciones entre pantallas y permite manejar la navegación de manera más flexible y modular.



##### Retrofit

Es una librería cliente HTTP para Android y Java que facilita la conexión a APIs RESTful, 
permitiendo realizar peticiones HTTP (GET, POST, PUT, DELETE) y obtener respuestas de forma
fácil, serializando automáticamente los datos JSON a objetos Java/Kotlin.

![Ejecución de 1 y 2 commit implementados](Imagenes/commit.png)

## Examinación de los requisitos del proyecto y planificación de sus requisitos 

![Establecimiento de puntos clave y planificación](Imagenes/analisisyplanificacion.png)


## 3 Commit: Inicio de implementacion de la estructura planificada :
##           Restructuración de proyecto y creación de pantalla de Bienvenida

### 1. Restructuración de orden de clases del proyecto

![Restructuració de proyecto](Imagenes/estructura.png)

Una vez estudiados los puntos clave del proyecto y las implementaciones necesarias, se toma la 
decisión de reestructurar la organización del mismo, implementando los siguientes cambios:

    1. Clase NavController:
        Esta clase se encargará de gestionar la navegación entre las diferentes pantallas del proyecto. 
        Su objetivo es centralizar el control de la navegación, lo que permite tener una organización más
        controlada y limpia. Esto es importante, ya que el proyecto involucra la implementación
        de varias pantallas con diversas relaciones entre ellas.
    2. Clases separadas para cada pantalla:
        Con el objetivo de garantizar la modularidad y escalabilidad del proyecto a largo plazo,
        se ha decidido crear una clase para cada pantalla, permitiendo gestionar las 
        pantallas de manera más independiente, facilitando su mantenimiento, cambios y la 
        implementación de nuevas funcionalidades o pantallas en el futuro.

### 2. Creación de pantalla de bienvenida

La implementación de la pantalla de bienvenida, tras la reestructuración de la organización del 
proyecto, no presentó demasiada complicación. El proceso fue directo, ya que se tenía claro el 
lugar donde se debía establecer la implementación de la interfaz de usuario (UI). En el
MainActivity, simplemente se realizó una referencia para llamar a esta pantalla.

 * Funcionalidad más relevante 

     ```Botón para navegar a la siguiente pantalla
        Button(
            onClick = {  // La acción que se ejecuta cuando el botón es presionado
                // Cuando el botón es presionado, navegamos a la pantalla del formulario
                AppNavigator.navigateToBienvenida(navController)  // Llama al método de navegación 'navigateToBienvenida' de AppNavigator
            }
        ) 
     ```
    !Al tener la navegación entre las pantallas centralizado en la clase AppNavigator, solo hay que 
    llamar a la función de la clase que indique la pantalla que se quiero ir ¡¡¡
          
![Pantalla de Bienvenida](Imagenes/pantallabienvenida.png)

## 4 Commit: Cambios UI de la pantalla de Inicio y establecimiento de condiciones de navegación 
##         entre pantallas: Modificación UI Inicio | implantación de condiciones de navegación 

 * Funcionalidad más relevantes:

    ```Botón de validación entre pantallas
        Button(
            onClick = {
                if (name.value.isBlank() || email.value.isBlank()) {
                    errorMessage.value = "Ambos campos son obligatorios."
                } else {
                    errorMessage.value = ""
                    userViewModel.getUserByEmail(email.value) { user ->
                        if (user != null) {
                            navigateToConsultaUser(navController, user)

                        } else {
                            navigateToRegistroUser(navController)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar Usuario")
        }
     ```
    - !Las condiciónes para la navegacion entre pantallas se decidio resolver con un if 
     ya que las condiciones de la navegación era clara: 
        * Si : los datos introducidos coincidian con los datos de la BBDD se va a la pantalla de Consula
        * Sino : se va a la pantalla de registro

    - La consulta a la BBDD se hizo a trávez de una query en la clase UserData , que en la encargada
      de comunicarse con la BBDD
   
    ```@Query("SELECT * FROM user WHERE email = :email LIMIT 1")
        suspend fun getUserByEmail(email: String): User?
    ```

## Commit 5  y 6 : Modificación UI Registro | Implementación de notificación
##                 Implementación de notificación datos UI, datos de fecha y contador

* Funcionalidad más relevantes:

  - Pantalla de RegistroScreen 
  
      ```
    // Mostrar el Dialog en el centro de la pantalla después de registrar
          if (showDialog) {
              // LaunchedEffect para hacer que el Dialog desaparezca después de un retraso
              LaunchedEffect(Unit) {
                  delay(2000)  // Esperar 2 segundos (puedes ajustar este valor)
                  showDialog = false  // Cerrar el Dialog
                  navController.popBackStack()  // Regresar a la pantalla de inicio
              }

              AlertDialog(
                  onDismissRequest = { showDialog = false },
                  title = { Text("Registro exitoso") },
                  text = { Text("El usuario ha sido registrado correctamente.") },
                  confirmButton = {}  // Sin botón, solo muestra el mensaje y lo quita después de 2 segundos
              )
          }
      ```
    El LaunchedEffect, que usa para iniciar una tarea en segundo, lo que me permite que al añadir los datos
    en la BBDD se combiarta en TRUE y se muestre la notificación que esta en segundo plano unos segundo
    y despúes se oculte.
  
  - Pantalla de ConsultaScreen:
  
    - Se establecen dos variables nuevas en la clase User, para poder guardar los datos en la BBDD:
    
    ```
      // Fecha de registro en formato String (puedes usar Long si prefieres timestamp)
      val registrationDate: String,

    // Contador de accesos
    val accessCount: Int = 0
    ```
    y en la clase consulta establecemos la llamada a las variables y el contador que sumara 1 cada vez
    que el jugador accede a Consulta 

     ``` 
    // Obtener los datos del usuario por su correo electrónico
    LaunchedEffect(userEmail) {
    mainViewModel.getUserByEmail(userEmail) { retrievedUser ->
    if (retrievedUser != null) {
    user = retrievedUser
    
    // Incrementar el contador de accesos al cargar la pantalla
    mainViewModel.incrementAccessCount(userEmail)

                // Actualizamos la fecha antigua (lo que estaba almacenado)
                currentNewDate = mainViewModel.getNewDate()  // Establecer la fecha nueva
            } else {
                Log.e("ConsultaScreen", "Usuario no encontrado con email: $userEmail")
            }
        }
    }
     ``` 
    
## Commit 7: Implementar variables fechas (nueva/antigua) | implementación de Hilos(Runnable) |
##           Implementacion de Retrofit

* Funcionalidad más relevantes:

  - Pantalla de ConsultaScreen:
  
    - Variables fecha nueva/antigua

        - En este cado utilizamos el Usas LaunchedEffect para cargar los datos del usuario al inciar la
          pantalla de Consulta, bajo la comparación del correo del usuario
        - Implementación de las variables de las fechas: se establecen dos variables para las fechas (nueva/antigua)
          se establece que en cada ejecución la variable de la fecha antigua se actualize con los datos de la
          variable de fecha nueva y se guarde y la fecha nueva se limpie hasta mostrar en tiempo real la fecha actual.

          ``` 
          Button(
          onClick = {
          // Al cerrar, actualizar la fecha antigua
          mainViewModel.updateOldDate()  // Actualiza la fecha antigua
          showNotification = false  // Cerrar la notificación
          },
          modifier = Modifier.fillMaxWidth() // Ocupa el 100% del ancho
          ) {
          Text("Cerrar")
          }
        ```
      - implementación de Hilos(Runnable):
    
        * Se utiliza Handler y Runnable para ejecutar el trozo de código que establece el hilo, 
          el valor del cual se maneja con showApiReminder, cuyos valores serán: true/false
          Looper.getMainLooper(): se utiliza para asegurar que el Runnable se ejecute en el hilo principan
    
        * Inicio hilo:
          LaunchedEffect(Unit): esto asegura que el código dentro del bloque se ejecute una vez que la 
          pantalla Consulta esta activada.

           ``` 
          LaunchedEffect(Unit) {
          handler.post(apiReminderRunnable)
          }
           ``` 
          handler.post(apiReminderRunnable) para iniciar la ejecución del Runnable que alternará
          la visibilidad de la notificación.
    
        * Control de la visibilidad

          El  showApiReminder es el que controla si la notificación se muestra o no. Mientras 
          showApiReminder sea true, la notificación será visible, y cuando se ponga en false, desaparecerá
        
          ``` 
           if (showApiReminder) {
            Box(
               modifier = Modifier
                 .fillMaxSize()
                 .wrapContentSize(Alignment.TopCenter)
                 .background(Color.Yellow.copy(alpha = 0.8f))
                 .padding(16.dp)
             ) {
                Text("🔔 Recuerda que puedes consultar la API", fontSize = 16.sp, color = Color.Black)
            }
             }
            ```   
        * Botón para detener el hilo
    
         ``` 
          Button(
             onClick = {
               showApiReminder = false  // Ocultar la notificación
               handler.removeCallbacks(apiReminderRunnable)  // Detener el hilo
            },
            modifier = Modifier.fillMaxWidth()
           ) {
            Text("Consulta de Usuario", fontSize = 24.sp)
           }

          ``` 
      
        - implementación de Retrofit
        
            1. Añadir las dependecias para poder utilizar Retrofil al archivo gradle
            
            ``` 
            // Retrofit
            implementation("com.squareup.retrofit2:retrofit:2.9.0")
            implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Convierte JSON en objetos de Kotlin

            // OkHttp (para ver logs de las peticiones, útil para depuración)
            implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
            ``` 
            Además de : 

            ```
            packaging {
            resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST"  // indice de lista de datos 
            excludes += "/META-INF/DEPENDENCIES"  // Agregado para excluir el archivo DEPENDENCIES
            }
            }
            ```
             ! Evitarán que ese archivo cause conflicto durante la compilación ¡
    
            También se debe añadir al Manifest :

            ```
            <!-- Agregar el permiso de Internet -->
            <uses-permission android:name="android.permission.INTERNET"/>
           ```
      
             ! Esto es necesario si la aplicación necesita navegación o interacción con internet para obtener los 
              datos de la API ¡

           2. Establecer la estructura donde se manerara el flujo de datos de Retrofit

          ![Estructura del proyecto con Retrofit](Imagenes/estructura_retrofit.png)
    
           3. Establecer comportamiento de cada clase Retrofit, el flujo es:
      
                * MovieApiService: Este servicio interactúa con la API para obtener los datos especificados
                * MovieRepository: se encargará de manejar las llamadas a la API, y luego pasará los datos al ViewModel
                * MovieResponse: Esta clase es una representación de la respuesta de la AP
                  * MovieResponse: Contiene una lista de datos (results), que es la respuesta principal que se recibe de la API.
                  * Movie: Representa la información de una película individual, representado por campos
                * RetrofitInstance: es una clase singleton, configura y proporciona la instancia de Retrofit para interactuar con la API 
                  * BASE_URL: La URL base de la API (en este caso, la de The Movie Database)
                  * OkHttpClient: Se utiliza para realizar las solicitudes HTTP. Aquí se crea un cliente básico para gestionar esas solicitudes
                  * retrofit: Instancia de Retrofit configurada con la URL base, el cliente de OkHttp y un convertidor Gson para convertir las respuestas JSON en objetos Kotlin
                  * movieApiService: La interfaz que define las funciones de la API (como obtener películas). Se crea mediante Retrofit usando la instancia retrofit

           4. Establecemos el flujo de información recogida para su visualización 
      
              * En el MainViewModel establecemos el llamado a los datos del repositorio
      
                  ```// Obtener películas populares desde el repositorio
                       fun getRandomMovie(language: String) {
                        viewModelScope.launch {
                     try {
                     val response = movieRepository.getPopularMovies(apiKey, language)
                     val randomMovie = response.results?.random() // Obtener una película aleatoria
                     _movies.postValue(MovieResponse(results = listOf(randomMovie))) // Pasamos solo esa película
                     } catch (e: Exception) {
                     Log.e("MainViewModel", "Error obteniendo película aleatoria: ${e.message}")
                     }
                     }
                     }
                  ```
                    * Llama al repositorio (movieRepository.getPopularMovies) para obtener las películas populares desde la API.
                    * Obtiene una película aleatoria de la lista de resultados.
                    * Actualiza el LiveData _movies con esa única película aleatoria.
      
              * Opcional si la extructura es modular, en el MainViewModelFactory , también se establece
                el parámetro de retrofit (movieRepository), para que los pase al MainViewModel cuando es creado.
                 !Retrofit influye indirectamente a través del movieRepository, que es responsable de hacer 
                  las solicitudes de red para obtener las películas desde la API usando Retrofit¡

## Commit 8: Establecer comportamiento variables contadores | implementación de biblioteca Coil |
##           Mejoras de las UIs | APIs con imagenes (J.Compose e Hilos)

  * Funcionalidad más relevantes:

    - Implementación de condiciones de variables Contador:

       1. Usuario nuevo se registra e inicia sesión, pero nunca entra a ConsultaScreen
            ✅ Contador antiguo = 0
            ✅ Contador nuevo = 0
       2.Usuario nuevo entra a ConsultaScreen por primera vez en esa sesión
            ✅ Contador antiguo = 0
            ✅ Contador nuevo = 1
       3. Usuario cierra sesión o reinicia la aplicación y vuelve a iniciar sesión, luego entra a ConsultaScreen
            ✅ Contador antiguo = 1
            ✅ Contador nuevo = 2
       4. Usuario se registra, pero cierra la aplicación antes de iniciar sesión y entrar a ConsultaScreen
          Al volver a iniciar sesión y entrar a ConsultaScreen:
            ✅ Contador antiguo = 0
            ✅ Contador nuevo = 1

            ```//Condición de botón de pantalla Inicio para pasar a pantalla Consulta
               Button(
            onClick = {
                if (name.value.isBlank() || email.value.isBlank()) {
                    errorMessage.value = "Ambos campos son obligatorios."
                } else {
                    errorMessage.value = ""
                    userViewModel.getUserByEmail(email.value) { user ->
                        if (user != null) {
                            // Si el contador es 0 (primer acceso), incrementamos el contador
                            if (user.accessCount == 0) {
                                userViewModel.incrementAccessCount(email.value)
                            }

                            // Navegar a la pantalla de consulta del usuario
                            navigateToConsultaUser(navController, user)
                        } else {
                            // Si el usuario no existe, navegar a la pantalla de registro
                            navigateToRegistroUser(navController)
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
             ) {
            Text("Inicio de Sesión")
            }
            ```
    - Implementación biblioteca Coli:
      
        ! Coil es una biblioteca de carga de imágenes para Jetpack Compose, 
          que permite cargar imágenes desde una URL¡
 
        1. Implementar las depencias en el gragle 

           ```
             // Coil (para cargar imágenes desde URL)
             implementation("io.coil-kt:coil-compose:2.2.0")
    
           ```
        2. Importar donde se quiere mostrar  
           
            ``` 
           import coil.compose.AsyncImage
            ```
         
        3. Añadir: 
        
         ``` 
    
          // Mostrar la imagen de la película
           AsyncImage(
          model = "https://image.tmdb.org/t/p/w500${movie.poster_path}", // URL completa de la imagen
          contentDescription = "Poster de la película ${movie.title}",
          modifier = Modifier.fillMaxWidth()
          )
    
         ``` 
           !movie.poster_path es un identificador de la imagen de la película. Se concatena con la 
            URL base de TMDB ("https://image.tmdb.org/t/p/w500") para formar la URL completa de la
            imagen, que luego se muestra usando AsyncImage¡

    -  Mejoras de las UIs:
            
    J.Compose te permite:

        * Diseño moderno
        * Un diseño optimizado, evitando recomposiciones innecesarias
        * Animaciones fluidas
        * Un manejo eficiente de estados con remember y viewModel.
        * Una navegación limia con NavHost y AnimatedNavHost
    
      -  Implementación de APIs de imagenes para trabajar con elementos propios de J.Compose 
         e Hilos 
    
          * Patantall FinApp:
    
           1. Conexión con la API 
     
           ```val pexelsImages by mainViewModel.pexelsImages.collectAsState()
    
             LaunchedEffect(Unit) {
              mainViewModel.getImagesFromPexels("nature")
               }
          ```
           2. Se muestrans las imagenes con LazyRow
         
            ```val pexelsImages by mainViewModel.pexelsImages.collectAsState()
                LazyRow(
                  modifier = Modifier
                  .fillMaxWidth()
                  .height(200.dp)  
                  .align(Alignment.BottomCenter)
                  .padding(horizontal = 16.dp),
                   horizontalArrangement = Arrangement.spacedBy(8.dp)
                   ) {
                        items(response.photos) { photo ->
                         AsyncImage(
                          model = photo.src.large,  // URL de la imagen obtenida de la API
                           contentDescription = "Imagen de Pexels",
                           modifier = Modifier
                           .width(150.dp)
                           .fillMaxHeight()
                           .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                  )
                 }
                }
          ```
         ! Flujo de la actividad: 
      
            1. El MainViewModel hace la solicitud a la API(Pexels) y obtiene imágenes (de la categoría especificada)
            2. pexelsImagenes cambia automáticamente en respuesta de la API
            3. LazyRow itera sobre las imágenes obtenidas y con la ayuda de AsyncImage() las muestra
            4. Con contentScale = ContentScale.Crop, las imágenes se ajustan para utilizar el espacio adecuado

       * Patantall RegistroScreen: Uso de Hilos sobre la API, mostrando las imágenes en J.Compose

          ! Utilizamos hilos (corutinas) para:
            1. Manejar la llamada a la API sin bloquear la UI

               ```
               LaunchedEffect(Unit) {
               mainViewModel.getRandomImages("nature")  
                 }
                 ```
         
            2. Actualizar dinámicamnete las imágenes en un carrusel infinito
          
                ```
               LaunchedEffect(Unit) {
               while (true) {
               delay(3000) // Esperar 3 segundos
               if (infiniteImages.isNotEmpty()) {
               infiniteImages.add(infiniteImages.first()) // Agregar la primera imagen al final
                infiniteImages.removeAt(0)  // Eliminar la primera imagen
                  }
                }
                 }
                 ```
         ! Flujo de la actividad: 
    
            1. Llamamos a la API 
            2. Mostramos las imágenes en un Carrusel (InfiniteImageCarousel)
            3. Rotación automática de Imágenes (Corutina en InfiniteImageCarousel)
        