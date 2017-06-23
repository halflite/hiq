package net.halflite.hiq.resource;

import static javax.ws.rs.core.Response.Status.FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.net.URI;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.pac4j.jax.rs.annotations.Pac4JCallback;
import org.pac4j.jax.rs.annotations.Pac4JProfile;
import org.pac4j.jax.rs.annotations.Pac4JSecurity;
import org.pac4j.oauth.profile.twitter.TwitterProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 認証コールバックのリソースクラス
 *
 * @author halflite
 *
 */
@Path("authenticate")
public class AuthenticateResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateResource.class);

	@GET
	@Pac4JSecurity(clients = "TwitterClient", authorizers = "isAuthenticated")
	public Response authenticate(@Pac4JProfile Optional<TwitterProfile> profile) {
		TwitterProfile twitterProfile = profile.orElseThrow(() -> new WebApplicationException(401));
		LOGGER.debug("Returning infos for {}", twitterProfile);
		return Response.ok().build();
	}

	@GET
	@Path("callback")
	@Pac4JCallback(skipResponse = true, defaultUrl = "/page")
	public Response callback(@Pac4JProfile Optional<TwitterProfile> profile, @Context UriInfo uriInfo) {
		TwitterProfile twitterProfile = profile.orElseThrow(() -> new WebApplicationException(UNAUTHORIZED));
		LOGGER.debug("Returning infos for {}", twitterProfile);
		URI location = uriInfo.getBaseUriBuilder()
				.path(PageResource.class)
				.path("/")
				.build();
		return Response.status(FOUND).location(location).build();
	}
}
