package edu.austral.starship.scala.base.framework

import edu.austral.starship.scala.CustomGameFramework
import processing.core.PApplet
import processing.event.KeyEvent


class GameManager extends PApplet {
  private val gameFramework: GameFramework = CustomGameFramework
  private var keySet = Set[Int]()

  override def settings(): Unit = gameFramework.setup(WindowSettings(this), ImageLoader(this))

  override def draw(): Unit = {
    clear()

    val timeSinceLastFrame = (frameRate / 60) * 100
    gameFramework.draw(g, timeSinceLastFrame, keySet)
  }

  override def keyPressed(event: KeyEvent): Unit = {
    keySet += event.getKeyCode

    gameFramework.keyPressed(event)
  }

  override def keyReleased(event: KeyEvent): Unit = {
    keySet -= event.getKeyCode

    gameFramework.keyReleased(event)
  }
}
