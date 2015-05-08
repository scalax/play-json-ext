package play.api.libs.json

import org.scalatest._
import play.api.libs.json.ext._

class JsonSyntaxSpec extends FlatSpec with Matchers {

  object camel {
    val madoka = Json.obj(
      "firstName" -> "Madoka",
      "lastName" -> "Kaname")
    val homura = Json.obj(
      "firstName" -> "Homura",
      "lastName" -> "Akemi",
      "girlFriends" -> madoka)

    val magicas = JsArray(madoka :: homura :: Nil)
  }

  object snake {
    val madoka = Json.obj(
        "first_name" -> "Madoka",
        "last_name" -> "Kaname")
    val homura = Json.obj(
        "first_name" ->"Homura",
        "last_name" -> "Akemi",
        "girl_friends" -> madoka)
    val magicas = JsArray(madoka :: homura :: Nil)
  }

  def largeJson(depth: Int): JsObject = {
    (1 to depth).map(i => Json.obj("id" -> i)).reduce { (sum, json) =>
      val JsObject(f) = json
      JsObject(f :+ ("nest" -> sum))
    }
  }

  val complexJson = largeJson(20000)

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

  it should "camelize and snakify large json" in {
    noException should be thrownBy complexJson.snakifyKeys()
    noException should be thrownBy complexJson.camelizeKeys()
  }

  it should "stringify json object" in {
    camel.homura.safeToString() should equal(Json.stringify(camel.homura))
  }

  it should "stringify json array" in {
    camel.magicas.safeToString() should equal(Json.stringify(camel.magicas))
  }

  it should "stringify large json" in {
    val complex = largeJson(20000)
    noException should be thrownBy complex.safeToString()
    an[StackOverflowError] should be thrownBy Json.stringify(complex)
  }
}
