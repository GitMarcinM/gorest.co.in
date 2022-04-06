package RestAssured;

import RestAssured.model.Post;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndpointsPosts extends Endpoints {

    public static Response getPosts() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.get("/posts/");
        return response;
    }

    public static Response getPostByUserId(String userId) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.get("/posts?user_id=" + userId);
        return response;
    }

    public static Response createPost(Post post) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.body(post).post("/posts");
        return response;
    }

    public static Response patchPost(Post post) {
        RequestSpecification request = RestAssured.given();
        request.body("{" +
                        "    \"title\" : \" "+post.getTitle()+"\""+
                        "    \"body\" : \""+post.getBody()+"\""+
                        "}")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.patch("/posts/" + post.getId());
        return response;
    }

    public static Response deletePost(String postId) {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.delete("/posts/" + postId);
        return response;
    }
}
