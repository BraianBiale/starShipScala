package edu.austral.starship.scala.game.model.sprites

import java.awt.Shape
import java.awt.geom.Ellipse2D

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.model.Player
import edu.austral.starship.scala.game.view.ImageVisitor
import processing.core.PImage

case class Starship(position: Vector2, speed: Vector2, player: Player, maxLives: Int,lastGunChanged: Long = 0, mainGun: Boolean = true, lastDied: Long = 0, collisionableRadious: Int = 40) extends Sprite {

  override def getSpeed: Vector2 = speed

  override def getPosition: Vector2 = this.position

  override def accept(visitor: ImageVisitor): PImage = visitor.visitStarship(this)

  def getShape: Shape = new Ellipse2D.Float(position.x , position.y , collisionableRadious * 2, collisionableRadious * 2)

  override def getSize: Int = collisionableRadious * 2
}
