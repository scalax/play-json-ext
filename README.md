# Play json extensions

[![Codacy Badge](https://www.codacy.com/project/badge/78e7bfbede6b4032804a8c741b0bcd5c)](https://www.codacy.com/app/jilen-zhang/play-json-ext)
[![Build Status](https://travis-ci.org/scalax/play-json-ext.svg)](https://travis-ci.org/scalax/play-json-ext)

Utility extendsion library for `play-json`

## Features

> Transform keys painlessly

```scala
import play.api.libs.json.ext._
...

val madoka = JsObject(
  Seq(
    "firstName" -> JsString("Madoka"),
    "lastName" -> JsString("Kaname")
  )
)

val snakified = madoka.snakifyKeys()
snakified.camelizeKeys()

```

Just transform keys as you like and you won't got any `StackOverFlow`

> Serialize json painlessly

```scala
    val complex = largeJson(20000)
    complex.safeToString() // no stackoverflow!
    Json.stringify(complex) // stackoverflow!
```


More features woking in progress
