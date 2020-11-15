package edu.austral.starship.scala.game.util

import java.util.concurrent.ThreadLocalRandom

object Random {

  def shouldAddAsteroid(rate: Int): Boolean = {
    val random: ThreadLocalRandom = ThreadLocalRandom.current()
    random.nextInt(rate) == 1
  }

  def randomInt(max: Int): Int = {
    val random: ThreadLocalRandom = ThreadLocalRandom.current()
    random.nextInt(max)
  }

}
