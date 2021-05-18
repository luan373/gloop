package org.apache.karaf.rest.whiteboard;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.karaf.jpa.model.Booking;
import org.apache.karaf.jpa.service.BookingService;
import org.apache.karaf.rest.security.Secured;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

@Path("booking")
@JaxrsApplicationSelect("(osgi.jaxrs.name=gloop)")
@Component(service = BookingServiceRest.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
public class BookingServiceRest {

	@Reference
	private BookingService bookingService;
	
	
	public BookingServiceRest() {
	}
	
	@Secured
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response list() {
		return Response.ok().entity(bookingService.list()).build();
	}

	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response get(@PathParam("id") Long id) {
		Response response = null;

		try {
			response = Response.ok().entity(bookingService.get(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public void add(Booking booking) {
		bookingService.add(booking);
	}

	@Path("/{id}")
	@DELETE
	public void remove(@PathParam("id") Long id) {
		bookingService.remove(id);
	}

}