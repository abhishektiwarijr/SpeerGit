
# SpeerGit App

An Android application consuming the [GitHub API](https://developer.github.com/v3/) to search for users on Github, display their followers, following and repositories. The project is
built with Compose, MVVM pattern as well as other architectural components and libraries.


### Screenshots
<img src="https://github.com/abhishektiwarijr/SpeerGit/assets/25899457/19554164-5316-409c-ae2d-af20dfae80f3" width=150/>
<img src="https://github.com/abhishektiwarijr/SpeerGit/assets/25899457/813f2581-4eb5-4181-84e4-9c119234617f" width=150/> 
<img src="https://github.com/abhishektiwarijr/SpeerGit/assets/25899457/32d3c13c-41a3-4d04-a7bc-182344baa994" width=150/>
<img src="https://github.com/abhishektiwarijr/SpeerGit/assets/25899457/e6bc9d45-0158-416f-891e-979eef490f8c" width=150/>
<img src="https://github.com/abhishektiwarijr/SpeerGit/assets/25899457/d4c7a02c-9ece-4a05-99c5-800d541f92a0" width=150/> 


## Architecture
The app is built using the MVVM clean code architectural pattern.


## Libraries
- [Jetpack](https://developer.android.com/jetpack)🚀
    - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI
      related data in a lifecycle conscious way
    - [Compose](https://developer.android.com/courses/pathways/compose) - A modern declarative way
      to build android ui
    - [Navigation](https://developer.android.com/jetpack/compose/navigation) - Handle everything needed for in-app navigation.
    - [ViewModel](https://developer.android.com/jetpack/compose/state#viewmodel-state) - Easily schedule asynchronous tasks for optimal execution.
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - Persistence library for Android.
- [Retrofit](https://square.github.io/retrofit/) - Type-safe HTTP client and supports coroutines out
  of the box. Used for the network calls.
- [Gson](https://github.com/google/gson) - Used to convert JSON to Java/Kotlin classes for the
  Retrofit
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for
  coroutines
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Used for
  Dependency injection
- [Coil](https://coil-kt.github.io/coil/compose/) - Allows for fetching and displaying of images in
  the composables
