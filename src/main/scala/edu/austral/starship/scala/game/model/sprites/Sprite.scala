package edu.austral.starship.scala.game.model.sprites

import java.awt.Shape

import edu.austral.starship.scala.base.collision.Collisionable
import edu.austral.starship.scala.game.model.Positionable
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.view.ImageVisitor
import processing.core.PImage

abstract class Sprite extends Positionable with Collisionable {

  override def accept(visitor: ImageVisitor): PImage

  override def getPosition: Vector2

  override def getSpeed: Vector2

  override def getSize: Int

  override def getShape: Shape
}
