# Play json extensions

Utility extendsion library for `play-json`

## Features

> Transform keys

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
snakified.camelizeKeys(

```

More features woking in progress
