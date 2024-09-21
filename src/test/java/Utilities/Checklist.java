package Utilities;

import UserData.Urls;
import UserData.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class Checklist {

    public static Response createChecklist(String checklistName,String cID)
    {

        return given()
                .baseUri(Urls.checklistUrl)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"name",checklistName,"idCard",cID)
                .when()
                .post();
    }

    public static Response getChecklist(String chID)
    {
        return given()
                .baseUri(Urls.checklistUrl+"/"+chID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .when()
                .get();
    }

    public static Response putChecklist(String chID)
    {
        return given()
                .baseUri(Urls.checklistUrl+"/"+chID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .queryParam("name","ch123")
                .when()
                .put();
    }

    public static Response delChecklist(String chID)
    {
        return given()
                .baseUri(Urls.checklistUrl+"/"+chID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .when()
                .delete();
    }
}
