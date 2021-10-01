package in.resreq;

import dto.ListUsers;
import dto.UserLogin;
import io.restassured.http.Cookies;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiTests {

    @Test
    public void firstTest(){
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void secondTest(){

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",equalTo(2))
                .body("data[0].first_name",equalTo("Michael"))
                .body("total",notNullValue())
                .statusCode(200);
    }

    @Test
    public void thirdTest(){

        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",equalTo(2))
                .body("data[0].first_name",equalTo("Michael"))
                .body("total",notNullValue())
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getInt("page"),2,"Page is not 2");
    }

    @Test
    public void fourTest(){

        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page",equalTo(2))
                .body("data[0].first_name",equalTo("Michael"))
                .body("total",notNullValue())
                .statusCode(200)
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(jsonPath.getInt("page"),2,"Page is not 2");
    }

    @Test
    public void fiveTest(){
        ListUsers listUserInfo = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(ListUsers.class);
   //     Assert.assertTrue(listUserInfo.getData().stream().anyMatch(x-> x.getId() == 12));
    }

    @Test
    public void sixTest(){
        HashMap<String,String> reqLogin = new HashMap<>();
        reqLogin.put("email","eve.holt@reqres.in");
        reqLogin.put("password","cityslicka");
        given()
                .contentType("application/json")
                .body(reqLogin)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void sevenTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in","cityslicka");
        given()
                .contentType("application/json")
                .body(userLogin)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void eightTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in","cityslicka");
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        given()
                .body(userLogin)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void nineTest(){
        UserLogin userLogin = new UserLogin("eve.holt@reqres.in","cityslicka");
        Specification.installSpec(Specification.requestSpec(),Specification.responseSpec());
        Cookies cookies= given()
                .body(userLogin)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response()
                .getDetailedCookies();

        given()
                .body(userLogin)
                .cookies(cookies)
                .when()
                .post("/api/login")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void tenTest(){
        given()
                .keyStore("src/Belyy.jks","123456")
                .when()
                .get("https://www.google.ru")
                .then()
                .log().all()
                .statusCode(200);
    }


}
