package edu.austral.starship.scala

import edu.austral.starship.scala.base.framework.{GameFramework, ImageLoader, WindowSettings}
import edu.austral.starship.scala.game.model.state.{GameState, PlayingState}
import edu.austral.starship.scala.game.model.{GameModel, Player}
import edu.austral.starship.scala.game.util.{Configuration, KeysConfig}
import edu.austral.starship.scala.game.view.ShapeManager
import processing.core.PGraphics
import processing.event.KeyEvent

object CustomGameFramework extends GameFramework {


  val gameModel: GameModel = new GameModel()

  var keysDown: Set[Char] = Set()

  var state: GameState = new PlayingState

  override def setup(windowsSettings: WindowSettings, imageLoader: ImageLoader): Unit = {
    Configuration.setup("/game.props")
    ShapeManager.loadShapes(imageLoader)
    windowsSettings.setSize(
      Configuration.getIntegerProperty("maxX"),
      Configuration.getIntegerProperty("maxY"))
    setupGameModel()
  }

  def setupGameModel(): Unit = {
    gameModel.setup(buildPlayers(),
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
    state = state.draw(gameModel, graphics, timeSinceLastDraw, keysDown)
  }

  override def keyPressed(event: KeyEvent): Unit = {
    keysDown += event.getKey
  }

  override def keyReleased(event: KeyEvent): Unit = {
    keysDown -= event.getKey
  }
}
