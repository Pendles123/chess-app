# Java Chess Engine & Web Application

**An independent software development project built with Java 21, Spring Boot, JUnit 5 and React.**

## Project Overview

This project began as a Java chess engine and is being developed into a full-stack web application.

My primary focus has been implementing chess rules and game-state management using object-oriented programming principles. I am extending the original Java application with a Spring Boot REST API and a React frontend, while keeping chess rules and move validation in the Java backend.

The project is an opportunity to apply and develop my skills in Java, software architecture, testing and web development.

**Current status:** Active development. The Java chess engine and validation logic form the foundation of the project. REST endpoints and React components have been implemented, but full frontend–backend integration is still in progress. A complete browser-playable version is not yet available.

## Technologies

| Area | Technologies |
|---|---|
| Backend | Java 21, Spring Boot, Maven |
| Testing | JUnit 5 |
| Frontend | React, JavaScript, Vite, HTML, CSS |
| API | REST, JSON |
| Version Control | Git, GitHub |

## Java Chess Engine

The original chess engine was developed using object-oriented Java, with separate classes responsible for pieces, game state, board representation and rule validation.

Key areas of implementation include:

- **Object-oriented design:** An abstract chess-piece class with specialised implementations for King, Queen, Rook, Bishop, Knight and Pawn.
- **Board representation:** An 8×8 board model for storing and accessing chess pieces.
- **Move validation:** Logic for checking piece movement, board occupancy and move restrictions.
- **Special chess rules:** Implementations addressing castling, en passant, pawn promotion, checks and pinned pieces, with further validation and refinement ongoing.
- **Game-state management:** Player turns, board updates, game identifiers and status tracking.
- **Legal-move generation:** Methods for calculating possible destinations, intended to support move highlighting in the web interface.

These features have required working with inheritance, polymorphism, encapsulation, collections, generics and Java's `Optional` class.

## Testing

JUnit 5 is used to test chess logic and validate expected behaviour across different board configurations.

Testing focuses on:

- Correct movement and validation of chess pieces.
- Restrictions imposed by other pieces.
- King safety and pinned-piece scenarios.
- Special chess rules and edge cases.

An important part of developing this project has been identifying scenarios where seemingly correct logic can produce unexpected results.

Testing and edge-case coverage are continuing as the engine evolves.

## Spring Boot REST API

The backend is being adapted to expose chess operations through REST endpoints.

The current controller defines:

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/games` | Create a new chess game |
| POST | `/api/games/{gameId}/move` | Submit a move for validation and execution |
| POST | `/api/games/{gameId}/resign` | Resign from a game |
| POST | `/api/games/{gameId}/draw` | Update a game's draw status |

The implementation separates responsibilities between:

- **GameController:** Receives HTTP requests.
- **GameService:** Coordinates application operations.
- **GameRepository:** Maintains game instances in memory.
- **Chess engine:** Contains the game model, chess rules and validation logic.
- **DTOs:** Transfer game state and move information between backend and frontend.

The endpoints are implemented in source code, although dependency configuration and end-to-end integration still require work.

## React Frontend

The frontend is being developed using React and Vite.

Its architecture uses reusable components for the chessboard, squares and pieces, with React Context managing shared game and selection state.

The intended interaction flow is:

1. A player starts a game through the React interface.
2. React requests a new game from the Spring Boot API.
3. Java creates the game and returns its initial state.
4. React renders the board and handles user interactions.
5. Subsequent moves are submitted to Java for validation.

**Design principle:** Java remains responsible for chess rules and move legality. React is responsible for presentation, user interaction and communicating with the API.

The frontend is still in development and is not yet a complete playable application.

## Repository Structure

- **`backend/`** — Spring Boot application, Java chess engine, DTOs, repository and tests.
- **`frontend/`** — React application, components, pages, shared state and API functions.
- **`README.md`** — Project documentation.

Selected source files:

- [Validation.java](backend/src/main/java/com/validations/Validation.java) — Chess rules and move validation.
- [Game.java](backend/src/main/java/com/chessgame/Game.java) — Game state and turn management.
- [GameService.java](backend/src/main/java/com/service/GameService.java) — Backend application operations.
- [GameController.java](backend/src/main/java/com/controller/GameController.java) — REST endpoints.
- [ChessProvider.jsx](frontend/src/context/ChessProvider.jsx) — Shared frontend state and API interaction.

## Local Development

**Prerequisites:** Java 21, Node.js, npm and Maven (or the included Maven wrapper).

To start the React development server:

1. Navigate to the `frontend` directory.
2. Run `npm install`.
3. Run `npm run dev`.
4. Open the local address displayed by Vite (currently configured as `http://localhost:3000`).

To run the backend tests, navigate to `backend` and run `./mvnw test` on macOS or Linux.

The Spring Boot backend is currently undergoing dependency-injection and integration fixes, so a successful application startup or passing test suite is not yet guaranteed.

## Development Roadmap

- [x] Establish the Java chessboard and piece model.
- [x] Implement core movement and validation logic.
- [x] Introduce JUnit testing.
- [x] Create Spring Boot controllers and service classes.
- [x] Establish the initial React component structure.
- [ ] Complete Spring Boot dependency configuration.
- [ ] Complete frontend–backend communication.
- [ ] Refine rule validation and expand automated test coverage.
- [ ] Implement legal-move highlighting and complete board interactions.
- [ ] Improve the user interface and overall gameplay experience.
- [ ] Explore future features for a chess-focused website.

## About the Project

I am a self-taught Java developer transitioning into software engineering from a background in commercial sales and management.

I began this project to develop practical programming experience beyond tutorials, particularly in object-oriented design, problem-solving, testing and software architecture.

The codebase reflects an ongoing learning and development process, including areas that are being refactored and improved as my understanding grows.

**Author:** Thomas Pendleton
