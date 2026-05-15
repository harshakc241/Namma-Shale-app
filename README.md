# Namma-Shaale Inventory - Digital Asset Auditor



## Tech Stack

- Kotlin
- Jetpack Compose + Material 3
- MVVM + Repository Pattern
- Room Database
- StateFlow + Coroutines
- Compose Navigation
- CameraX
- Android PDF export + share intent

## Open In Android Studio

1. Open Android Studio.
2. Select **Open**.
3. Choose this folder: `C:\Users\harsh\OneDrive\Documents\New project`.
4. Let Gradle sync.
5. Run the `app` configuration on an emulator or Android phone.

## Beginner Build Roadmap

Day 1: Open project, sync Gradle, run the app.
Day 2: Study `data/local/entity` and Room annotations.
Day 3: Study DAO queries and Flow.
Day 4: Study repository functions.
Day 5: Study ViewModel state with StateFlow.
Day 6: Build and test Dashboard.
Day 7: Build and test Add Asset.
Day 8: Build Asset Details and Health Check.
Day 9: Build Issue Log and Repair list.
Day 10: Add CameraX photo capture.
Day 11: Generate report statistics.
Day 12: Export PDF and share.
Day 13: Add validation and empty states.
Day 14: Polish Material 3 UI.
Day 15: Prepare interview explanation and demo script.

## Architecture Summary

The app uses a beginner-friendly clean architecture layout:

- `data`: Room entities, DAO, database, repository implementation.
- `domain`: enums and repository contract.
- `presentation`: Compose screens, ViewModels, navigation, reusable UI.
- `util`: PDF and file helpers.

ViewModels expose immutable `StateFlow` to Compose screens. Screens send user events to ViewModels. ViewModels call the repository. The repository talks to Room and returns Flows.

## Diagrams

### High Level System Architecture

```mermaid
flowchart TD
    User["Teacher / School Staff"] --> App["Android Compose App"]
    App --> VM["ViewModels"]
    VM --> Repo["Inventory Repository"]
    Repo --> Room["Room Database"]
    App --> Camera["CameraX"]
    App --> Pdf["PDF Export + Share"]
```

### Clean Architecture

```mermaid
flowchart LR
    UI["Presentation: Compose Screens"] --> ViewModel["ViewModel + StateFlow"]
    ViewModel --> Domain["Domain: Models + Repository Contract"]
    Domain --> Data["Data: Repository Implementation"]
    Data --> Local["Room DAO + Entities"]
```

### MVVM Flow

```mermaid
sequenceDiagram
    participant Screen as Compose Screen
    participant VM as ViewModel
    participant Repo as Repository
    participant DB as Room DAO
    Screen->>VM: User event
    VM->>Repo: Save / query data
    Repo->>DB: DAO call
    DB-->>Repo: Flow / result
    Repo-->>VM: Domain data
    VM-->>Screen: StateFlow UI state
```

### Use Case Diagram

```mermaid
flowchart LR
    Teacher["Teacher / Staff"] --> Register["Register Asset"]
    Teacher --> Inspect["Monthly Health Check"]
    Teacher --> Issue["Report Issue"]
    Teacher --> Repair["Track Repair"]
    Teacher --> Dashboard["View Dashboard"]
    Teacher --> Report["Generate PDF Report"]
```

### Activity Diagram

```mermaid
flowchart TD
    Start([Open App]) --> Login[Login]
    Login --> Dashboard[Dashboard]
    Dashboard --> Add[Add Asset]
    Dashboard --> Health[Health Check]
    Dashboard --> Repair[Repair Requests]
    Dashboard --> Reports[Reports]
    Add --> Dashboard
    Health --> Dashboard
    Repair --> Dashboard
    Reports --> Share[Share PDF]
    Share --> End([Done])
```

### Sequence Diagram: Add Asset

```mermaid
sequenceDiagram
    actor Teacher
    participant Screen as AddAssetScreen
    participant VM as AssetViewModel
    participant Repo as InventoryRepository
    participant DAO as AssetDao
    Teacher->>Screen: Enters asset details
    Teacher->>Screen: Captures/selects photo
    Screen->>VM: saveAsset(form)
    VM->>Repo: addAsset(asset)
    Repo->>DAO: insertAsset(entity)
    DAO-->>Repo: generated id
    Repo-->>VM: success
    VM-->>Screen: navigate back
```

### Class Diagram

```mermaid
classDiagram
    class AssetEntity {
        Long id
        String name
        String category
        String serialNumber
        AssetCondition condition
        String imageUri
        Long purchaseDate
        Int quantity
        String location
    }
    class HealthCheckEntity
    class IssueEntity
    class RepairRequestEntity
    class InventoryDao
    class InventoryDatabase
    class InventoryRepository
    class OfflineInventoryRepository
    class DashboardViewModel
    class AssetViewModel
    InventoryRepository <|.. OfflineInventoryRepository
    OfflineInventoryRepository --> InventoryDao
    InventoryDatabase --> InventoryDao
    AssetEntity --> HealthCheckEntity
    AssetEntity --> IssueEntity
    AssetEntity --> RepairRequestEntity
```
