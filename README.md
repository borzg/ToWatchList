# ToWatchList
![Watchlist screen](/screenshots/watchlist-screen.png "Список просмотров")
![Search screen](/screenshots/search-screen.png "Экран поиска")
![Detail screen](/screenshots/detail-screen.png "Детальная информация о фильме")

Приложение, разработанное мной в рамках демонстрационного проекта. Использует API сервиса The Movie Database (TMDb) для поиска фильмов, сериалов и ТВ-шоу и предоставляет возможность добавления их в список для дальнейшего просмотра.

При проектировании архитектуры приложения был применен шаблон MVVM (с использованием архитектурных компонентов, таких как ViewModel и LiveData), а также методология Clean Architecture

Стек используемых технологий:
--------------

+ В качестве языка программирования: Kotlin
+ Для внедрения зависимостей: Hilt
+ Для получения данный из удаленного источника: Retrofit2
+ Для хранения данный на устройстве: Room Persistence Library
+ Для реализации асинхронной работы: Kotlin coroutines и Asynchronous Flow
+ Для навигации по приложению: Navigation Component
+ Для работы с изображениями: Glide
+ Также использовалась Paging Library для постраничной загрузки данных
