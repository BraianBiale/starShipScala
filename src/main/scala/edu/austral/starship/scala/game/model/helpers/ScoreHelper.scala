package edu.austral.starship.scala.game.model.helpers

import edu.austral.starship.scala.game.model.Player
import edu.austral.starship.scala.game.model.sprites.Bullet

object ScoreHelper {


  var scores: Map[String, Int] = Map()

  def setup(players: List[Player]): Unit = {
    players.foreach(player => scores = scores.updated(player.name, 0))
  }

  def onBulletCollisionWithAsteroid(bullet: Bullet): Unit = {
    scores = scores.updatedWith(bullet.starship.player.name)(optionalValue =>
      optionalValue
        .map(value => value + 1) orElse Some(1)
    )
  }

  def onBulletCollisionWithStarship(bullet: Bullet): Unit = {
    scores = scores.updatedWith(bullet.starship.player.name)(optionalValue =>
      optionalValue
        .map(value => value + 100) orElse Some(100)
    )
  }

  def getScoreByPlayerName(name: String): Option[Int] = {
    scores.get(name)
  }

  def resetScoreOfPlayer(name: String): Unit = {
    scores = scores.updated(name, 0)
  }

  def getAll: List[String] = {
    scores.toList.map { case (name: String, value: Int) => name + ": " + value }
  }
}
