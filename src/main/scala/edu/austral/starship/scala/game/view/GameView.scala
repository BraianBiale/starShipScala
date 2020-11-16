package edu.austral.starship.scala.game.view

import edu.austral.starship.scala.CustomGameFramework.gameModel
import edu.austral.starship.scala.game.model.GameModel
import edu.austral.starship.scala.game.model.helpers.{LivesHelper, ScoreHelper}
import processing.core.PGraphics

class GameView {


  def drawSprites(graphics: PGraphics): Unit = {
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

  def drawLivesInfo(graphics: PGraphics): Unit = {
    LivesHelper
      .getAll
      .zipWithIndex
      .foreach(
        {
          case (player: String, index: Int) => {
            graphics.textSize(15)
            graphics.text(player, 300, 100 + index * 20)
          }
        })
  }

  def drawPlayingScores(graphics: PGraphics): Unit = {
    ScoreHelper
      .getAllScores
      .zipWithIndex
      .foreach(
        {
          case (player: String, index: Int) => {
            graphics.textSize(15)
            graphics.text(player, 100, 100 + index * 20)
          }
        })
  }


   def drawGameOverScores(gameModel: GameModel, graphics: PGraphics): Unit = {
    graphics.text("Game Over", (gameModel.maxX / 2) - 10, gameModel.maxY / 2 - 30)
    ScoreHelper.getAllScores.zipWithIndex.foreach {
      case (score: String, index: Int) => {
        graphics.textSize(20)
        graphics.text(score, (gameModel.maxX / 2) - 10, gameModel.maxY / 2 + index * 25)
      }
    }
  }
}
