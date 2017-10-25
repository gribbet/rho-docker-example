package example

import org.http4s.rho.RhoService
import org.http4s.rho.swagger.SwaggerSupport
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.StreamApp

object Main extends StreamApp {

  def stream(args: List[String]) =
    BlazeBuilder
      .mountService(
        HelloWorldService.toService(
          SwaggerSupport()))
      .bindHttp(8080, "0.0.0.0")
      .serve
}

object HelloWorldService extends RhoService {

  import org.http4s.rho.swagger._

  "Simple hello world route" **
    GET / "hello" |>> Ok("Hello world!")
}