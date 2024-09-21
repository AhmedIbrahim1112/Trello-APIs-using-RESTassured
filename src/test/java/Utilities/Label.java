package Utilities;

import UserData.Urls;
import UserData.*;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;

public class Label {

    public static Response createLabel(String labelName,String bID,String color)
    {
        return given()
                .baseUri(Urls.labelsUrl)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"name",labelName,"idBoard",bID,"color",color)
                .when()
                .post();
    }

    public static Response getLabel(String laID)
    {
        return given()
                .baseUri(Urls.labelsUrl+"/"+laID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token,"fields","all")
                .when()
                .get();
    }

    public static Response putLabel(String laID)
    {
        return given()
                .baseUri(Urls.labelsUrl+"/"+laID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .queryParam("name","lbl123")
                .when()
                .put();
    }

    public static Response delLabel(String laID)
    {
        return given()
                .baseUri(Urls.labelsUrl+"/"+laID)
                .header("Content-Type","application/json")
                .queryParams("key",User.key,"token",User.token)
                .when()
                .delete();
    }

}
