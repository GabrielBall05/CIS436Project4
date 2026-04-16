# CIS436Project4
Recipe Finder App
Overview

This project is a multi-screen Android application built using Kotlin. The app allows users to browse recipes, search and filter them by category, and view detailed information including ingredients, instructions, images, and YouTube videos.

The app uses the MealDB API to fetch real recipe data and demonstrates key mobile development concepts such as RecyclerView, fragments, ViewModel, LiveData, and API integration.

Team Contributions

Gabe (Initial Development / Starter Code)
Set up the Android project structure and layout files
Created the base fragments (Recipe List and Recipe Details)
Implemented initial navigation using Safe Args
Defined core data models (Recipe, IngredientMeasurement)
Set up the ViewModel structure and LiveData placeholders
Added initial UI components (RecyclerView, search bar, spinner)
Provided starter logic and TODOs for further implementation

Francisco Hernandez (Feature Implementation & Completion)
Integrated the MealDB API using Volley to fetch recipe data
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
Created and executed test cases for app functionality
Verified correct behavior of navigation, search, and filtering features
Recorded and presented the explanation/demo video of the application

Features
Browse recipes from a live API
Search recipes by name
Filter recipes by category
View detailed recipe information
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
