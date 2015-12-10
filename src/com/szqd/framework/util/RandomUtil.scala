package com.szqd.framework.util

import scala.util.Random

/**
 * Created by like on 9/1/15.
 */
object RandomUtil {
  def nextLong(n:Long): Long =
  {

    1 + (Random.nextDouble()*(n - 1)).asInstanceOf[Long];
  }
}
