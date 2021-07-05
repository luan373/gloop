package br.com.gloop.rest.whiteboard;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import br.com.gloop.jpa.model.Materia;
import br.com.gloop.jpa.service.MateriaService;
import br.com.gloop.rest.security.Secured;

@Path("materia")
@JaxrsApplicationSelect("(osgi.jaxrs.name=gloop)")
@Component(service = MateriaRest.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
public class MateriaRest {

	@Reference
	private MateriaService materiaService;

	public MateriaRest() {
	}

	@Secured
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response list() {
		return Response.ok().entity(materiaService.list()).build();
	}

	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response get(@PathParam("id") Long id) {
		Response response = null;

		try {
			response = Response.ok().entity(materiaService.get(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response add(Materia materia) {
		Response response = null;

		try {
			materiaService.add(materia);

			response = Response.ok().build();
		} catch (Exception e) {
			response = Response.serverError().build();
		}

		return response;
	}
	
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public Response edit(Materia materia) {
		Response response = null;

		try {
			materiaService.edit(materia);

			response = Response.ok().build();
		} catch (Exception e) {
			response = Response.serverError().build();
		}

		return response;
	}

	@Path("/{id}")
	@DELETE
	public Response remove(@PathParam("id") Long id) {
		Response response = null;

		try {
			//materiaService.remove(id);

			response = Response.ok().build();
		} catch (Exception e) {
			response = Response.serverError().build();
		}

		return response;
	}

}