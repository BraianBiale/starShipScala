package edu.austral.starship.scala.base.collision
import java.awt.Shape

trait Collisionable {

  def getShape: Shape

  def collidesWithAny(collisionables: List[Collisionable]): Boolean = {
    collisionables.exists(_.collidesWith(this))
  }

  def collidesWith(collisionable: Collisionable): Boolean = {
    this.getShape.getBounds.intersects(collisionable.getShape.getBounds)
  }


}
