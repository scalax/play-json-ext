# Play json extensions

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



More features woking in progress
