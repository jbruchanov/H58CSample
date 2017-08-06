# H58CSample

Use latest [Android Studio](https://developer.android.com/studio/preview/index.html)

Optional
- Search for images by tag
  - type tag and "done" on keyboard
- Image caching
  - automaticly by glide
- Order by date taken or date published
  - [just in code](https://github.com/jbruchanov/H58CSample/blob/dev/app/src/main/kotlin/com/scurab/android/h58csample/main/presenter/GlobalPhotosPresenter.kt#L76)
- Save image to the System Gallery
  - ignored
- Open image in system browser
  - simple click on item
- Share picture by email
  - long click on item, just sharing a link, not full image (would need content provider)
