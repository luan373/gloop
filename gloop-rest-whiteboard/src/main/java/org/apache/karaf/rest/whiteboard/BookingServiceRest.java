package org.apache.karaf.rest.whiteboard;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.karaf.jpa.Booking;
import org.apache.karaf.jpa.BookingService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;

@Path("booking")
@Component(service = BookingServiceRest.class, property = { "osgi.jaxrs.resource=true" })
public class BookingServiceRest {

	@Reference
	private BookingService bookingService;

	private ObjectMapper mapper;

	public BookingServiceRest() {
		if (mapper == null) {
			mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
	}

	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response list(@Context SecurityContext context) {
		Response response = null;
		try {
			response = Response.ok().entity(mapper.writeValueAsString(bookingService.list())).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response get(@PathParam("id") Long id) {
		Response response = null;

		try {
			response = Response.ok().entity(mapper.writeValueAsString(bookingService.get(id))).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
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