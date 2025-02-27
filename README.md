
A Java-based song recommendation system with a JavaFX interface that uses machine learning algorithms to provide personalized music recommendations.

## Overview

SongRecSys is a recommendation engine that implements K-Nearest Neighbors (KNN) and K-Means clustering algorithms to suggest songs similar to those a user already likes. The system processes song data with various audio features and determines similarity between songs using different distance metrics.

## Installation 

### Prerequisites

- Java JDK 8 or higher
- JavaFX (included in JDK 8, separate download for JDK 11+)

### Quick Setup

1. **Clone the repository**

  ```bash
   git clone https://github.com/luciacepria/SongRecSys.git
   ```

2. **Open the project in your IDE** (Eclipse, IntelliJ IDEA, etc.)

3. **Run the application**

	- Navigate to `org.example.Principal` class
	- Run the `main` method
	- The JavaFX interface will launch automatically


## How It Works

1. The JavaFX application launches, presenting the user interface
2. The system loads song data from CSV files
3. Users can select their preferred algorithm (KNN or K-Means) and distance metric (Euclidean or Manhattan)
4. When a user selects a song they like, the system recommends similar songs based on audio features
5. Recommendations are displayed in the user interface


| Screen 1                                                                    | Screen 2                                                                    |
| --------------------------------------------------------------------------- | --------------------------------------------------------------------------- |
| <img src="Captura de pantalla 2025-02-27 a las 20.24.43.png" width="300" /> | <img src="Captura de pantalla 2025-02-27 a las 20.24.43.png" width="300" /> |


## Contributing

Project closed to contributions

## License

None
