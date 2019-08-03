# Popcorn

The app fetches movie data using [themoviedb.org](https://www.themoviedb.org/) API.

# Screenshots

## Movies:
Now Playing | Upcoming | Top Rated
:-------------:|:-------------:|:-------------:
<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/1.jpg" width="309" height="518">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/2.jpg" width="309" height="518">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/3.jpg" width="309" height="518">

##  Details:
1 | 2 | 3
:-------------:|:-------------:|:-------------:
<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/4.jpg" width="309" height="518">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/5.jpg" width="309" height="518">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/6.jpg" width="309" height="518">

## Search and Library:
Search | Library | Library Empty 
:-------------:|:-------------:|:-------------:
<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/7.jpg" width="309" height="518">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/8.jpg" width="309" height="518">|<img src="https://github.com/eduard1abdulmanov123/PopcornAndroidApplication/blob/master/screenshots/9.jpg" width="309" height="518">

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

# In the next version
* Will be added filters to search(by year, genre, rating)
* To be added with the recommendations of the movies
* There will be a better library. You can sort, divide, and perform other manipulations with movies
* So it will be an improved design
