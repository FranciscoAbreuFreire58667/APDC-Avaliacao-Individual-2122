package pt.unl.fct.di.adc.firstwebapp.resources;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.cloud.datastore.Transaction;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;

import pt.unl.fct.di.adc.firstwebapp.util.AuthToken;
import pt.unl.fct.di.adc.firstwebapp.util.LoginData;
import pt.unl.fct.di.adc.firstwebapp.util.NEW_AccountState;
import pt.unl.fct.di.adc.firstwebapp.util.NEW_ChangePasswordData;
import pt.unl.fct.di.adc.firstwebapp.util.NEW_ChangeRoleAndActivity;
import pt.unl.fct.di.adc.firstwebapp.util.SINGLE_USERNAME_DATA;
import pt.unl.fct.di.adc.firstwebapp.util.NEW_UserChangeDataForUser;
import pt.unl.fct.di.adc.firstwebapp.util.NEW_UserData;
import pt.unl.fct.di.adc.firstwebapp.util.NEW_UserRole;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Path("/test")

public class NEW_ManageResources {

	private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
	private final Gson g = new Gson();
	Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	public NEW_ManageResources() {
	}

	@POST
	@Path("/op00")
	@Consumes(MediaType.APPLICATION_JSON)
	public void ForceDelete(SINGLE_USERNAME_DATA data) {
		LOG.fine("Attempt to register user: " + data.username);

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
		Entity check = datastore.get(userKey);
		if (check != null) {
			datastore.delete(userKey);
		}

	}

	// method to create the first super user
	@POST
	@Path("/op0")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerSU(NEW_UserData data) {
		LOG.fine("Attempt to register user: " + data.username);

		if (!data.validRegistration()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		if (!data.validPassword()) {
			return Response.status(Status.BAD_REQUEST).entity("The passwords inserted are different.").build();
		}
		if (!data.validPassword2()) {
			return Response.status(Status.BAD_REQUEST).entity("The password must have at least 8 characters.").build();
		}
		boolean valid = EmailValidator.getInstance().isValid(data.email);
		if (!valid) {
			return Response.status(Status.BAD_REQUEST).entity("The email inserted is not valid.").build();
		}

		Transaction txt = datastore.newTransaction();
		try {
			Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity check = txt.get(userKey);
			if (check != null) {
				txt.rollback();
				return Response.status(Status.BAD_REQUEST).entity("This username is already used.").build();
			} else {
				Entity user = Entity.newBuilder(userKey).set("username", data.username).set("email", data.email)
						.set("name", data.name).set("password", DigestUtils.sha512Hex(data.password))
						.set("landlineNumber", data.landlineNumber).set("mobilePhoneNumber", data.mobilePhoneNumber)
						.set("mainAddress", data.mainAddress).set("complementaryAddress", data.complementaryAddress)
						.set("placeOfAddress", data.placeOfAddress).set("NIF", data.NIF)
						.set("profilePublic", data.profilePublic).set("role", NEW_UserRole.SU.getRole())
						.set("state", NEW_AccountState.ACTIVE.getValue()).build();
				txt.add(user);
				LOG.info("User registered " + data.username);
				txt.commit();
				return Response.ok("Registered with sucess.").build();
			}
		} finally {
			if (txt.isActive()) {
				txt.rollback();
			}
		}

	}

	@POST
	@Path("/op1")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(NEW_UserData data) {

		LOG.fine("Attempt to register user: " + data.username);

		if (!data.validRegistration()) {
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter.").build();
		}
		if (!data.validPassword()) {
			return Response.status(Status.BAD_REQUEST).entity("The passwords inserted are different.").build();
		}
		if (!data.validPassword2()) {
			return Response.status(Status.BAD_REQUEST).entity("The password must have at least 8 characters.").build();
		}
		boolean valid = EmailValidator.getInstance().isValid(data.email);
		if (!valid) {
			return Response.status(Status.BAD_REQUEST).entity("The email inserted is not valid.").build();
		}

		Transaction txt = datastore.newTransaction();
		try {
			Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity check = txt.get(userKey);
			if (check != null) {
				txt.rollback();
				return Response.status(Status.BAD_REQUEST).entity("This username is already used.").build();
			} else {

				String landlineNumber = data.landlineNumber;
				if (landlineNumber == null) {
					landlineNumber = "UNDEFINED";
				}

				String mobilePhoneNumber = data.mobilePhoneNumber;
				if (mobilePhoneNumber == null) {
					mobilePhoneNumber = "UNDEFINED";
				}

				String mainAddress = data.mainAddress;
				if (mainAddress == null) {
					mainAddress = "UNDEFINED";
				}

				String complementaryAddress = data.complementaryAddress;
				if (complementaryAddress == null) {
					complementaryAddress = "UNDEFINED";
				}

				String placeOfAddress = data.placeOfAddress;
				if (placeOfAddress == null) {
					placeOfAddress = "UNDEFINED";
				}

				String NIF = data.NIF;
				if (NIF == null) {
					NIF = "UNDEFINED";
				}

				String profilePublic = data.profilePublic;
				if (profilePublic == null) {
					profilePublic = "UNDEFINED";
				}

				Entity user = Entity.newBuilder(userKey).set("username", data.username).set("email", data.email)
						.set("name", data.name).set("password", DigestUtils.sha512Hex(data.password))
						.set("landlineNumber", landlineNumber).set("mobilePhoneNumber", mobilePhoneNumber)
						.set("mainAddress", mainAddress).set("complementaryAddress", complementaryAddress)
						.set("placeOfAddress", placeOfAddress).set("NIF", NIF).set("profilePublic", profilePublic)
						.set("role", NEW_UserRole.USER.getRole()).set("state", NEW_AccountState.INACTIVE.getValue())
						.build();
				txt.add(user);
				LOG.info("User registered " + data.username);
				txt.commit();
				return Response.ok("Registered with sucess.").build();
			}
		} finally {
			if (txt.isActive()) {
				txt.rollback();
			}
		}
	}

	@POST
	@Path("/op6/{username}/op2")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeUser(@PathParam("username") String username, SINGLE_USERNAME_DATA data) {
		LOG.fine("Attempt to delete user: " + data.username);

		if (data.username == null) {
			LOG.warning("Missing information.");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Transaction txt = datastore.newTransaction();
		try {

			Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
			Entity user = datastore.get(userKey);

			Key userToBeRemovedKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity userToBeRemoved = datastore.get(userToBeRemovedKey);

			Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(username);
			Entity token = datastore.get(userTokenKey);

			Key userToBeRemovedTokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.username);
			Entity tokenToBeRemoved = datastore.get(userToBeRemovedTokenKey);

			if (token == null) {

				LOG.warning("Login is necessary.");
				return Response.status(Status.BAD_REQUEST).entity("Login is necessary.1").build();
			}
			if (token.getLong("expirationData") < System.currentTimeMillis()) {
				datastore.delete(userTokenKey);

				LOG.warning("Login is necessary.");
				return Response.status(Status.BAD_REQUEST).entity("Login is necessary.2").build();
			}

			if (userToBeRemoved == null) {
				txt.rollback();

				LOG.warning("Username does not exist.");
				return Response.status(Status.BAD_REQUEST).entity("Username does not exist.").build();
			}

//			if (token == null) {
//				LOG.warning("No Token.");
//				return Response.status(Status.BAD_REQUEST).entity("No Token.").build();
//			}
//			if (token.getLong("expirationData") < System.currentTimeMillis()) {
//				datastore.delete(userTokenKey);
//				LOG.warning("Token expired.");
//				return Response.status(Status.BAD_REQUEST).entity("Token expired.").build();
//			}

			String roleToBeRemoved = userToBeRemoved.getString("role");
			String role = user.getString("role");
			if (!possibleToRemove(username, role, data.username, roleToBeRemoved)) {
				txt.rollback();
				LOG.warning("Username: " + username + " does not permission to complete this action.");
				return Response.status(Status.BAD_REQUEST).entity("no permission.").build();
			} else {
				if (tokenToBeRemoved != null)
					txt.delete(userToBeRemovedTokenKey);
				txt.delete(userToBeRemovedKey);
				LOG.info("User deleted " + data.username);
				txt.commit();
				return Response.ok("Account deleted with sucess.").build();
			}
		} finally {
			if (txt.isActive()) {
				txt.rollback();
			}
		}

	}

	@POST
	@Path("/op6/{username}/op4")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeAttrib(@PathParam("username") String username, NEW_UserChangeDataForUser data) {
		LOG.fine("Attempt to change the info of one username.");

		if (data.username == null) {
			LOG.warning("Missing information.");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Transaction txt = datastore.newTransaction();
		try {

			Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
			Entity user = datastore.get(userKey);

			Key userToBeChangedKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity userToBeChanged = datastore.get(userToBeChangedKey);

			Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(username);
			Entity token = datastore.get(userTokenKey);

			if (token == null) {

				LOG.warning("Login is necessary.");
				return Response.status(Status.BAD_REQUEST).entity("Login is necessary.1").build();
			}
			if (token.getLong("expirationData") < System.currentTimeMillis()) {
				datastore.delete(userTokenKey);

				LOG.warning("Login is necessary.");
				return Response.status(Status.BAD_REQUEST).entity("Login is necessary.2").build();
			}

			if (userToBeChanged == null) {
				txt.rollback();

				LOG.warning("Username does not exist.");
				return Response.status(Status.BAD_REQUEST).entity("Username does not exist.").build();
			}

			String roleToBeChanged = userToBeChanged.getString("role");
			String role = user.getString("role");

			if (role.equals(NEW_UserRole.USER.getRole()) && !username.equals(data.username)) {
				txt.rollback();
				LOG.warning("Username: " + username + " does not permission to complete this action.");
				return Response.status(Status.BAD_REQUEST).entity("no permission.1a").build();

			}
			if (role.equals(NEW_UserRole.SU.getRole()) && role.equals(roleToBeChanged)) {
				txt.rollback();
				LOG.warning("Username: " + username + " does not permission to complete this action.");
				return Response.status(Status.BAD_REQUEST).entity("no permission.1b").build();
			}
			if (role.equals(NEW_UserRole.GS.getRole())) {
				if (!roleToBeChanged.equals(NEW_UserRole.USER.getRole())
						|| !roleToBeChanged.equals(NEW_UserRole.GBO.getRole())) {
					txt.rollback();
					LOG.warning("Username: " + username + " does not permission to complete this action.");
					return Response.status(Status.BAD_REQUEST).entity("no permission.1c").build();
				}
			}
			if (role.equals(NEW_UserRole.GBO.getRole()) && !roleToBeChanged.equals(NEW_UserRole.USER.getRole())) {
				txt.rollback();
				LOG.warning("Username: " + username + " does not permission to complete this action.");
				return Response.status(Status.BAD_REQUEST).entity("no permission.1d").build();

			} else {

				if (role.equals(NEW_UserRole.USER.getRole()) && username.equals(data.username)) {
					String landlineNumber = data.landlineNumber;
					if (landlineNumber == null) {
						landlineNumber = userToBeChanged.getString("landlineNumber");
					}

					String mobilePhoneNumber = data.mobilePhoneNumber;
					if (mobilePhoneNumber == null) {
						mobilePhoneNumber = userToBeChanged.getString("mobilePhoneNumber");
					}

					String mainAddress = data.mainAddress;
					if (mainAddress == null) {
						mainAddress = userToBeChanged.getString("mainAddress");
					}

					String complementaryAddress = data.complementaryAddress;
					if (complementaryAddress == null) {
						complementaryAddress = userToBeChanged.getString("complementaryAddress");
					}

					String placeOfAddress = data.placeOfAddress;
					if (placeOfAddress == null) {
						placeOfAddress = userToBeChanged.getString("placeOfAddress");
					}

					String NIF = data.NIF;
					if (NIF == null) {
						NIF = userToBeChanged.getString("NIF");
					}

					String profilePublic = data.profilePublic;
					if (profilePublic == null) {
						profilePublic = userToBeChanged.getString("profilePublic");
					}

					Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey))

							.set("landlineNumber", landlineNumber).set("mobilePhoneNumber", mobilePhoneNumber)
							.set("mainAddress", mainAddress).set("complementaryAddress", complementaryAddress)
							.set("placeOfAddress", placeOfAddress).set("NIF", NIF).set("profilePublic", profilePublic)
							.build();
					txt.update(userToUpdate);
					LOG.info("User changed " + data.username);
					txt.commit();
					return Response.ok("Account changed with sucess.").build();
				} else if ((role.equals(NEW_UserRole.GBO.getRole())
						&& roleToBeChanged.equals(NEW_UserRole.USER.getRole()))
						|| (role.equals(NEW_UserRole.SU.getRole()))
						|| (role.equals(NEW_UserRole.GS.getRole())
								&& roleToBeChanged.equals(NEW_UserRole.GBO.getRole()))
						|| (role.equals(NEW_UserRole.GS.getRole())
								&& roleToBeChanged.equals(NEW_UserRole.USER.getRole()))) {

					String email = data.email;
					if (email == null) {
						email = userToBeChanged.getString("email");
					}

					String name = data.name;
					if (name == null) {
						name = userToBeChanged.getString("name");
					}

					String landlineNumber = data.landlineNumber;
					if (landlineNumber == null) {
						landlineNumber = userToBeChanged.getString("landlineNumber");
					}

					String mobilePhoneNumber = data.mobilePhoneNumber;
					if (mobilePhoneNumber == null) {
						mobilePhoneNumber = userToBeChanged.getString("mobilePhoneNumber");
					}

					String mainAddress = data.mainAddress;
					if (mainAddress == null) {
						mainAddress = userToBeChanged.getString("mainAddress");
					}

					String complementaryAddress = data.complementaryAddress;
					if (complementaryAddress == null) {
						complementaryAddress = userToBeChanged.getString("complementaryAddress");
					}

					String placeOfAddress = data.placeOfAddress;
					if (placeOfAddress == null) {
						placeOfAddress = userToBeChanged.getString("placeOfAddress");
					}

					String NIF = data.NIF;
					if (NIF == null) {
						NIF = userToBeChanged.getString("NIF");
					}

					String profilePublic = data.profilePublic;
					if (profilePublic == null) {
						profilePublic = userToBeChanged.getString("profilePublic");
					}

					Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey)).set("email", email)
							.set("name", name).set("landlineNumber", landlineNumber)
							.set("mobilePhoneNumber", mobilePhoneNumber).set("mainAddress", mainAddress)
							.set("complementaryAddress", complementaryAddress).set("placeOfAddress", placeOfAddress)
							.set("NIF", NIF).set("profilePublic", profilePublic).build();
					txt.update(userToUpdate);
					LOG.info("User changed " + data.username);
					txt.commit();
					return Response.ok("Account changed with sucess.").build();
				
					
				}else {
					txt.rollback();
					LOG.warning("Username: " + username + " does not permission to complete this action.");
					return Response.status(Status.BAD_REQUEST).entity("no permission.2").build();
				}

			}
		} finally {
			if (txt.isActive()) {
				txt.rollback();
			}
		}

	}

	@POST
	@Path("/op6/{username}/op4a")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeRoleAndActivity(@PathParam("username") String username, NEW_ChangeRoleAndActivity data) {
		LOG.fine("Attempt to change the info of one username.");
		if (username == null) {
			LOG.warning("There's no such user.");
			return Response.status(Status.FORBIDDEN).entity("There's no such user.").build();
		}
		Transaction txt = datastore.newTransaction();
		try {

			Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
			Entity user = datastore.get(userKey);

			Key userToBeChangedKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity userToBeChanged = datastore.get(userToBeChangedKey);

			Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(username);
			Entity token = datastore.get(userTokenKey);

			if (token == null) {

				LOG.warning("Login is necessary.");
				return Response.status(Status.BAD_REQUEST).entity("Login is necessary.1").build();
			}
			if (token.getLong("expirationData") < System.currentTimeMillis()) {
				datastore.delete(userTokenKey);

				LOG.warning("Login is necessary.");
				return Response.status(Status.BAD_REQUEST).entity("Login is necessary.2").build();
			}

			if (userToBeChanged == null) {
				txt.rollback();

				LOG.warning("Username does not exist.");
				return Response.status(Status.BAD_REQUEST).entity("Username does not exist.").build();
			}

			String roleToBeChanged = userToBeChanged.getString("role");
			String role = user.getString("role");

			if (role.equals(NEW_UserRole.USER.getRole()) && user == userToBeChanged) {
				String state = data.getState();
				if (state == null) {
					state = userToBeChanged.getString("state");
				}
				Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey)).set("state", state).build();
				txt.update(userToUpdate);
				LOG.info("User changed " + data.username);
				txt.commit();
				return Response.ok("Account changed with sucess.").build();

			} else if (role.equals(NEW_UserRole.GBO.getRole()) && roleToBeChanged.equals(NEW_UserRole.USER.getRole())) {
				String state = data.getState();
				if (state == null) {
					state = userToBeChanged.getString("state");
				}
				Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey)).set("state", state).build();
				txt.update(userToUpdate);
				LOG.info("User changed " + data.username);
				txt.commit();
				return Response.ok("Account changed with sucess.").build();
			} else if (role.equals(NEW_UserRole.GS.getRole()) && roleToBeChanged.equals(NEW_UserRole.USER.getRole())
					&& data.role.equals(NEW_UserRole.GBO.getRole())) {
				String roleToBe = data.getRole();
				if (roleToBe == null) {
					roleToBe = userToBeChanged.getString("role");
				}
				Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey))
						.set("role", NEW_UserRole.GBO.getRole()).build();
				txt.update(userToUpdate);
				LOG.info("User changed " + data.username);
				txt.commit();
				return Response.ok("Account changed with sucess.").build();

			} else if (role.equals(NEW_UserRole.GS.getRole()) && roleToBeChanged.equals(NEW_UserRole.GBO.getRole())) {
				String state = data.getState();
				if (state == null) {
					state = userToBeChanged.getString("state");
				}
				Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey))
						.set("state", NEW_UserRole.GBO.getRole()).build();
				txt.update(userToUpdate);
				LOG.info("User changed " + data.username);
				txt.commit();
				return Response.ok("Account changed with sucess.").build();

			} else if (role.equals(NEW_UserRole.SU.getRole())) {
				String state = data.getState();
				if (state == null) {
					state = userToBeChanged.getString("state");
				}
				String roleToBe = data.getRole();
				if (roleToBe == null) {
					roleToBe = userToBeChanged.getString("role");
				}
				Entity userToUpdate = Entity.newBuilder(datastore.get(userToBeChangedKey)).set("state", state)
						.set("role", roleToBe).build();
				txt.update(userToUpdate);
				LOG.info("User changed " + data.username);
				txt.commit();
				return Response.ok("Account changed with sucess.").build();
			} else {
				txt.rollback();
				LOG.warning("Username: " + username + " does not permission to complete this action.");
				return Response.status(Status.BAD_REQUEST).entity("no permission.").build();
			}

		} finally {
			if (txt.isActive()) {
				txt.rollback();
			}
		}

	}

	@POST
	@Path("/op6/{username}/op3")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listUsers(@PathParam("username") String username) {
		LOG.fine("List users.");
		if (username == null) {
			LOG.warning("There's no such user.");
			return Response.status(Status.FORBIDDEN).entity("There's no such user.").build();
		}

		Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(username);
		Entity token = datastore.get(userTokenKey);
		if (token == null) {

			LOG.warning("Login is necessary.");
			return Response.status(Status.BAD_REQUEST).entity("Login is necessary.1").build();
		}
		if (token.getLong("expirationData") < System.currentTimeMillis()) {
			datastore.delete(userTokenKey);

			LOG.warning("Login is necessary.");
			return Response.status(Status.BAD_REQUEST).entity("Login is necessary.2").build();
		}

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
		Entity user = datastore.get(userKey);
		String role = user.getString("role");

		if (role.equals(NEW_UserRole.SU.getRole())) {
			Query<Entity> query = Query.newEntityQueryBuilder().setKind("User").build();
			List<Entity> users = new LinkedList<>();
			QueryResults<Entity> logs = datastore.run(query);

			while (logs.hasNext()) {
				Entity userToPrint = logs.next();
				users.add(userToPrint);
			}
			String result = "";
			for (Entity entity : users) {
				result += entity.getString("username") + " " + entity.getString("email") + " "
						+ entity.getString("name") + " " + entity.getString("landlineNumber") + " "
						+ entity.getString("mobilePhoneNumber") + " " + entity.getString("mainAddress") + " "
						+ entity.getString("complementaryAddress") + " " + entity.getString("placeOfAddress") + " "
						+ entity.getString("NIF") + " "// + entity.getString("profilePublic") + " "
						+ entity.getString("role") + " " + entity.getString("state") + "\n";
			}

			// return Response.ok(g.toJson(users)).build();
			return Response.ok(result).build();

		} else if (role.equals(NEW_UserRole.GS.getRole())) {
			List<Entity> users = new LinkedList<>();

			Query<Entity> queryUSER = Query.newEntityQueryBuilder().setKind("User")
					.setFilter((PropertyFilter.ge("role", NEW_UserRole.USER.getRole()))).build();
			QueryResults<Entity> logs = datastore.run(queryUSER);
			Query<Entity> queryGBO = Query.newEntityQueryBuilder().setKind("User")
					.setFilter((PropertyFilter.ge("role", NEW_UserRole.GBO.getRole()))).build();
			QueryResults<Entity> logs2 = datastore.run(queryGBO);

			while (logs.hasNext()) {
				Entity userToPrint = logs.next();
				users.add(userToPrint);
			}

			while (logs2.hasNext()) {
				Entity userToPrint = logs2.next();
				users.add(userToPrint);
			}
			String result = "";
			for (Entity entity : users) {
				result += entity.getString("username") + " " + entity.getString("email") + " "
						+ entity.getString("name") + " " + entity.getString("landlineNumber") + " "
						+ entity.getString("mobilePhoneNumber") + " " + entity.getString("mainAddress") + " "
						+ entity.getString("complementaryAddress") + " " + entity.getString("placeOfAddress") + " "
						+ entity.getString("NIF") + " "// + entity.getString("profilePublic") + " "
						+ entity.getString("role") + " " + entity.getString("state") + "\n";
			}

			return Response.ok(result).build();

		} else if (role.equals(NEW_UserRole.GBO.getRole())) {
			List<Entity> users = new LinkedList<>();

			Query<Entity> queryUSER = Query.newEntityQueryBuilder().setKind("User")
					.setFilter((PropertyFilter.ge("role", NEW_UserRole.USER.getRole()))).build();
			QueryResults<Entity> logs = datastore.run(queryUSER);
			while (logs.hasNext()) {
				Entity userToPrint = logs.next();
				users.add(userToPrint);
			}
			String result = "";
			for (Entity entity : users) {
				result += entity.getString("username") + " " + entity.getString("email") + " "
						+ entity.getString("name") + " " + entity.getString("landlineNumber") + " "
						+ entity.getString("mobilePhoneNumber") + " " + entity.getString("mainAddress") + " "
						+ entity.getString("complementaryAddress") + " " + entity.getString("placeOfAddress") + " "
						+ entity.getString("NIF") + " "// + entity.getString("profilePublic") + " "
						+ entity.getString("role") + " " + entity.getString("state") + "\n";
			}

			return Response.ok(result).build();
		} else {

			List<Entity> users = new LinkedList<>();

			Query<Entity> queryUSER = Query.newEntityQueryBuilder().setKind("User")
					.setFilter(PropertyFilter.ge("role", NEW_UserRole.USER.getRole())).build();
//							PropertyFilter.ge("state", NEW_AccountState.ACTIVE.getValue()),
//							PropertyFilter.ge("profilePublic", "true")))

			QueryResults<Entity> logs = datastore.run(queryUSER);
			while (logs.hasNext()) {
				Entity userToPrint = logs.next();
				if (userToPrint.getString("state").equals(NEW_AccountState.ACTIVE.getValue())
						&& userToPrint.getString("profilePublic").equals("true"))
					users.add(userToPrint);
			}

			String result = "";
			for (Entity entity : users) {
				result += entity.getString("username") + " " + entity.getString("email") + " "
						+ entity.getString("name") + "\n";
			}

			return Response.ok(result).build();
		}

	}

	@POST
	@Path("/op6")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doLogin(LoginData data) {
		LOG.fine("Attempt to login user: " + data.username);
		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
		Entity user = datastore.get(userKey);

		Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.username);

		if (user != null) {
			String hashedPassword = user.getString("password");
			if (hashedPassword.equals(DigestUtils.sha512Hex(data.password))) {

				Entity tokenCheck = datastore.get(tokenKey);

				if (tokenCheck == null) {
					if (user.getString("state").equals(NEW_AccountState.ACTIVE.getValue())) {
						AuthToken token = new AuthToken(data.username);
						Entity tokenEntity = Entity.newBuilder(tokenKey).set("username", data.username)
								.set("role", user.getString("role")).set("creationData", token.creationData)
								.set("expirationData", token.expirationData).set("tokenID", token.tokenID).build();
						datastore.add(tokenEntity);
						LOG.info("User " + data.username + " has logged in sucessefully.");
						// return Response.ok(g.toJson(token)).build();
						return Response.ok().entity("Success.").build();
					} else {
						LOG.warning("The account with the username: " + data.username + " is not active.");
						return Response.status(Status.FORBIDDEN).entity("Not active account.").build();
					}
				} else {
					if ((tokenCheck.getLong("expirationData") < System.currentTimeMillis())) {
						datastore.delete(tokenKey);
						if (user.getString("state").equals(NEW_AccountState.ACTIVE.getValue())) {
							AuthToken token = new AuthToken(data.username);
							Entity tokenEntity = Entity.newBuilder(tokenKey).set("username", data.username)
									.set("role", user.getString("role")).set("creationData", token.creationData)
									.set("expirationData", token.expirationData).set("tokenID", token.tokenID).build();
							datastore.add(tokenEntity);
							LOG.info("User " + data.username + " has logged in sucessefully.");
							// return Response.ok(g.toJson(token)).entity("Success.").build();
							return Response.ok().entity("Success.").build();
						} else {
							LOG.warning("The account with the username: " + data.username + " is not active.");
							return Response.status(Status.FORBIDDEN).entity("Not active account.").build();
						}
					} else {
						LOG.warning("The account with the username: " + data.username + " is not active.");
						return Response.status(Status.FORBIDDEN).entity("Already logged in.Test.").build();
					}
				}
			} else {
				LOG.warning("Wrong password for username: " + data.username);
				return Response.status(Status.FORBIDDEN).entity("Wrong password.").build();

			}

		} else {
			LOG.warning("Failed login attempt for username: " + data.username);
			return Response.status(Status.FORBIDDEN).entity("There is no such user.").build();
		}

	}

	@POST
	@Path("/op6/{username}/op7")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response token(@PathParam("username") String username) {
		LOG.fine("Token of user: " + username);
		if (username == null) {
			LOG.warning("Missing information.");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Key userTokenKey = datastore.newKeyFactory().setKind("Token").newKey(username);
		Entity token = datastore.get(userTokenKey);
		if (token == null) {

			LOG.warning("Login is necessary.");
			return Response.status(Status.BAD_REQUEST).entity("Login is necessary.1").build();
		}
		if (token.getLong("expirationData") < System.currentTimeMillis()) {
			datastore.delete(userTokenKey);

			LOG.warning("Login is necessary.");
			return Response.status(Status.BAD_REQUEST).entity("Login is necessary.2").build();
		} else {
			LOG.warning("Login is necessary.");
			return Response.ok(g.toJson(token)).build();
		}

	}

	@POST
	@Path("/op6/{username}/op5")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response changePassword(@PathParam("username") String username, NEW_ChangePasswordData data) {
		LOG.fine("Token of user: " + username);
		if (username == null) {
			LOG.warning("Missing information.");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
		Entity user = datastore.get(userKey);
		if (user != null) {
			String hashedPassword = user.getString("password");
			if (hashedPassword.equals(DigestUtils.sha512Hex(data.oldPassword))
					&& data.newPassword.equals(data.confirmation)) {
				// update user
				Entity userToUpdate = Entity.newBuilder(datastore.get(userKey))

						.set("password", DigestUtils.sha512Hex(data.newPassword)).build();
				datastore.update(userToUpdate);
				LOG.info("Success.");
				return Response.ok().entity("Success.").build();
			} else {
				LOG.warning("Failed attempt to change password for username: " + username);
				return Response.status(Status.FORBIDDEN).build();
			}
		} else {
			LOG.warning("Failed attempt to change password for username: " + username);
			return Response.status(Status.FORBIDDEN).build();
		}

	}

	@POST
	@Path("/op6/{username}/op8")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response logOut(@PathParam("username") String username) {
		LOG.fine("Token of user: " + username);
		if (username == null) {
			LOG.warning("Missing information.");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
		Entity user = datastore.get(userKey);

		if (user != null) {
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(username);
			Entity tokenCheck = datastore.get(tokenKey);

			if (tokenCheck != null) {
				datastore.delete(tokenKey);
				LOG.info("Success.");
				return Response.ok().entity("Success").build();
			} else {
				LOG.info("Success.");
				return Response.ok().entity("No login made yet.").build();
			}

		} else {
			LOG.warning("Failed attempt to change password for username: " + username);
			return Response.status(Status.FORBIDDEN).build();
		}

	}

	private boolean possibleToRemove(String username, String role, String usernameToBeRemoved, String roleToRemove) {

		if (role.equals(NEW_UserRole.USER.getRole()) && !username.equals(usernameToBeRemoved))
			return false;

		if (role.equals(NEW_UserRole.SU.getRole())) {
			return true;
		}
		if (role.equals(NEW_UserRole.GS.getRole())) {
			if (!roleToRemove.equals(NEW_UserRole.USER.getRole()) || !roleToRemove.equals(NEW_UserRole.GBO.getRole())) {
				return false;
			}
		}
		if (role.equals(NEW_UserRole.GBO.getRole()) && !roleToRemove.equals(NEW_UserRole.USER.getRole())) {
			return false;
		}

		return true;
	}


}
