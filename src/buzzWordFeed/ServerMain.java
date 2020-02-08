package buzzWordFeed;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class ServerMain {

	public static void main(String[] args) throws IOException {
	    HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
	    HttpContext context = server.createContext("/");
	    context.setHandler(ServerMain::SimpleResponse);
	    server.start();
	}
	
	private static void SimpleResponse(HttpExchange exchange) throws IOException {
	    String response = getTestResponse();
	    exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
	    OutputStream os = exchange.getResponseBody();
	    os.write(response.getBytes());
	    os.close();
	}
	
	private static String getTestResponse() {
		
	}
	

}
