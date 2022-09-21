# Android

## Getting Started
- Language code: Kotlin
- Min SDK: 23
- Architecture: Google Android Architecture with Compose (MVVM + Domain) (https://developer.android.com/topic/architecture?hl=en)

## Useful Link
- OnBoarding slide
- Figma
- Issue board
- Firebase
- Slack
- Lokalise/Crowdin:
- CI/CD

## Generate APK
- Output Channel
- Build time

### Gitlab
Link
- Create a new tag to make a build
    - Create a staging tag: `**staging**`
    - Create a relese tag: `**prod**`

### Bitrise
Link
- Start/Schedule a build
    - branch: develop
    - workflow:
        - deploy-staging: Create a staging build
        - deploy-prod: Create a release build

## Application Architecture
Refer: https://developer.android.com/jetpack/compose/state
![](/screenshots/compose_architecture.png)
App Architecture:
  - UI Layer Module (Xml Layout, Compose, State Holder, ViewModel...): `App`
  - Domain Layer Module: `Domain`
  - Data Layer Module: `Data`
  - Other Lib Modules: `Core, Analytics, Design`

**Base/Important classes**
- BaseComposeActivity, BaseComposeFragment
- BaseViewModel
- SharedPreferences
- EncryptedSharedPreferences

### UI Layer
Includes
- View: `Activity/Fragment, View, Composable, layout xml...`
- StateHolder: `ViewModel, remember, ...`

```
@Composable
fun ABriefFormatOfCompose() {
    // Init Variable

    // Render UI

    // Init and release block
    DisposableEffect(key1 = Unit) {
        // Init connection, call api, add event...
        
        onDispose {
           // Stop, Destroy, release resources.
        }
    }

    // SideEffect block
    SideEffect {}

    // LaunchedBlock
    LaunchedEffect(key1 = ...) {}

    // Old Android LifeCycle block
    OnLifecycleEvent { _, event -> }
}
```

```
@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    val event = MutableSharedFlow<SignInResponse>()

    fun requestOTP(email: String, firstName: String) {
        launch {}
    }
    ...
```

### Data Layer
![](/screenshots/data_layer.png)
Includes
- Local
    - Database: Room
    - SharedPreferences
- Remote
    - Retrofit

```
@Singleton
class AuthRepository @Inject constructor()
```

### Other Libs

#### Analytics
Includes App Analytic
```
@Inject lateinit var analytics: Analytics
...
analytics.logEvent(AnalyticsEvent("Hello World", mapOf("Long" to "Handsome")), ClientType.FIREBASE)
```

#### Design
Includes Material Composable, TextStyle, Button, Theme...

#### Core
Includes generic class, function extention...

## Kotlin
### Idioms
- NullSafety: `value?.executeFunction()` (Don't use `value!!executeFunction()`)

### Extension function
- String?.orEmpty(): `val text: String? = null, text.orEmpty() => ""`
- Commit a new value asynchronously `sharedPreferences.edit { putBoolean("key", value) }`
- `CharSequence.isDigitsOnly()`

### Kotlin Coroutine
Refer: https://developer.android.com/kotlin/flow
![](/screenshots/kotlin_flow.png)
- Flow: StateFlow/MutableStateFlow, SharedFlow/MutableSharedFlow

Activity
```
...Activity {
    ...
    lifecycleScope.launch { 
        // Execute code 
    }
}
```

ViewModel
```
...ViewModel {
    ...
    viewModelScope.lauch {
        // Execute code
    }
}
```

Execute code in IO thread
```
..scope.launch(Dispatchers.IO) {

}
```

## GitFlow Workflow
- Refer: https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow
<img src="/screenshots/gitflow_workflow.png"  width="707" height="451"/>

    - Default branch: `develop`
    - Feature branch: `feature/[issue-id]_title` (Example: feature/360_notification_and_net_protocor_score)
    - Bug fix branch: `bugfix/[issue-id]_title` (Example: bugfix/112_crash_when_open_video)
    - Release branch: `release/[version]` (Example: release/1.0.0)
    - Hotfix branch:  `hotfix/[issue-id]_title` (Example: hotfix/252_fend_gin)

## Gradle Configuration
Refer: https://developer.android.com/studio/build/build-variants
- Variants (Environments):
    - debug
    - staging(".staging")
    - release
- Shrink Release:
    - minifyEnabled - true
    - shrinkResources - true
- Project-wide properties
    ```
        root/build.gradle
        ext {
            libraryVersion = "1.1.0"
        }
    ```

## Debug tool
- Android Studio Profile
- Debug/Staging environment
- Stetho: http://facebook.github.io/stetho/
- Hyperion: https://github.com/willowtreeapps/Hyperion-Android

## Security
- Certificate pinning
- EncryptedSharedPreferences

## CI/CD
- CI: Gitlab CI
    - Build debug
    - Check ktlintFormat
    - Check Detekt
    - Run Unit Test
- CD:
    - Prod Signing
    - Bitrise (Todo)
        - Release to Slack
        - Release to PlayStore Testing

## Testing
- Unit Test
- UI Test
- Manual Test

# Images
Format: `ic_[name]_[color]_[size]` (Example: ic_add_white_16)
How to add image/icon:
- Image from Figma: Select Export tab in Figma to export image
    - SVG: for simple image. Size <= 10kb.
    - PNG: For complex image. Size >= 20 kb
        - 0.75x => drawable-ldpi - optional
        - 1x => drawable-mdpi - should have
        - 1.5x => drawable-hdpi - optional
        - 2x => drawable-xhdpi - should have
        - 3x => drawable-xxdpi - should have
    - If size is from 10 -> 20kb, either SVG/PNG is good, depends on how the image looks like.
- Image from any source:
    1. Open https://appicon.co/#image-sets
    2. Select/drag your image from computer
    3. Check Android box => Generate
    4. Add generated images to project
