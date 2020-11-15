package edu.austral.starship.scala.game.model

import edu.austral.starship.scala.game.model.engine.{AsteroidEngine, BulletEngine, GunEngine, StarshipEngine}
import edu.austral.starship.scala.base.vector.Vector2
import edu.austral.starship.scala.game.model.helpers.{LivesHelper, ScoreHelper}
import edu.austral.starship.scala.game.model.sprites.Starship

class GameModel {


  var maxX = 0

  var maxY = 0

  var asteroidGenerationRate: Int = (maxY * maxX) / 40000

  var sprites: GameSprites = GameSprites(List(), List(), List(), List())

  var gameOver = false

  var players: List[Player] = List()

  var maxLives: Int = 0


  def stepCycle(timeSinceLastDraw: Float, keysDown: Set[Char]): Unit = {

    sprites = GameSprites(
      StarshipEngine.stepCycle(sprites, timeSinceLastDraw, keysDown, maxX, maxY),
      AsteroidEngine.stepCycle(sprites, timeSinceLastDraw,keysDown, maxX, maxY),
      BulletEngine.stepCycle(sprites, timeSinceLastDraw, keysDown, maxX, maxY),
      GunEngine.stepCycle(sprites, timeSinceLastDraw, keysDown, maxX, maxY)
    )

  }

  def getPositionables: List[Positionable] = {
    if (sprites.starships.isEmpty) {
      setup(players, maxX, maxY, maxLives)
    }
    sprites.starships ::: sprites.asteroids ::: sprites.bullets ::: sprites.guns
  }

  def setup(players: List[Player], maxX: Int, maxY: Int, maxLives: Int): Unit = {
    this.maxLives = maxLives
    this.players = players
    this.maxX = maxX
    this.maxY = maxY

    sprites = sprites.copy(
      starships = players
        .zipWithIndex
        .map {case (player, index) => Starship(Vector2(maxX / 2, maxY / 2 + index * 100), Vector2(0, 0), player, maxLives)}
    )

    ScoreHelper.setup(players)
    LivesHelper.setup(players.map(_.name), maxLives)

  }

  def getGameSprites: GameSprites = sprites

  def resetGameSprites: Unit = sprites = GameSprites(List(), List(), List(), List())
}
