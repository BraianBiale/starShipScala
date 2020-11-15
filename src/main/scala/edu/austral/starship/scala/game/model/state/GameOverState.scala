package edu.austral.starship.scala.game.model.state
import edu.austral.starship.scala.CustomGameFramework
import edu.austral.starship.scala.game.model.GameModel
import edu.austral.starship.scala.game.model.helpers.ScoreHelper
import processing.core.PGraphics

class GameOverState extends GameState {

  var runtime: Long = 0
  val timeShown = 4000

  override def draw(gameModel: GameModel, graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Char]): GameState = {
    drawScore(gameModel, graphics)
    if (runtime == 0) {
      runtime = System.currentTimeMillis()
    } else if (runtime + timeShown < System.currentTimeMillis()) {
      CustomGameFramework.setupGameModel()
      return new PlayingState
    }
    this
  }

  private def drawScore(gameModel: GameModel, graphics: PGraphics): Unit = {
    graphics.text("Game Over", (gameModel.maxX / 2) - 10, gameModel.maxY / 2 - 30)
    ScoreHelper.getAllScores.zipWithIndex.foreach {
      case (score: String, index: Int) => {
        graphics.textSize(20)
        graphics.text(score, (gameModel.maxX / 2) - 10, gameModel.maxY / 2 + index * 12)
      }
    }
  }
}
