package com.shop.common.configuration

import com.typesafe.config.ConfigFactory

/**
  * Created by mgb on 06/02/2016.
  */
object Configuration {
  val config = ConfigFactory.load()
  val serviceURL = config.getString("service.url")
}
