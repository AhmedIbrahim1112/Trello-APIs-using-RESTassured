package Utilities;

import UserData.Urls;
import UserData.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class Lists {

    public static Response createList(String listName,String bID)
    {

        return given()
                .baseUri(Urls.listsUrl)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"name",listName,"idBoard",bID)
                .when()
                .post();
    }

    public static Response getList(String lID)
    {
        return given()
                .baseUri(Urls.listsUrl+"/"+lID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"fields","all")
                .when()
                .get();
    }

    public static Response putList(String lID)
    {
        return given()
                .baseUri(Urls.listsUrl+"/"+lID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .queryParam("name","l123")
                .when()
                .put();
    }

}
