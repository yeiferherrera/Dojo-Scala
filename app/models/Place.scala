package models

import play.api.libs.json.{Json,Reads, Writes}

case class Place(id:Int, name:String, description:Option[String])

object Place{
  implicit val placeWrite: Writes[Place] = Json.writes[Place]
  implicit val placeRead: Reads[Place] = Json.reads[Place]

}

