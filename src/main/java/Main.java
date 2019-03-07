import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
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
    TC_1highest_user_id() {
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
        System.out.println(highest_userid);
    }
    @Test
    public void
    TC_2highest_id(){
//        int num = new Random().nextInt(10);
        Response response =
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
        System.out.println(highest_userid + " "+ highest_id);

            RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
            given().urlEncodingEnabled(true)
                    .contentType("application/json")
                    .param("postId", highest_id)
                    .param("id", 1)
                    .param("name", "Karol")
                    .param("email", "k@S.com")
                    .param("body", "Fun Comment")
                    .header("Accept", ContentType.JSON.getAcceptHeader())
                    .post("/comments")
                    .then().statusCode(200);


    }
}