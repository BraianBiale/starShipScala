package edu.austral.starship.scala.game.model.engine

import edu.austral.starship.scala.game.model.GameSprites
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.model.helpers.{LivesHelper, ScoreHelper}
import edu.austral.starship.scala.game.model.sprites.Starship

object StarshipEngine extends Engine[Starship] {

  val speedDelta: Float = 0.5F

  def stepCycle(gameSprites: GameSprites, timeSinceLastDraw: Float, keysDown: Set[Char], maxX: Int, maxY: Int): List[Starship] = {
    gameSprites.starships
      .flatMap(starship => {
        val newStarship = stepCycle(starship, keysDown, maxX, maxY)
        addPoints(gameSprites, newStarship)
        checkDead(gameSprites, newStarship)
      })
  }

  private def addPoints(gameSprites: GameSprites, starship: Starship): Unit = {
    gameSprites
      .bullets
      .filter(_.starship.player != starship.player)
      .foreach(bullet => {
        if (starship.collidesWith(bullet)) {
          ScoreHelper.onBulletCollisionWithStarship(bullet)
        }
      })
  }

  private def checkDead(gameSprites: GameSprites, starship: Starship): Option[Starship] = {
    val othersBullets = gameSprites.bullets.filter(_.starship.player != starship.player)
    val diable = starship.lastDied + 1000 < System.currentTimeMillis()

    if (diable && starship.collidesWithAny(gameSprites.asteroids ::: othersBullets)) {
      LivesHelper.onLifeLost(starship.player.name)

      if (starship.maxLives == 1) {
        ScoreHelper.resetScoreOfPlayer(starship.player.name)
        None
      } else {
        Some(starship.copy(
          maxLives = starship.maxLives - 1,
          lastDied = System.currentTimeMillis()
        ))
      }
    }
    else Some(starship)
  }

  private def stepCycle(starship: Starship, keysDown: Set[Char], maxX: Int, maxY: Int): Starship = {
    val newStarship = checkBorders(processKeys(starship, keysDown), maxX, maxY)
    newStarship.copy(position = newStarship.position + newStarship.speed)
  }

  private def checkBorders(starship: Starship, maxX: Int, maxY: Int): Starship = {
    var xSpeed = starship.speed.x
    var ySpeed = starship.speed.y

    if (starship.position.x - 50 < 0) {
      xSpeed = 5
    }
    if (starship.position.x > maxX - 50) {
      xSpeed = -5
    }
    if (starship.position.y - 50 < 0) {
      ySpeed = 5
    }
    if (starship.position.y > maxY - 50) {
      ySpeed = -5
    }
    starship.copy(speed = Vector2(xSpeed, ySpeed))
  }

  private def processKeys(starship: Starship, keysDown: Set[Char]): Starship = {

    var deltaX: Float = 0
    var deltaY: Float = 0

    keysDown.foreach {
      case starship.player.keysConfig.up => deltaY = deltaY - speedDelta
      case starship.player.keysConfig.left => deltaX = deltaX - speedDelta
      case starship.player.keysConfig.down => deltaY = deltaY + speedDelta
      case starship.player.keysConfig.right => deltaX = deltaX + speedDelta
      case _ =>
    }
    starship.copy(
      lastGunChanged = if (keysDown.contains(starship.player.keysConfig.changeGun)) System.currentTimeMillis() else starship.lastGunChanged,
      speed = Vector2(starship.speed.x + deltaX, starship.speed.y + deltaY),
      mainGun =
        if (keysDown.contains(starship.player.keysConfig.changeGun) && (System.currentTimeMillis() - starship.lastGunChanged) > 100) {
          !starship.mainGun
        }
        else starship.mainGun
    )
  }


}
