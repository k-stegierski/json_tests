import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class Main {
    int highest_userid = 0;
    int highest_id = 0;
    public static void main(String[] args) {
    }
    @Test
    public void
    TC_1_highest_user_id() {
        Response response =
        when().
                get("https://jsonplaceholder.typicode.com/posts/").
                then().
                statusCode(200).
                extract().
                response();
        List<Integer> userId = response.path("userId");
        highest_userid = 0;
        for(int i:userId)
        {
            if(highest_userid<i)highest_userid=i;
        }
//        int num = new Random().nextInt(10);
        response =
                when().
                        get("https://jsonplaceholder.typicode.com/posts?userId="+highest_userid).
                        then().
                        statusCode(200).
                        extract().
                        response();
        List<Integer> id = response.path("id");
        highest_id = 0;
        for(int i:id)
        {
            if(highest_id<i)highest_id=i;
        }
        System.out.println("Highest userID: "+highest_userid + " Highest ID within userID: "+ highest_id);

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
        JsonObject post_request = new JsonObject();
        post_request.addProperty("email", "test9@test.com");
        post_request.addProperty("body", "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");
        post_request.addProperty("name", "Karol");
        post_request.addProperty("id", highest_id);
        post_request.addProperty("postid", highest_userid);
        httpRequest.body(post_request.toString());
        response = httpRequest.post("/comments");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,201);
        System.out.println("Status Code is: "+statusCode);
    }
}