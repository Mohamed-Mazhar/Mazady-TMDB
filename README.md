# Mazady-TMDB
# Movie App

A clean architecture Android app for browsing movies with favorites functionality.

## Architecture Decisions

### Clean Architecture Layers
- **Domain**: Pure Kotlin models and business logic
- **Data**: Repository implementations, DTOs, and mappers
- **Presentation**: ViewModels and UI components

### Key Components
- **StateFlow** for observable state management
- **Repository pattern** abstracting data sources
- **Sealed Result classes** for error handling
- **Mappers** for DTO ↔ Domain conversion

## Running the Project

1. Clone the repository
2. Build and run in Android Studio

### Trade-offs
- **Error Handling**: Basic error messages - could be enhanced with retry logic

### Assumptions
- TMDB API will remain stable
- Favorites list won't exceed practical limits for local storage
- Network connectivity is usually available

## Future Improvements
- Add caching layer
- Add more comprehensive error states
- Include UI tests
