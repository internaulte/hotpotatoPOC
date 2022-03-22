package controller

import entities.{ErrorTypeOne, ErrorTypeThree, ErrorTypeTwo}
import repositories.Repository
import hotpotato._
import useCases.UseCase

import scala.concurrent.ExecutionContext.Implicits.global

object Main {

  def main(args: Array[String]): Unit = {
    val useCase = new UseCase(new Repository)

    for {
      result <- useCase.function("truc", "machin")
    } yield {
      result.mapErrorAllInto[Unit](
        (_: ErrorTypeOne) => println("prout"),
        (_: ErrorTypeTwo) => println("prout2"),
        (_: ErrorTypeThree) => println("prout3")
      ).map(result => println(result.param1 + result.param2))
    }
  }
}
