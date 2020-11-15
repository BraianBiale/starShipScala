package edu.austral.starship.scala.game.model

abstract class AbstractModel {

  def stepCycle(timeSinceLastDraw: Float, keysDown: Set[Char])

  def setup(players: List[Player], maxX: Int, maxY: Int, maxLives: Int): Unit

  def getPositionable: List[Positionable]

}

