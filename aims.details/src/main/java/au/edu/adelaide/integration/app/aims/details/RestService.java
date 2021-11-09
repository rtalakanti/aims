package au.edu.adelaide.integration.app.aims.details;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import au.edu.adelaide.integration.app.person.details.model.ErrorMessage;
import au.edu.adelaide.integration.app.person.details.model.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


public interface RestService {

	@Operation(summary = "Returns students' Details.", description = "", tags = { "person" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "An list of addresses", content = @Content(schema =
			@Schema(implementation = PersonResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "401", description = "You are not authorized to submit the request", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "403", description = "You do not have permission to submit the request", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "404", description = "Resource is not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "405", description = "Method Not Allowed"),
			@ApiResponse(responseCode = "406", description = "Not Acceptable"),
			@ApiResponse(responseCode = "500", description = "Unable to process the request", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@Path("/person/{employeeId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PersonResponse getPersonAddresses(
			@Parameter(in = ParameterIn.PATH, description = "The numerical part of the employee/student id", required = true, schema = @Schema()) String employeeId);

	@Operation(summary = "Returns students' Details.", description = "", tags = { "person" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "An list of addresses", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "401", description = "You are not authorized to submit the request", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "403", description = "You do not have permission to submit the request", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "404", description = "Resource is not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
			@ApiResponse(responseCode = "405", description = "Method Not Allowed"),
			@ApiResponse(responseCode = "406", description = "Not Acceptable"),
			@ApiResponse(responseCode = "500", description = "Unable to process the request", content = @Content(schema = @Schema(implementation = ErrorMessage.class))) })
	@Path("/student/{employeeId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PersonResponse getStudentAddresses(
			@Parameter(in = ParameterIn.PATH, description = "The numerical part of the employee/student id", required = true, schema = @Schema()) String employeeId);

}
