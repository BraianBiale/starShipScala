package edu.austral.starship.scala.game.model.engine

import edu.austral.starship.scala.game.model.GameSprites
import edu.austral.starship.scala.game.model.sprites.{Bullet, Starship}

object BulletEngine extends Engine [Bullet]{

  def stepCycle(gameSprites: GameSprites, timeSinceLastDraw: Float, keysDown: Set[Char], maxX: Int, maxY: Int): List[Bullet] = {

    gameSprites.bullets.flatMap(bullet => stepCycle(bullet, maxX, maxY)) ::: newBullets(gameSprites, keysDown)
  }

  private def mainGunLogic(gameSprites: GameSprites, starship: Starship): Boolean = !gameSprites.bullets.exists(bullet => bullet.starship.player.equals(starship.player))

  private def altGunLogic(gameSprites: GameSprites, starship: Starship): Boolean = !gameSprites
    .bullets
    .exists(bullet => bullet.starship.player.equals(starship.player) && (System.currentTimeMillis() - bullet.time) < 300)

  private def newBullets(gameSprites: GameSprites, keysDown: Set[Char]): List[Bullet] = {
    gameSprites.starships.flatMap(starship => {
      if (keysDown.contains(starship.player.keysConfig.shoot) &&
        (starship.mainGun && mainGunLogic(gameSprites, starship) || (!starship.mainGun && altGunLogic(gameSprites, starship)))
      ) {
        Some(newBullet(starship))
      } else None
    })
  }

  private def newBullet(starship: Starship): Bullet = {
    Bullet(starship.position, starship.speed.unitary * 15, starship, System.currentTimeMillis())
  }

  private def stepCycle(bullet: Bullet, maxX: Int, maxY: Int): Option[Bullet] = {
    val inside = bullet.position.x > 0 && bullet.position.x < maxX && bullet.position.y + 30 > 0 && bullet.position.y < maxY
    if (inside) Some(bullet.copy(position = bullet.position + bullet.speed))
    else None
  }

}
