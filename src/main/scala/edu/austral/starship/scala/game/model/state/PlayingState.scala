package edu.austral.starship.scala.game.model.state
import edu.austral.starship.scala.CustomGameFramework.gameModel
import edu.austral.starship.scala.game.model.GameModel
import edu.austral.starship.scala.game.model.helpers.{LivesHelper, ScoreHelper}
import edu.austral.starship.scala.game.view.ShapeManager
import processing.core.PGraphics

class PlayingState extends GameState {

  override def draw(gameModel: GameModel, graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Char]): GameState = {
    gameModel.stepCycle(timeSinceLastDraw, keySet)
    if (gameModel.getGameSprites.starships.nonEmpty) {
      drawSprites(graphics)
      drawScores(graphics)
      drawLivesInfo(graphics)
      this
    } else {
      gameModel.resetGameSprites
      new GameOverState
    }
  }

  private def drawSprites(graphics: PGraphics): Unit = {
    val positionables = gameModel.getPositionables

    positionables
      .map(positionable => ShapeManager.getCollider(positionable))
      .foreach(collider => {
        graphics.imageMode(3)
        graphics.pushMatrix()
        graphics.translate(collider.x, collider.y)
        graphics.rotate(collider.direction)
        graphics.image(collider.image, 0, 0)
        graphics.popMatrix()
      })
  }

  private def drawLivesInfo(graphics: PGraphics): Unit = {
    LivesHelper
      .getAll
      .zipWithIndex
      .foreach(
        {
          case (player: String, index: Int) => {
            graphics.textSize(15)
            graphics.text(player, 300, 100 + index * 12)
          }
        })
  }

  private def drawScores(graphics: PGraphics): Unit = {
    ScoreHelper
      .getAllScores
      .zipWithIndex
      .foreach(
        {
          case (player: String, index: Int) => {
            graphics.textSize(15)
            graphics.text(player, 100, 100 + index * 12)
          }
        })
  }
}
