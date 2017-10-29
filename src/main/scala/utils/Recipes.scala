package utils

import scala.annotation.tailrec

object Recipes {

  @tailrec
  def dropWhile[B](l: List[B], f: B => Boolean): List[B] =
    l match {
      case h :: t if f(h) => dropWhile(t, f)
      case _ => l
    }

}
