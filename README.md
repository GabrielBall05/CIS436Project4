# CIS436Project4

Recipe Finder App
Overview

This project is a multi-screen Android application built using Kotlin. The app allows users to browse recipes, search and filter them by category, and view detailed information including ingredients, instructions, images, and YouTube videos.

The app uses the MealDB API to fetch real recipe data and demonstrates key mobile development concepts such as RecyclerView, Fragments, Navigation, ViewModel, LiveData, and API integration.

ViewModel code is in: Project4\app\src\main\java\com\example\project4\viewmodels
UI code (fragments + adapter) is in: Project4\app\src\main\java\com\example\project4\ui
Data class code is in: Project4\app\src\main\java\com\example\project4\data

Team Contributions

Gabe (Initial Development / Starter Code)
Set up the Android project structure and layout files
Created the base fragments (Recipe List and Recipe Details)
Implemented initial navigation using navigation graph and Safe Args
Defined core data models (Recipe, IngredientMeasurement)
Set up the ViewModel structure, LiveData placeholders, and search/filter logic
Added initial UI components (RecyclerView, search bar, spinner)
Provided starter logic and TODOs for further implementation

Francisco Hernandez (Feature Implementation & Completion)
Integrated the MealDB API using Volley to fetch recipe and category data
Implemented full ViewModel logic with LiveData updates
Added search functionality and category filtering
Completed navigation from list screen to details screen
Implemented the recipe details page:
Displayed recipe name, category, ingredients, and instructions
Loaded images using Coil
Added YouTube link functionality (click to open video)
Debugged runtime issues and fixed binding/import errors
Improved UI layout and overall user experience

Gio (Testing & Documentation)
Implemented handling null/failed images by showing clear default image
Created test cases to test app functionality
Executed test plan while providing screenshots and pass/fail
Verified correct behavior of fetching, navigation, filtering, and persistence features
Recorded and presented the explanation/demo video of the application

Features
Browse recipes from a live API
Search recipes by name
Filter recipes by category
View detailed recipe information (ingredients + instructions)
Display recipe images
Open YouTube cooking videos directly from the app

Technologies Used
Kotlin
Android Studio
RecyclerView
ViewModel & LiveData
Volley (API calls)
Coil (image loading)
Navigation Component (Safe Args)
