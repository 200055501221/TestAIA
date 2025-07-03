package aiatest.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.sql.PreparedStatement;
import java.util.Optional;
import java.sql.Connection;
/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");
            
        // Parse query parameter
        final String query = request.getQueryParameters().get("name");
        final String name = request.getBody().orElse(query);
        ObjectMapper mapper = new ObjectMapper();

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).header("Content-Type", "application/json").body("Hello, " + name).build();
        }
    }
}
