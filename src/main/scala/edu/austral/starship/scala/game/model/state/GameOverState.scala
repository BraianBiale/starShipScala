package edu.austral.starship.scala.game.model.state
import edu.austral.starship.scala.CustomGameFramework
import edu.austral.starship.scala.game.model.GameModel
import processing.core.PGraphics

class GameOverState extends GameState {

  var runtime: Long = 0
  val waitTime: Int = 4000

  override def draw(gameModel: GameModel, graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Char]): GameState = {
    gameModel.gameView.drawGameOverScores(gameModel, graphics)
    if (runtime == 0) {
      runtime = System.currentTimeMillis()
    } else if (runtime + waitTime < System.currentTimeMillis()) {
      CustomGameFramework.setupGameModel()
      return new PlayingState
    }
    this
  }

}
