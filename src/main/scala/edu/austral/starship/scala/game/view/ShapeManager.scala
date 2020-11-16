package edu.austral.starship.scala.game.view

import edu.austral.starship.scala.base.framework.ImageLoader
import edu.austral.starship.scala.game.model.Positionable
import processing.core.{PConstants, PImage}

object ShapeManager {

  val imageVisitor: ImageVisitor = new ImageVisitor

  def loadShapes(imageLoader: ImageLoader): Unit = {
    imageVisitor.setImages (
      Map(
      "STARSHIP" -> imageLoader.load("img/starship1.png"),
      "SMALL_ASTEROID" -> resize(imageLoader.load("img/asteroid1.png"), 50, 50),
      "BIG_ASTEROID" -> resize(imageLoader.load("img/asteroid1.png"), 100, 100),
      "BULLET" -> resize(imageLoader.load("img/bullet2.png"), 400, 400),
      "GUN1" -> imageLoader.load("img/gun1.png"),
      "GUN2" -> imageLoader.load("img/gun2.png")
      )
    )
  }

  def getCollider(drawable: Positionable): Collider = {
    Collider(
      imageVisitor.getImage(drawable),
      drawable.getPosition.x,
      drawable.getPosition.y,
      drawable.getSpeed.rotate(PConstants.PI / 2).angle,
      drawable.getSpeed.module
    )
  }

  def resize(image: PImage, x: Int, y: Int): PImage = {
    image.resize(x, y)
    image
  }

}
