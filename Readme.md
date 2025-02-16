# Localize 


### Add Supported Languages
inside your `app/build.gradle` file add the language tag, as shown below also ensure the specified `string.xml` is available on your apps `res/values/strigns/` folder. 

```kotlin
android {
    ...

    defaultConfig {
        ...
        resourceConfigurations += setOf("en", "ta", "hi", "ml")
    }
}
```