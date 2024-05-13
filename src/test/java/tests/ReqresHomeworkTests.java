package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@DisplayName("Unit 15. Homework test cases")
public class ReqresHomeworkTests extends TestBase {
    private static final String USER_DATA_UPDATE_METHOD_PUT = "{\"name\": \"user1\", \"job\": \"check put\"}";
    private static final String USER_DATA_UPDATE_METHOD_PATCH = "{\"name\": \"user2\", \"job\": \"check patch\"}";
    private static final String USER_DATA_CREATION_METHOD_POST = "{\"name\": \"user3\", \"job\": \"check post\"}";

    @Test
    @DisplayName("Verify response data after successful user info update with method PUT")
    void successfulUserInfoUpdate() {
        given()
                .body(USER_DATA_UPDATE_METHOD_PUT)
                .contentType(JSON)
                .log().uri()

                .when()
                .put("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("user1"))
                .body("job", is("check put"));
    }

    @Test
    @DisplayName("Verify response data after successful user info update with method PATCH")
    void successfulUserInfoUpdateWithPatch() {
        given()
                .body(USER_DATA_UPDATE_METHOD_PATCH)
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("user2"))
                .body("job", is("check patch"));
    }

    @Test
    @DisplayName("Verify user info data received using method GET. " +
            "!!! There is an error in the text field!!!")
    void successfulSingleUserGetTest() {
        given()
                .log().uri()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"))
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    @DisplayName("Verify status code 404 - single user not found")
    void checkSingleUserNotFound404() {
        given()
                .log().uri()
                .get("/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Verify response data after successful user creation using method POST")
    void successfulUserCreation() {
        given()
                .body(USER_DATA_CREATION_METHOD_POST)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("user3"))
                .body("job", is("check post"));
    }
}
