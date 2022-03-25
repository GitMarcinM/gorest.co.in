package RestAssured.user_management;

import RestAssured.Endpoints;
import RestAssured.model.User;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetUserTest extends Endpoints {

    Response responseCreatedUser;
    String userId;
    User validUser;

    @Before
    public void setUp() throws Exception {
        validUser = new User(
                "testUserXd",
                "testEmajl@testgmail.com",
                "male",
                "active"
        );
        responseCreatedUser = createUser(validUser);
        userId = responseCreatedUser.jsonPath().getString("id");
    }

    @After
    public void tearDown() throws Exception {
        deleteUser(userId);
    }

    @Test
    public void testGetAllUsers() {
        Response getAllUsers = getUsers();
        getAllUsers.then().log().body();
    }

    @Test
    public void testGetUserAfterCreate() {

        Response userDataResponse = getUserByName(validUser.getName());
        userDataResponse.then().log().body();

        User[] userFromResponse = userDataResponse.getBody().as(User[].class);

        System.out.println(userFromResponse[0].toString());

        assertThat("Valid user should be the same as user from response",
                userFromResponse[0].equals(validUser));
    }
}
