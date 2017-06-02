package net.halflite.hiq.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

/**
 * トップページのリソースクラス
 *
 * @author halflite
 *
 */
@Path("/")
public class IndexResource {

	/**
	 * インデックス
	 *
	 * @return
	 */
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public Viewable index() {
		return new Viewable("/index");
	}
}
