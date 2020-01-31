[ ![Download](https://api.bintray.com/packages/amitsahni/Library/google/images/download.svg) ](https://bintray.com/amitsahni/Library/google/_latestVersion)

**How to Use Library**

Go to -> [Firebase Console](https://console.firebase.google.com/)
Enable `Google` from `Authentication` `SignIn` and copy webclientId

```kotlin
clientId = "put client id here"
```

To check for current user

```kotlin
googleProfile
```

`Login Sample`

```kotlin
googleLogin(1000)
```

`Profile Sample`

```kotlin
googleProfile
```

`OnAcitivyResult`

```kotlin
GoogleManager.onActivityResult(data!!) {
                    if (this)
                        googleProfile
                        googleToken {
                            Log.i("Token = ", it.toString())
                        }
                }
```

Download
--------

[ ![Download](https://api.bintray.com/packages/amitsahni/Library/google/images/download.svg) ](https://bintray.com/amitsahni/Library/google/_latestVersion)


```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/amitsahni/Library" 
    }
}
```

```groovy
implementation 'com.amitsahni:google:0.0.1-alpha06'
```