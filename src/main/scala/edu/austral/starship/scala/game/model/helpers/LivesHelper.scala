package edu.austral.starship.scala.game.model.helpers

object LivesHelper {

  var playersLivesCounter: Map[String, Int] = Map()

  def setup(players: List[String], maxLives: Int): Unit = {
    players.foreach(player => playersLivesCounter = playersLivesCounter.updated(player, maxLives))
  }

  def onLifeLost(playerName: String): Unit = {
    playersLivesCounter = playersLivesCounter.updatedWith(playerName)(old => old.map(value => value - 1))
  }

  def getAll: List[String] = {
    playersLivesCounter.toList.map { case (name: String, value: Int) => name + " lives: " + value }
  }

}
