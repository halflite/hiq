package net.halflite.hiq.resource;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.pac4j.jax.rs.annotations.Pac4JCallback;
import org.pac4j.jax.rs.annotations.Pac4JProfile;
import org.pac4j.jax.rs.annotations.Pac4JSecurity;
import org.pac4j.oauth.profile.twitter.TwitterProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.halflite.hiq.entity.Account;
import net.halflite.hiq.logic.AuthenticateLogic;

/**
 * 認証コールバックのリソースクラス
 *
 * @author halflite
 *
 */
@Path("authenticate")
public class AuthenticateResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateResource.class);

	private final AuthenticateLogic authenticateLogic;

	@GET
	@Pac4JSecurity(clients = "TwitterClient", authorizers = "isAuthenticated")
	public Response authenticate(@Context UriInfo uriInfo, @Pac4JProfile Optional<TwitterProfile> profile) {
		TwitterProfile twitterProfile = profile.orElseThrow(() -> new WebApplicationException(Status.UNAUTHORIZED));
		LOGGER.debug("Profile {}", twitterProfile);
		Account account = authenticateLogic.findAccountOrCreate(twitterProfile);
		LOGGER.debug("Accout:{}", account);
		URI location = uriInfo.getBaseUriBuilder()
				.path(PageResource.class)
				.path("/")
				.build();
		return Response.temporaryRedirect(location).build();
	}

	@GET
	@Path("callback")
	@Pac4JCallback(skipResponse = true)
	public Response callback(@Context UriInfo uriInfo, @Pac4JProfile Optional<TwitterProfile> profile) {
		TwitterProfile twitterProfile = profile.orElseThrow(() -> new WebApplicationException(Status.UNAUTHORIZED));
		LOGGER.debug("Profile {}", twitterProfile);
		URI location = uriInfo.getBaseUriBuilder()
				.path(AuthenticateResource.class)
				.build();
		return Response.temporaryRedirect(location).build();
	}

	@Inject
	public AuthenticateResource(AuthenticateLogic authenticateLogic) {
		this.authenticateLogic = authenticateLogic;
	}
}
