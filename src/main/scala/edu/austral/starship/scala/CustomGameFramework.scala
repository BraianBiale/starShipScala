package edu.austral.starship.scala

import edu.austral.starship.scala.base.framework.{GameFramework, ImageLoader, WindowSettings}
import edu.austral.starship.scala.game.model.helpers.{LivesHelper, ScoreHelper}
import edu.austral.starship.scala.game.model.{AbstractModel, GameModel, Player}
import edu.austral.starship.scala.game.util.{Configuration, KeysConfig}
import edu.austral.starship.scala.game.view.ShapeManager
import processing.core.PGraphics
import processing.event.KeyEvent

object CustomGameFramework extends GameFramework {


  val model: AbstractModel = new GameModel()

  var keysDown: Set[Char] = Set()

  override def setup(windowsSettings: WindowSettings, imageLoader: ImageLoader): Unit = {
    Configuration.setup("/game.props")
    ShapeManager.loadShapes(imageLoader)
    windowsSettings.setSize(
      Configuration.getIntegerProperty("maxX"),
      Configuration.getIntegerProperty("maxY"))
    model.setup(buildPlayers(),
      Configuration.getIntegerProperty("maxX"),
      Configuration.getIntegerProperty("maxY"),
      Configuration.getIntegerProperty("maxLives")
    )
  }

  def buildPlayers(): List[Player] = {
    List.range(1, Configuration.getIntegerProperty("players") + 1)
      .map(playerNumber => Player(
        Configuration.getProperty("playerName" + playerNumber),
        KeysConfig(
          Configuration.getCharProperty("upControl" + playerNumber),
          Configuration.getCharProperty("downControl" + playerNumber),
          Configuration.getCharProperty("leftControl" + playerNumber),
          Configuration.getCharProperty("rightControl" + playerNumber),
          Configuration.getCharProperty("shootControl" + playerNumber),
          Configuration.getCharProperty("changeGun" + playerNumber),
        )
      ))
  }

  override def draw(graphics: PGraphics, timeSinceLastDraw: Float, keySet: Set[Int]): Unit = {
    model.stepCycle(timeSinceLastDraw, keysDown)
    val positionables = model.getPositionable

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

    ScoreHelper
      .getAll
      .zipWithIndex
      .foreach(
        {
          case (player: String, index: Int) => graphics.text(
            player,
            100,
            100 + index * 12)
        })

    LivesHelper
      .getAll
      .zipWithIndex
      .foreach(
        {
          case (player: String, index: Int) => graphics.text(
            player,
            300,
            100 + index * 12)
        })

    if (positionables.isEmpty) {
      graphics.text("GAME OVER",
        Configuration.getIntegerProperty("maxX") / 2,
        Configuration.getIntegerProperty("maxY") / 2)
    }
  }

  override def keyPressed(event: KeyEvent): Unit = {
    keysDown += event.getKey
  }

  override def keyReleased(event: KeyEvent): Unit = {
    keysDown -= event.getKey
  }
}
