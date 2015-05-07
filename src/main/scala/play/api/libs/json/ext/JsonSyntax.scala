package play.api.libs.json

import scala.util.control.TailCalls._
import scala.reflect._

trait JsonSyntax {

  implicit class JsonOps(underlying: JsValue) {

    def transformKeys(f: String => String): JsValue = {
      transformKeys(underlying, f).result
    }

    def snakifyKeys() = {
      transformKeys(snakify)
    }

    def camelizeKeys() = {
      transformKeys(camelize)
    }

    def safeToString(): String = safeToString(underlying).result

    private def safeToString(json: JsValue): TailRec[String] = json match {
      case JsObject(fields) =>
        val fieldsStr = fields.map {
          case (k, v) => tailcall(safeToString(v)).map(quote(k) + ":" + _)
        }
        aggregate(fieldsStr).map(_.mkString("{", "," , "}"))
      case JsArray(elements) =>
        val elemsStr = elements.map(e => tailcall(safeToString(e)))
        aggregate(elemsStr).map(_.mkString("[", ",","]"))
      case JsString(v) => done(quote(v))
      case JsNull => done("null")
      case js => done(js.toString)
    }

    private def quote(str: String) = s""""$str""""

    private def snakify(str: String) = {
      "(?<!^)([A-Z\\d])".r.replaceAllIn(str, "_$1").toLowerCase()
    }

    private def camelize(str: String) = {
      "_([a-z\\d])".r.replaceAllIn(str,  m => { m.group(1).toUpperCase() })
    }

    private def transformKeys(json: JsValue, f: String => String): TailRec[JsValue] = json match {
      case JsObject(fields) =>
        val transformedFields = fields.map {
          case (k, v) => tailcall(transformKeys(v, f)).map(f(k) -> _)
        }
        aggregate(transformedFields).map(JsObject(_))
      case JsArray(elems) =>
        aggregate(elems.map(e => tailcall(transformKeys(e, f)))).map(JsArray(_))
      case _ => done(json)
    }

    private def aggregate[T: ClassTag](recs: Seq[TailRec[T]]): TailRec[Seq[T]] = {
      val init: Seq[T] = Array.empty[T]
      recs.foldLeft(done(init)) { (rsr, rr ) =>
        for {
          rs <- rsr
          r <- rr
        } yield rs :+ r
      }
    }
  }
}
