package TestSuite1;

import UserData.*;
import Utilities.Boards;
import Utilities.Lists;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static org.testng.Assert.assertEquals;

public class ListTest {

    public static String boardID ;
    public static String listID ;

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
    public void updateList()
    {
        Response response = Lists.putList(listID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("l123"));

    }

    @Test(dependsOnMethods = "updateList",priority = 1)
    public void validGetList()
    {
        Response response = Lists.getList(listID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(listID));

    }

    @Test(dependsOnMethods = "validGetList",priority = 4)
    public void deleteBoardForList()
    {
        Response response = Boards.delBoard(boardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("_value",equalTo(null));
    }

    @Test(dependsOnMethods = "deleteBoardForList",priority = 4)
    public void invalidGetList()
    {
        Response response = Lists.putList(listID);
        response.
                then().log().all().assertThat().statusCode(404);
        Assert.assertEquals(response.getBody().asString(),"model not found");
    }
}