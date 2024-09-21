package TestSuite1;

import UserData.*;
import Utilities.Boards;
import Utilities.Cards;
import Utilities.Checklist;
import Utilities.Lists;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static org.testng.Assert.assertEquals;

public class FullPath {
    public static String boardID ;
    public static String listID ;
    public static String cardID ;
    public static String checklistID ;

    @BeforeClass
    public void createEnv(){
        Response bResponse = Boards.createBoard(Names.boardName);
        bResponse.then().log().all();
        assertEquals(bResponse.getStatusCode(),200);
        boardID=bResponse.path("id");

        Response lResponse = Lists.createList(Names.listName,boardID);
        lResponse.then().log().all();
        assertEquals(lResponse.getStatusCode(),200);
        listID=lResponse.path("id");

        Response cResponse = Cards.createCard(Names.cardName, listID);
        cResponse.then().log().all();
        assertEquals(cResponse.getStatusCode(),200);
        cardID=cResponse.path("id");

        Response chResponse = Checklist.createChecklist(Names.checklistName, cardID);
        chResponse.then().log().all();
        assertEquals(chResponse.getStatusCode(),200);
        checklistID=chResponse.path("id");

    }

    @Test(priority = 0)
    public void updateBoard()
    {
        Response response = Boards.putBoard(boardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("desc", equalTo("The board is updated"));

    }

    @Test(priority = 1)
    public void validGetBoard()
    {
        Response response = Boards.getBoard(boardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(boardID)).body("name",equalTo(Names.boardName));

    }


    @Test(priority = 0)
    public void updateList()
    {
        Response response = Lists.putList(listID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("l123"));

    }

    @Test(priority = 1)
    public void validGetList()
    {
        Response response = Lists.getList(listID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(listID));

    }

    @Test(priority = 0)
    public void updateCard()
    {
        Response response = Cards.putCard(cardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("c123"));

    }

    @Test(priority = 1)
    public void validGetCard()
    {
        Response response = Cards.getCard(cardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(cardID))
                .body("name", equalTo("c123"));

    }

    @Test(priority = 0)
    public void updateChecklist()
    {
        Response response = Checklist.putChecklist(checklistID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("ch123"));

    }

    @Test(priority = 1)
    public void validGetChecklist()
    {
        Response response = Checklist.getChecklist(checklistID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(checklistID))
                .body("name", equalTo("ch123"));

    }




    @AfterClass
    public void deleteBoard()
    {
        Response chResponse = Checklist.delChecklist(checklistID);
        chResponse.
                then().log().all().assertThat().statusCode(200);
        Assert.assertEquals(chResponse.getBody().asString(),"{\"limits\":{}}");

        Response cResponse = Cards.delCard(cardID);
        cResponse.
                then().log().all().assertThat().statusCode(200);
        Assert.assertEquals(cResponse.getBody().asString(),"{\"limits\":{}}");

        Response bResponse = Boards.delBoard(boardID);
        bResponse.
                then().log().all().assertThat().statusCode(200)
                .body("_value",equalTo(null));

    }


}