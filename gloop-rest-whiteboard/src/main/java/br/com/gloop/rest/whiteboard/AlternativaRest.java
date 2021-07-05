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

import br.com.gloop.jpa.model.Alternativa;
import br.com.gloop.jpa.service.AlternativaService;
import br.com.gloop.rest.security.Secured;

@Path("alternativa")
@JaxrsApplicationSelect("(osgi.jaxrs.name=gloop)")
@Component(service = AlternativaRest.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
public class AlternativaRest {

	@Reference
	private AlternativaService alternativaService;

	public AlternativaRest() {
	}

	@Secured
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response list() {
		return Response.ok().entity(alternativaService.list()).build();
	}

	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response get(@PathParam("id") Long id) {
		Response response = null;

		try {
			response = Response.ok().entity(alternativaService.get(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public void add(Alternativa alternativa) {
		alternativaService.add(alternativa);
	}

	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public void edit(Alternativa alternativa) {
		alternativaService.edit(alternativa);
	}

	@Path("/{id}")
	@DELETE
	public void remove(@PathParam("id") Long id) {
		try {
			alternativaService.remove(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}