package org.apache.karaf.rest.whiteboard;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)
@JaxrsApplicationBase("rest")
@JaxrsName("gloop")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resource = new HashSet<>();
		
		resource.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
		resource.add(org.apache.karaf.rest.security.AuthenticationFilter.class);

		resource.add(org.apache.karaf.rest.whiteboard.BookingServiceRest.class);
		resource.add(org.apache.karaf.rest.whiteboard.AuthenticationRest.class);

		return resource;
	}

}
