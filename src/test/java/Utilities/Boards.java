package Utilities;

import UserData.Urls;
import UserData.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class Boards {

    public static Response createBoard(String boardName)
    {

        return given()
                .baseUri(Urls.boardsUrl)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"name",boardName)
                .when()
                .post();
    }

    public static Response getBoard(String bID)
    {
        return given()
                .baseUri(Urls.boardsUrl+"/"+bID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .when()
                .get();
    }

    public static Response putBoard(String bID)
    {
        return given()
                .baseUri(Urls.boardsUrl+"/"+bID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .queryParam("desc","The board is updated")
                .when()
                .put();
    }

    public static Response delBoard(String bID)
    {
        return given()
                .baseUri(Urls.boardsUrl+"/"+bID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .when()
                .delete();
    }
}
