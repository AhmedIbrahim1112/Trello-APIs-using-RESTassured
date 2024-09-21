package TestSuite1;

import UserData.*;
import Utilities.Boards;
import Utilities.Cards;
import Utilities.Lists;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static org.testng.Assert.assertEquals;

public class CardTest {

    public static String boardID ;
    public static String listID ;
    public static String cardID ;

    @BeforeClass
    public void createBoardForList()
    {
        Response response = Boards.createBoard(Names.boardName);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        boardID=response.path("id");
    }

    @Test(priority = 0)
    public void createList(){
        Response response = Lists.createList(Names.listName,boardID);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        listID=response.path("id");
    }

    @Test(dependsOnMethods = "createList",priority = 1)
    public void createCard(){
        Response response = Cards.createCard(Names.cardName, listID);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        cardID=response.path("id");
    }

    @Test(dependsOnMethods = "createCard",priority = 1)
    public void updateCard()
    {
        Response response = Cards.putCard(cardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("c123"));

    }

    @Test(dependsOnMethods = "updateCard",priority = 1)
    public void validGetCard()
    {
        Response response = Cards.getCard(cardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(cardID))
                .body("name", equalTo("c123"));

    }

    @Test(dependsOnMethods = "validGetCard",priority = 1)
    public void deleteCard()
    {
        Response response = Cards.delCard(cardID);
        response.
                then().log().all().assertThat().statusCode(200);
        Assert.assertEquals(response.getBody().asString(),"{\"limits\":{}}");
    }

    @Test(dependsOnMethods = "deleteCard",priority = 1)
    public void invalidGetCard()
    {
        Response response = Cards.getCard(cardID);
        response.
                then().log().all().assertThat().statusCode(404);
        Assert.assertEquals(response.getBody().asString(),"The requested resource was not found.");
    }

    @Test(dependsOnMethods = "invalidGetCard",priority = 4)
    public void deleteBoardForList()
    {
        Response response = Boards.delBoard(boardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("_value",equalTo(null));
    }


}