package edu.austral.starship.scala.game.model

import edu.austral.starship.scala.game.model.sprites.{Asteroid, Bullet, Gun, Starship}

case class GameSprites(starships: List[Starship], asteroids: List[Asteroid], bullets: List[Bullet], guns: List[Gun])
