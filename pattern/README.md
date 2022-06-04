# Pattern
[![Generic badge](https://img.shields.io/badge/Version-1.0.0-<green>.svg)](https://github.com/longdt57/kotlin-ext/releases/latest)

## Implementation
build.gradle
```
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.longdt57.kotlin-ext:pattern:{version}'
}
```

## Usage

### EventLiveData

```
/**
 * Class representing live data that emits one-shot events
 * Guarantees that once consumed event is not redelivered
 */
 
protected val _navigator: MutableLiveData<navigator> = EventLiveData<NavigationEvent>()
val navigator: LiveData<NavigationEvent> = _navigator
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
 
protected val _navigator: MutableLiveData<NavigationEvent> = SingleLiveData<NavigationEvent>()
val navigator: LiveData<NavigationEvent> = _navigator
```
