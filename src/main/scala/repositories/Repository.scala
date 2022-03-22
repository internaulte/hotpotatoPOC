package repositories

import entities.{ErrorTypeOne, ErrorTypeThree, ErrorTypeTwo, MyRepoCaseClass, MyRepoCaseClass2}
import hotpotato._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class Repository {
  def function(param: String): Future[Either[OneOf2[ErrorTypeOne, ErrorTypeTwo], MyRepoCaseClass]] = {
    Future {
      param match {
        case "truc" => Right(MyRepoCaseClass("toto"))
        case "machin" => Left(ErrorTypeOne().embedInto[OneOf2[ErrorTypeOne, ErrorTypeTwo]])
        case _ => Left(ErrorTypeTwo().embedInto[OneOf2[ErrorTypeOne, ErrorTypeTwo]])
      }
    }
  }

  def function2(param: String): Future[Either[OneOf2[ErrorTypeThree, ErrorTypeTwo], MyRepoCaseClass2]] = {
    implicit val embedder: Embedder[OneOf2[ErrorTypeThree, ErrorTypeTwo]] = Embedder.make
    Future {
      param match {
        case "machin" => Right(MyRepoCaseClass2(5L))
        case "truc" => Left(ErrorTypeThree().embed)
        case _ => Left(ErrorTypeTwo().embed)
      }
    }
  }
}
