# Pattern
[![Version](https://jitpack.io/v/longdt57/kotlin-ext.svg)](https://github.com/longdt57/kotlin-ext/releases)

Follow the MVVM + Clean Architecture + Coroutines
Refers:
- https://github.com/nimblehq/android-templates/tree/develop/CoroutineTemplate

## Implementation
build.gradle
```
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.longdt57:kotlin-ext:{version}'
}
```

## Usage

### EventLiveData

```
/**
 * Class representing live data that emits one-shot events
 * Guarantees that once consumed event is not redelivered
 */
 
protected val _uistate: MutableLiveData<Uistate> = EventLiveData<Uistate>()
val uistate: LiveData<UiState> = _uistate
```

### SingleLiveData

```
A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 
protected val _uiState: MutableLiveData<UiState> = SingleLiveData<UiState>()
val uiState: LiveData<UiState> = _uiState
```

### EncryptedSharedPreferences

```
val encryptedSharedPreferences: EncryptedSharedPreferences(...)
var tokenType: String by encryptedSharedPreferences.args(defaultValue = "Bearer")
var accessToken: String? by encryptedSharedPreferences.argsNullable()

```

### AccessTokenInterceptor

```
val interceptor = object : AccessTokenInterceptor() {
    override fun getAccessToken(): String {
        TODO("provide accessToken") 
    }
}

OkHttpClient.Builder().addInterceptor(interceptor)
```

### RefreshTokenAuthenticator

```
val authenticator = object : RefreshTokenAuthenticator() {

    override fun refreshToken(): String? {
        TODO("execute refreshToken, return accessToken to re-call api automatically")
    }

    override fun onRefreshTokenFail(ex: Exception) {
        TODO("Log out, show toast, ...")
    }
}

OkHttpClient.Builder().authenticator(authenticator)
```

### UseCase
```
class UserUseCase: BaseUseCase<Unit, User> {
    override fun execute...
}
```

### ViewModel
```
- ViewModel.launch // Same to viewModelScope.launch
- ViewModel.launchInMain // execute code in Dispatchers.Main

class HomeViewModel(app: Application, private val userUseCase: UserUseCase): BaseViewModel(app) {
    
    fun getUser {
        launch {
          showLoading()
          
          userUseCase.invoke(Unit)
              .onSuccess { user -> navigateTo(UiState) }
              .onError { exception -> // Todo handle error}
              
          hideLoading()
        }
    }

}
```

### Fragment
```
class HomeFragment: BaseFragment<FragmentHomeBinding> {

    override val viewModel: HomeViewModel by viewModels()
    
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
    
    override val uiStateController: (UiState) -> Unit
        get() = { event -> }
      
      
    override fun initViewModel()    // execute in onCreate()
    override fun setupView()        // execute in onViewCreated()
    override fun setupViewEvents()  // execute in onViewCreated()
    override fun observeViewModel() // execute in onViewCreated()
    
    
    override fun showLoading()
    override fun hideLoading()
    
}
```
