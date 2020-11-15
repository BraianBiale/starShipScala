package edu.austral.starship.scala.game.model.engine

import edu.austral.starship.scala.game.model.GameSprites
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.model.sprites.Gun

object GunEngine extends Engine[Gun] {

  implicit def tupleToVector2(tuple: (Int, Int)): Vector2 = {
    Vector2(tuple._1.toFloat, tuple._2.toFloat)
  }


  override def stepCycle(gameSprites: GameSprites, timeSinceLastDraw: Float, keysDown: Set[Char], maxX: Int, maxY: Int): List[Gun] = {
   gameSprites.starships.map(starship => {
     Gun(starship.position + (-25, 35), starship.speed, starship)
   })
  }
}
