package edu.austral.starship.scala.game.view

import edu.austral.starship.scala.game.model.Positionable
import edu.austral.starship.scala.game.model.sprites.{Asteroid, Bullet, Gun, Starship}
import processing.core.PImage

class ImageVisitor() {

  var images: Map[String, PImage] = Map()

  def setImages(map: Map[String, PImage]): Unit = {
    images = map
  }

  def getImage(drawable: Positionable): PImage = {
    drawable.accept(this)
  }

  def visitBullet(bullet: Bullet): PImage = resize(images("BULLET"), bullet.getSize, bullet.getSize)

  def visitAsteroid(asteroid: Asteroid): PImage = {
    if (asteroid.getSize == 50)
      images("SMALL_ASTEROID")
    else
      images("BIG_ASTEROID")

  }

  def visitStarship(starship: Starship): PImage = resize(images("STARSHIP"), starship.getSize, starship.getSize)

  def visitGun(gun: Gun): PImage = {
    if (gun.starship.mainGun) resize(images("GUN1"), gun.getSize, gun.getSize)
    else resize(images("GUN2"), gun.getSize, gun.getSize)
  }

  def resize(image: PImage, x: Int, y: Int): PImage = {
    image.resize(x, y)
    image
  }

}
