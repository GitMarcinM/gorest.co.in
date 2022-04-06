package RestAssured.post_management;

import RestAssured.EndpointsPosts;
import RestAssured.model.Post;
import RestAssured.model.User;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class PatchPostTest extends EndpointsPosts {

    Response responseCreatedPost;
    String postId;
    Post newPost;

    Response responseCreatedUser;
    String userId;
    User validUser;

    @Before
    public void setUp() throws Exception {
        validUser = new User(
                "New User",
                "test.email@address.com",
                "female",
                "inactive"
        );
        responseCreatedUser = createUser(validUser);
        userId = responseCreatedUser.jsonPath().getString("id");

        newPost = new Post(
                userId,
                "This is the title of the post",
                "This is the body of the post"
        );
        responseCreatedPost = createPost(newPost);
        postId = responseCreatedPost.jsonPath().getString("id");
    }

    @After
    public void tearDown() throws Exception {
        deletePost(postId);
        deleteUser(userId);
    }

    @Test
    public void testCreatePatchDeletePost() {

        Post updatePost = new Post(
                postId,
                newPost.getUser_id(),
                "This is the title of post after update",
                "This is the body of the post after update"
        );

        Response postDataResponse = getPostByTitle(newPost.getTitle());

        patchPost(updatePost);
        Response getPatchedPost = getPostByTitle(updatePost.getTitle());

        Post[] postFromResponse = postDataResponse.getBody().as(Post[].class);
        System.out.println(postFromResponse[0]);

        Post[] patchPostFromResponse = getPatchedPost.getBody().as(Post[].class);
        System.out.println(patchPostFromResponse[0]);

        assertThat("New post should be the same as post from response",
                postFromResponse[0].equals(newPost));

        assertThat("Update post should be the same as post from response",
                patchPostFromResponse[0].equals(updatePost));
    }
}
