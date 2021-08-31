import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, path}
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext


//import akka
object Server extends App{
  val host = "0.0.0.0"
  val port = 9000
  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  val materializer: ActorMaterializer = ActorMaterializer()

//  def route = path("hello") {  get {    complete("Hello, World!")  }}
//    def route = path("hello") {
//      Directives.get {
//        complete("Hello, World!")
//      }
//    }
  var send = Huffman.createCodeTree("my name is Tamar Birentzweig".toList)
  var encoding = Huffman.quickEncode(send)("Tamar".toList)
  var decoding = Huffman.decode(send,encoding)

  val itemRoutes: Route =
    Directives.concat(
      path("encode") {
        Directives.get {
          complete("Hello, Tamar! encode " + encoding.toString())
        }
      },
      path("decode") {
        Directives.get {
          complete("Hello, Tamar! decode " + decoding.toString())
        }
      }
    )
  Http().bindAndHandle(itemRoutes, host, port)

}
