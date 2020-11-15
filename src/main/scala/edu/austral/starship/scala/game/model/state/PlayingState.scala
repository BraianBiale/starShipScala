package edu.austral.starship.scala.game.model.state
import edu.austral.starship.scala.game.model.GameModel
import processing.core.PGraphics

class PlayingState extends GameState {

  override def draw(gameModel: GameModel, graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Char]): GameState = {
    gameModel.stepCycle(timeSinceLastDraw, keySet)
    if (gameModel.getGameSprites.starships.nonEmpty) {
      gameModel.gameView.drawSprites(graphics)
      gameModel.gameView.drawPlayingScores(graphics)
      gameModel.gameView.drawLivesInfo(graphics)
      this
    } else {
      gameModel.resetGameSprites
      new GameOverState
    }
  }
}
