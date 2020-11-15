package edu.austral.starship.scala.game.model.engine

import edu.austral.starship.scala.game.model.GameSprites

trait Engine[T] {

  def stepCycle(gameSprites: GameSprites, timeSinceLastDraw: Float, keysDown: Set[Char], maxX: Int, maxY: Int): List[T]

}
