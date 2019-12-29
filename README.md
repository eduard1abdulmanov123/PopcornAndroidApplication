# Popcorn

The app fetches movie data using [themoviedb.org](https://www.themoviedb.org/) API.

# Screenshots

Main menu | Movie list | Movie list | Search
:-------------:|:-------------:|:-------------:|:-------------:
<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/1.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/2.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/3.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/4.jpg" width="309" height="380">

Search Empty | Search Query | Filter | Library empty 
:-------------:|:-------------:|:-------------:|:-------------
<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/5.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/6.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/7.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/8.jpg" width="309" height="380">

Search Empty | Search Query | Filter | Library empty 
:-------------:|:-------------:|:-------------:|:-------------
<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/9.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/10.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/11.jpg" width="309" height="380">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/12.jpg" width="309" height="380">

# Features
* Infinite scrolling with RecyclerView
* The work list is implemented as a finite state machine
* Search movies by title
* Mark as favorite any movie for easy access in the future
* Watch movie trailers and read movie reviews.

# Used Libraries:
* RxJava2 and Retrofit2 libraries to manage Rest Client
* Picasso library to load images
* Room library for implementation of database
* Dagger2 library for dependency injection

# Design pattern
* Was used the design pattern MVP(Model View Presenter) to make the code simple, understandable, testable and easily maintainable
* To weaken the relationship code was created, the core module, where you write code making requests to the server, and also implemented to work with the database

