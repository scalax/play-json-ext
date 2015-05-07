package play.api.libs.json

import org.scalatest._
import play.api.libs.json.ext._

class JsonSyntaxSpec extends FlatSpec with Matchers {

  object camel {
    val madoka = JsObject(
      Seq(
        "firstName" -> JsString("Madoka"),
        "lastName" -> JsString("Kaname")
      )
    )

    val homura = JsObject(
      Seq(
        "firstName" -> JsString("Homura"),
        "lastName" -> JsString("Akemi"),
        "girlFriends" -> madoka
      ))

    val magicas = JsArray(madoka :: homura :: Nil)
  }

  object snake {
    val madoka = JsObject(
      Seq(
        "first_name" -> JsString("Madoka"),
        "last_name" -> JsString("Kaname")
      )
    )

    val homura = JsObject(
      Seq(
        "first_name" -> JsString("Homura"),
        "last_name" -> JsString("Akemi"),
        "girl_friends" -> madoka
      ))
    val magicas = JsArray(madoka :: homura :: Nil)
  }

  "Json syntax" should "snakify keys of json object" in {
    camel.homura.snakifyKeys() should equal(snake.homura)
  }

  it should "snakify keys of json array" in {
    camel.magicas.snakifyKeys() should equal(snake.magicas)
  }

  it should "camelize keys of json object" in {
    snake.homura.camelizeKeys() should equal(camel.homura)
  }

  it should "camelize keys of json array" in {
    snake.magicas.camelizeKeys() should equal(camel.magicas)
  }
}
