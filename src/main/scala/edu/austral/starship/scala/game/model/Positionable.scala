package edu.austral.starship.scala.game.model

import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.view.ImageVisitor
import processing.core.PImage

trait Positionable {

  def accept(visitor: ImageVisitor): PImage

  def getPosition: Vector2

  def getSpeed: Vector2

  def getSize: Int

}
