package useCases

import entities.{ErrorTypeOne, ErrorTypeThree, ErrorTypeTwo, MyCaseClass}
import hotpotato._
import repositories.Repository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UseCase(repo: Repository) {

  def function(param: String, param2: String): Future[Either[OneOf3[ErrorTypeOne, ErrorTypeTwo, ErrorTypeThree], MyCaseClass]] = {
    implicit val embedder: Embedder[OneOf3[ErrorTypeOne, ErrorTypeTwo, ErrorTypeThree]] = Embedder.make
    for {
      repoCaseClass <- repo.function(param)
      repoCaseClass2 <- repo.function2(param2)
    } yield {
      for {
        repoCaseClassResult <- repoCaseClass.embedError
        repoCaseClass2Result <- repoCaseClass2.embedError
      } yield {
        MyCaseClass(repoCaseClassResult.param, repoCaseClass2Result.param)
      }
    }
  }
}
