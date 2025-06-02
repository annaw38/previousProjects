import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.io.OutputStream;

public class HelloWebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        HttpContext context = server.createContext("/");
        context.setHandler(exchange -> {
		MapADT map = new PlaceholderMap<String,String>();
                String query = exchange.getRequestURI().getQuery();
                //System.out.println(query);
                String[] parts = query.split("&",2);
                //System.out.println("first: " +parts[0]);
                //System.out.println("second: " +parts[1]);
		String key = null;
		String value = null;
		//System.out.println(parts[0].substring(0,7));
		//System.out.println(parts[0].substring(0,7).equals("course="));
		if(parts[0].substring(0,7).equals("course=")){
			for(int i = 0;i < parts[0].length();i++){
				if(parts[0].charAt(i) == '='){
					key = parts[0].substring(i+1);
					break;
				}
			}
			for(int j = 0;j < parts[1].length();j++){
				if(parts[1].charAt(j) == '='){
					value = parts[1].substring(j+1);
					break;
				}
			}
		}
		
		else{
			 for(int k = 0;k < parts[0].length();k++){
                                if(parts[0].charAt(k) == '='){
                                        value = parts[0].substring(k+1);
                                        break;
                                }
                        }

			for(int m = 0;m < parts[1].length();m++){
                                if(parts[1].charAt(m) == '='){
                                        key = parts[1].substring(m+1);
                                        break;
                                }
                        }
		}


		//System.out.println("key: " +key);
		//System.out.println("value: " +value);
                map.put(key, value);
                String response = "Hello " + map.get(key) + "! <br/>I hope you are having a great " + java.time.LocalDateTime.now();
		System.out.println("Server Received HTTP Request");
		exchange.getResponseHeaders().add("Content-type","text/html");
		exchange.sendResponseHeaders(200, response.length());
		OutputStream outStream = exchange.getResponseBody();
		outStream.write(response.getBytes());
		outStream.close();
	});

        server.start();
	System.out.println("Hello Web Server Running...");
    }
}
