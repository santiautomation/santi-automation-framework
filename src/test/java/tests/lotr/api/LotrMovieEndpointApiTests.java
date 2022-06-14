package tests.lotr.api;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import testrunner.ServiceTest;

public class LotrMovieEndpointApiTests extends ServiceTest {

    @Test
    public void lotrGet() {
        Response res = doGet("/movie", true);

        validate.assertTrue(200 == res.statusCode(), "Verifying the response code is 200");
    }

    @Test(description = "Tests an LOTR API Endpoint which requires Authorization, without sending the" +
            "Authorization header, and expects a 401 status from it.")
    public void lotrMovieUnauthorized() {
        Response res = doGet("/movie", false);

        validate.assertTrue(401 == res.statusCode(), "Verifying the response code is 401 Unauthorized");
    }
}
