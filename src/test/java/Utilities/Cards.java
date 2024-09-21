package Utilities;

import UserData.Urls;
import UserData.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class Cards {

    public static Response createCard(String cardName,String lID)
    {

        return given()
                .baseUri(Urls.cardsUrl)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"name",cardName,"idList",lID)
                .when()
                .post();
    }

    public static Response getCard(String cID)
    {
        return given()
                .baseUri(Urls.cardsUrl+"/"+cID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"fields","all")
                .when()
                .get();
    }

    public static Response putCard(String cID)
    {
        return given()
                .baseUri(Urls.cardsUrl+"/"+cID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .queryParam("name","c123")
                .when()
                .put();
    }

    public static Response delCard(String cID)
    {
        return given()
                .baseUri(Urls.cardsUrl+"/"+cID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .when()
                .delete();
    }
}
