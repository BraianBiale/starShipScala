package edu.austral.starship.scala.game.model.state

import edu.austral.starship.scala.game.model.GameModel
import processing.core.PGraphics

trait GameState {

  def draw(gameModel: GameModel, graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Char]): GameState
}
