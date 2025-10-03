# Procedural 2D World Generator in Java

## Overview

This project is a Java-based procedural 2D world generator created for an Object-Oriented Programming (OOP) course. It builds dynamic, natural-looking landscapes using layered noise functions, places trees and vegetation procedurally, and features an animated day/night cycle. The code demonstrates modular, object-oriented design and procedural content generation, making it both educational and practical.

## Features

- **Procedural Terrain Generation:**  
  Generates unique terrain each run using layered noise algorithms for realistic landscapes.
- **Dynamic Vegetation:**  
  Trees are placed based on terrain height, and leaves are animated to sway and fall over time.
- **Day/Night Cycle:**  
  Simulates time passing with a moving sun and changing light.
- **OOP Principles:**  
  The project emphasizes encapsulation, modularity, and clean separation of concerns.

## Getting Started

### Prerequisites

- Java (JDK 8 or newer)
- [danogl library](https://github.com/yoavain/danogl) (used for game object and rendering framework)

### Running the Project

1. Clone this repository:
    ```bash
    git clone https://github.com/funhyper/Procedural-2D-World-Generator-in-Java.git
    ```
2. Import the project into your Java IDE (such as IntelliJ IDEA or Eclipse).
3. Make sure the danogl library is included in your classpath.
4. Run the `PepseGameManager` main class.

## Project Structure

- `src/pepse/PepseGameManager.java` – Main game manager and entry point
- `src/pepse/world/Terrain.java` – Handles terrain generation and rendering
- `src/pepse/world/trees/TreeBuilder.java` – Procedurally places trees and manages foliage
- `src/pepse/util/NoiseGenerator.java` – Generates smooth noise values for terrain shaping
- `src/pepse/world/daynight/Sun.java` – Manages day/night cycle and sun movement
- `src/pepse/util/ColorSupplier.java` – Provides color variations for natural appearance

## How It Works

- The world is built using mathematical noise to generate rolling hills and valleys.
- Trees and vegetation are added in appropriate locations, with random variation for realism.
- The sun animates across the sky, creating a dynamic day/night cycle.
- All components are implemented using object-oriented programming principles for clarity and flexibility.
