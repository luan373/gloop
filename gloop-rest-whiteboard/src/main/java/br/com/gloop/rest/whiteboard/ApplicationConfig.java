package br.com.gloop.rest.whiteboard;

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
		resource.add(br.com.gloop.rest.security.CORSFilter.class);
		resource.add(br.com.gloop.rest.security.AuthenticationFilter.class);

		resource.add(br.com.gloop.rest.whiteboard.AuthenticationRest.class);
		resource.add(br.com.gloop.rest.whiteboard.AlternativaRest.class);
		resource.add(br.com.gloop.rest.whiteboard.MateriaRest.class);
		resource.add(br.com.gloop.rest.whiteboard.QuestaoRest.class);

		return resource;
	}

}
