package org.apache.karaf.rest.util;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationBase;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsName;

@Component(service = Application.class)
@JaxrsApplicationBase("rest")
@JaxrsName("gloop")
public class ApplicationConfig extends Application{
	
	 @Override
	    public Set<Class<?>> getClasses() {
	        Set<Class<?>> resource = new HashSet<>();
	        addResource(resource);
	        return resource;
	    }

	    private void addResource(Set<Class<?>> resource) {
	        resource.add( org.apache.karaf.rest.whiteboard.BookingServiceRest.class);
	    }

}
