# ToWatchList
![Watchlist screen](/screenshots/watchlist-screen.png "Список просмотров")
![Search screen](/screenshots/search-screen.png "Экран поиска")
![Detail screen](/screenshots/detail-screen.png "Детальная информация о фильме")

ToWatchList - демонстрационное приложение для поиска, и локального сохранения информации о фильмах, сериалах и ТВ-шоу. Использует API сервиса The Movie Database (TMDb).

С версии 0.2 приложение было полностью перенесено на Jetpack Compose. При проектировании архитектуры приложения применялась методология Clean Architecture.

Стек используемых технологий:
--------------

+ В качестве языка программирования: Kotlin
+ Для внедрения зависимостей: Hilt
+ Для получения данных из удаленного источника: Retrofit2
+ Для хранения данных на устройстве: Room Persistence Library
+ Для реализации асинхронной работы: Kotlin coroutines и Asynchronous Flow
+ Для работы с изображениями: Coil
+ Для конфигурации gradle-файлов проекта: KTS
+ Также использовалась Paging Library для постраничной загрузки данных
