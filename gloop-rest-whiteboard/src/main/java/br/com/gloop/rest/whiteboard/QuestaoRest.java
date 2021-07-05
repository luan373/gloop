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

import br.com.gloop.jpa.model.Questao;
import br.com.gloop.jpa.service.QuestaoService;
import br.com.gloop.rest.security.Secured;

@Path("questao")
@JaxrsApplicationSelect("(osgi.jaxrs.name=gloop)")
@Component(service = QuestaoRest.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
public class QuestaoRest {

	@Reference
	private QuestaoService questaoService;
	
	public QuestaoRest() {
	}
	
	@Secured
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response list() {
		return Response.ok().entity(questaoService.list()).build();
	}

	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response get(@PathParam("id") Long id) {
		Response response = null;

		try {
			response = Response.ok().entity(questaoService.get(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response add(Questao questao) {
		Response response = null;

		try {
			questaoService.add(questao);

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
	public void edit(Questao questao) {
		questaoService.edit(questao);
	}

	@Path("/{id}")
	@DELETE
	public void remove(@PathParam("id") Long id) {
		questaoService.remove(id);
	}

}