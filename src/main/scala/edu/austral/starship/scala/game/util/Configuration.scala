package edu.austral.starship.scala.game.util

import java.net.URL
import java.util.Properties

import scala.io.Source


object Configuration {


  private val properties: Properties = new Properties()

  def setup(path: String): Unit ={
    val url: URL = getClass.getResource(path)
    if (url != null) {
      val source = Source.fromURL(url)
      properties.load(source.bufferedReader())
    }else {
      throw new RuntimeException("Could not read properties file")
    }
  }

  def getIntegerProperty(key: String): Int = properties.getProperty(key).toInt
  def getCharProperty(key: String): Char = properties.getProperty(key).toCharArray.head
  def getProperty(key: String): String = properties.getProperty(key)


}

