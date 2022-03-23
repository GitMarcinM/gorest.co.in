package RestAssured.user_management;

import RestAssured.Endpoints;
import RestAssured.model.User;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PatchUserTest extends Endpoints {

    Response responseCreatedUser;
    String userId;
    User validUser;

    @Before
    public void setUp() throws Exception {
        validUser = new User(
                "New User",
                "test.email@ddress.com",
                "male",
                "active"
        );
        responseCreatedUser = createUser(validUser);
        userId = responseCreatedUser.jsonPath().getString("id");
        validUser.setEmail(validUser.getEmail());
    }

    @After
    public void tearDown() throws Exception {
        deleteUser(userId);
    }

    @Test
    public void CreateGetPatchGetDeleteUser() {

        User updateUser = new User(
                "Update User",
                validUser.getEmail(),
                "female",
                "inactive"
        );

        Response getCreatedUser = getUserByName(validUser.getName());

        patchUser(updateUser, userId);
        Response getPatchedUser = getUserByName(updateUser.getName());

        User[] userFromResponse = getCreatedUser.getBody().as(User[].class);
        System.out.println(userFromResponse[0]);

        User[] patchUserFromResponse = getPatchedUser.getBody().as(User[].class);
        System.out.println(patchUserFromResponse[0]);

        assertThat("Valid user should be the same as user from response",
                userFromResponse[0].equals(validUser));

        assertThat("Patched user should be the same as user from response",
                patchUserFromResponse[0].equals(updateUser));
    }
}
