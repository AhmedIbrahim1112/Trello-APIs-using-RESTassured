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

public class ChecklistTest {

    public static String boardID ;
    public static String listID ;
    public static String cardID ;
    public static String checklistID ;

    @BeforeClass
    public void createBoardForList()
    {
        Response response = Boards.createBoard(Names.boardName);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        boardID=response.path("id");
    }

    @Test(priority = 1)
    public void createListForCard(){
        Response response = Lists.createList(Names.listName,boardID);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        listID=response.path("id");
    }

    @Test(priority = 2)
    public void createCardForChecklist(){
        Response response = Cards.createCard(Names.cardName, listID);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        cardID=response.path("id");
    }

    @Test(priority = 3)
    public void createChecklist(){
        Response response = Checklist.createChecklist(Names.checklistName, cardID);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        checklistID=response.path("id");
    }

    @Test(dependsOnMethods = "createChecklist",priority = 1)
    public void updateChecklist()
    {
        Response response = Checklist.putChecklist(checklistID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("ch123"));

    }

    @Test(dependsOnMethods = "createChecklist",priority = 2)
    public void validGetChecklist()
    {
        Response response = Checklist.getChecklist(checklistID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(checklistID))
                .body("name", equalTo("ch123"));

    }

    @Test(dependsOnMethods = "createChecklist",priority = 3)
    public void deleteChecklist()
    {
        Response response = Checklist.delChecklist(checklistID);
        response.
                then().log().all().assertThat().statusCode(200);
        Assert.assertEquals(response.getBody().asString(),"{\"limits\":{}}");
    }


    @Test(dependsOnMethods = "deleteChecklist",priority = 1)
    public void invalidGetChecklist()
    {
        Response response = Checklist.getChecklist(checklistID);
        response.
                then().log().all().assertThat().statusCode(404);
        Assert.assertEquals(response.getBody().asString(),"The requested resource was not found.");
    }



    @AfterClass
    public void deleteBoardForList()
    {
        Response response = Boards.delBoard(boardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("_value",equalTo(null));
    }


}