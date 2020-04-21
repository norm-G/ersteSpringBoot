package ng.uebungen.erste;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ErsteUebungApplication {

	@Value("${server.port}")
    private int serverPort;
 
    @Value("${server.http.port}")
    private int serverHttpPort;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ErsteUebungApplication.class, args);
	}
	
	 @Bean
	    public ServletWebServerFactory servletContainer() {
	        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	            @Override
	            protected void postProcessContext(Context context) {
	                SecurityConstraint securityConstraint = new SecurityConstraint();
	                securityConstraint.setUserConstraint("CONFIDENTIAL");
	                SecurityCollection collection = new SecurityCollection();
	                collection.addPattern("/*");
	                securityConstraint.addCollection(collection);
	                context.addConstraint(securityConstraint);
	            }
	        };
	        tomcat.addAdditionalTomcatConnectors(createHttpRedirectConnector());
	        return tomcat;
	    }
 
    private Connector createHttpRedirectConnector() {
        Connector httpConnector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        httpConnector.setScheme("http");
        httpConnector.setPort(serverHttpPort);
        httpConnector.setRedirectPort(serverPort);
        return httpConnector;
    }
	

}
