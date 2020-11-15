package edu.austral.starship.scala.game.model.sprites

import java.awt.Shape
import java.awt.geom.Ellipse2D

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.view.ImageVisitor
import processing.core.PImage

case class Asteroid(position: Vector2, speed: Vector2, size: Int) extends Sprite {

  override def accept(visitor: ImageVisitor): PImage = visitor.visitAsteroid(this)

  override def getPosition: Vector2 = position

  override def getSpeed: Vector2 = speed

  override def getShape: Shape = new Ellipse2D.Float(position.x , position.y , size * 2, size * 2)

  override def getSize: Int = size * 2
}
