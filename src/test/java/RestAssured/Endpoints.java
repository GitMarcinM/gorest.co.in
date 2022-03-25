package RestAssured;

import RestAssured.model.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

public class Endpoints {

    public static final String ACCESS_TOKEN = "eebd514ca76ddfb7e628131ec49a287d2b9b88fb52fe2046c376f655d22c94cf";

    @BeforeClass
    public static void beforeClass() throws Exception {
        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "https://gorest.co.in";
        }
        RestAssured.baseURI = baseHost;

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "/public/v2";
        }
        RestAssured.basePath = basePath;
    }

    public static Response createUser(User user) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.body(user).post("/users");
        return response;
    }

    public static Response deleteUser(String userId) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.delete("/users/" + userId);
        return response;
    }

    public static Response getUserByName(String userName) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.get("/users?name=" + userName);
        return response;
    }

    public static Response getUsers() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.get("/users/");
        return response;
    }

    public static Response patchUser(User user) {
        RequestSpecification request = RestAssured.given();
        request.body("{" +
                        "    \"name\" : \""+user.getName()+"\","+
                        "    \"gender\" : \""+user.getGender()+"\","+
                        "    \"status\" : \""+user.getStatus()+"\""+
                        "}")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.patch("/users/" + user.getId());
        return response;
    }
}
