import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Login extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-AR,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0")


	val scn = scenario("Login")
		.exec(http("request_0")
			.get("/")
			.resources(http("request_1")
			.get("/favicon.ico")))
		.pause(6)
		.exec(http("request_2")
			.post("/login")
			.formParam("username", "nuevousuario@uniovi.es")
			.formParam("password", "01234")
			.resources(http("request_3")
			.get("/favicon.ico")))
		.pause(2)
		.exec(http("request_4")
			.get("/logout")
			.resources(http("request_5")
			.get("/favicon.ico")))
		.pause(3)
		.exec(http("request_6")
			.post("/login")
			.formParam("username", "pepe@uniovi.es")
			.formParam("password", "01234")
			.resources(http("request_7")
			.get("/favicon.ico")))
		.pause(3)
		.exec(http("request_8")
			.get("/logout")
			.resources(http("request_9")
			.get("/favicon.ico")))

	setUp(scn.inject(rampUsers(2000) over(500 seconds))).protocols(httpProtocol)
}