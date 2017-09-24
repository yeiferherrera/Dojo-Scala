package controllers

import javax.inject._

import models.Place
import play.api._
import play.api.mvc._
import play.api.libs.json.{JsError, JsSuccess, Json}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  var places = List(
    Place(1,"Barbosa", Some("cualquier cosa")),
    Place(2,"UdeA", Some("Otra Cosa")),
    Place(3,"Rionegro", None)
  )

  def listPlaces = Action {
    val json = Json.toJson(places)
    Ok(json)
  }

  def addPlace = Action{
    request => val json = request.body.asJson.get

      json.validate[Place] match {
        case success: JsSuccess[Place]=>
          places = places :+ success.get
          Ok(Json.toJson(Map("Response"->"Ingresado..")))

        case error: JsError => BadRequest(Json.toJson(Map("error"->"error")))
      }

  }

  def removePlace(id:Int) = Action{
    places = places.filterNot(_.id == id)
    Ok(Json.toJson(Map("Response" -> "Elinado..")))
  }

  def updatePlace = Action{
    request =>val json = request.body.asJson.get
      json.validate[Place] match {
        case success: JsSuccess[Place]=>
          val newPlace = Place(success.get.id, success.get.name, success.get.description)
          places = places.map(x => if(x.id == success.get.id)newPlace
          else x)
          Ok(Json.toJson(Map("Response" -> "Actualizando..")))
        case  error: JsError => BadRequest(Json.toJson(Map("Error"->"error")))
      }
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }



}
