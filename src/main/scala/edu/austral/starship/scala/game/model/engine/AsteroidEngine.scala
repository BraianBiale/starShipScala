package edu.austral.starship.scala.game.model.engine

import edu.austral.starship.scala.game.model.GameSprites
import edu.austral.starship.scala.game.util.Random
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.model.helpers.ScoreHelper
import edu.austral.starship.scala.game.model.sprites.{Asteroid, Bullet}

import scala.collection.immutable.List


object AsteroidEngine extends Engine[Asteroid] {

  var created: Int  = 0
  var size: Int  = 50

  def stepCycle(gameSprites: GameSprites, timeSinceLastDraw: Float, keysDown: Set[Char], maxX: Int, maxY: Int): List[Asteroid] = {

    gameSprites.asteroids.flatMap(stepCycle(_, gameSprites.bullets, maxX, maxY)) ::: newAsteroid(maxX, maxY)

  }

  private def newAsteroid(maxX: Int, maxY: Int):List[Asteroid] = {
    if (Random.shouldAddAsteroid(maxX * maxY / 30000)) List(AsteroidEngine.buildAsteroid(maxX, maxY))
    else List()
  }

  private def stepCycle(asteroid: Asteroid, bullets: List[Bullet], maxX: Int, maxY: Int): Option[Asteroid] = {
    var dead = false
    bullets.foreach(bullet => {
      if (bullet.collidesWith(asteroid)) {
        dead = true
        ScoreHelper.onBulletCollisionWithAsteroid(bullet)
      }
    }
    )
    if (dead) None
    else {
      val newAsteroid = asteroid.copy(
        position = asteroid.position + asteroid.speed,
      )
      if (newAsteroid.position.x > 0 && newAsteroid.position.x < maxX && newAsteroid.position.y + 30 > 0 && newAsteroid.position.y < maxY) {
        None
      }
      Some(newAsteroid)
    }
  }

  private def buildAsteroid(maxX: Int, maxY: Int): Asteroid = {
  size =  if(Random.randomInt(2) == 1) 50 else 25
    Random.randomInt(3) match {
      case 0 =>
        Asteroid(
          Vector2(Random.randomInt(maxX), 0 + 1),
          Vector2.fromModule(3, Math.toRadians(Random.randomInt(90) + 45).toFloat),
          size
        )
      case 1 =>
        Asteroid(
          Vector2(maxX - 1, Random.randomInt(maxY)),
          Vector2.fromModule(3, Math.toRadians(Random.randomInt(90) + 135).toFloat),
          size
        )
      case 2 =>
        Asteroid(
          Vector2(0 + 1, Random.randomInt(maxY)),
          Vector2.fromModule(3, Math.toRadians(Random.randomInt(90) - 45).toFloat),
          size
        )
    }

  }

}
