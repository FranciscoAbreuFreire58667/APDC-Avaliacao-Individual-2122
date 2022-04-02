package pt.unl.fct.di.adc.firstwebapp.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import pt.unl.fct.di.adc.firstwebapp.util.RegisterData;
import pt.unl.fct.di.adc.firstwebapp.util.RegisterData2;

@Path("/register")
public class RegisterResources {
	private static final Logger LOG = Logger.getLogger(RegisterResources.class.getName());

	public RegisterResources() {

	}

	@POST
	@Path("/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegistrationV1(RegisterData data) {

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		LOG.fine("Attempt to register user: " + data.username);

		if (!data.validRegistration()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();

		}

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
		Entity user = Entity.newBuilder(userKey).set("user_pwd", DigestUtils.sha512Hex(data.password))
				.set("user_creation_time", Timestamp.now()).build();

		datastore.put(user);
		LOG.info("User registered " + data.username);
		return Response.ok("{}").build();
	}
	

	@POST
	@Path("/v2")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegistrationV2(RegisterData2 data) {

		Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		LOG.fine("Attempt to register user: " + data.username);

		if (!data.validRegistration()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
		Entity check = datastore.get(userKey);
		if (check == null) {

			Entity user = Entity.newBuilder(userKey).set("user_pwd", DigestUtils.sha512Hex(data.password))
					.set("user_creation_time", Timestamp.now()).build();

			datastore.put(user);
			LOG.info("User registered " + data.username);
			return Response.ok("{}").build();
		} else {
			LOG.info("Username already in use: " + data.username);
			return Response.status(Status.CONFLICT).entity("Username already in use.").build();
		}
	}
}
