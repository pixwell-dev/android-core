# Core Android + Sample Kotlin project Pixwell

## App signing + app build variants
### App build variants
On sample app we have example 2 builds variants:
1. debug
2. release (build variant that is automatically signed)

After build of these two variants we have 2 apps with package names:
1. sk.pixwell.android.core (debug)
2. sk.pixwell.android.core.dev (release)

Applications names (`android:label="@string/app_name"`):
1. Android Core (debug)
2. Android Core DEV (release)

These names can be changed in:
1. [src/release/res/values/constants.xml](sample/src/release/res/values/constants.xml)
2. [src/debug/res/values/constants.xml](sample/src/release/res/values/constants.xml)

### App signing
1. On top level [build.gradle]() file we must add:
```def keystorePropertiesFile = rootProject.file("keystore.properties")

def keystoreProperties = new Properties()

keystoreProperties.load(new FileInputStream(keystorePropertiesFile))

project.ext {
    KEYSTORE_STORE_FILE = rootProject.file(keystoreProperties['storeFile'])
    KEYSTORE_STORE_PASSWORD = keystoreProperties['storePassword']
    KEYSTORE_KEY_ALIAS = keystoreProperties['keyAlias']
    KEYSTORE_KEY_PASSWORD = keystoreProperties['keyPassword']
} 
```
2. On root project we must add **certificate file (.jks)** and **keystore.properties**

*Example **certificate file (.jks)** and **keystore.properties*** you can find on Pixwell deal keychain.

## What to do next
* include permission handling or at least thing about it
* include camera and gallery handling 
