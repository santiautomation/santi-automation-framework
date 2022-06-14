package testrunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import logging.Report;
import org.testng.annotations.BeforeClass;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Base Service Test class.
 * All API-only tests should extend this class
 */
public class ServiceTest extends BaseTest {

    @BeforeClass
    protected void initRestAssured() {
        RestAssured.useRelaxedHTTPSValidation();

        String baseHost = properties.getString("url");
        RestAssured.baseURI = baseHost;
    }

    protected Map<String, String> generateHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "Bearer " + properties.getString("api.accesstoken"));

        return headers;
    }

    protected Response doGet(String endpoint, boolean withAuthorization) {
        Report.log(String.format("Calling GET on endpoint: %s%s", RestAssured.baseURI, endpoint));

        Response res;

        if (withAuthorization) {
            res = given().when()
                    .headers(generateHeaders())
                    .get(endpoint);
        } else {
            res = given().when()
                    .get(endpoint);
        }

        Report.log("Response Code: " + res.statusCode());
        Report.log("Response Body: " + res.asString());

        return res;
    }
}
