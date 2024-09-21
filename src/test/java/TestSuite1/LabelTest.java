package TestSuite1;

import UserData.*;
import Utilities.Boards;
import Utilities.Label;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static org.testng.Assert.assertEquals;

public class LabelTest {

    public static String boardID ;
    public static String labelID ;

    @BeforeClass
    public void createBoardForLabel()
    {
        Response response = Boards.createBoard(Names.boardName);
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        boardID=response.path("id");
    }




    @Test(priority = 0)
    public void createLabel(){
        Response response = Label.createLabel(Names.labelName,boardID,"yellow");
        response.then().log().all();
        assertEquals(response.getStatusCode(),200);
        labelID=response.path("id");
    }

    @Test(dependsOnMethods = "createLabel",priority = 1)
    public void updateLabel()
    {
        Response response = Label.putLabel(labelID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("name", equalTo("lbl123"));

    }

    @Test(dependsOnMethods = "updateLabel",priority = 1)
    public void validGetLabel()
    {
        Response response = Label.getLabel(labelID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("id", equalTo(labelID));

    }

    @Test(dependsOnMethods = "validGetLabel",priority = 1)
    public void deleteLabel()
    {
        Response response = Label.delLabel(labelID);
        response.
                then().log().all().assertThat().statusCode(200);
        Assert.assertEquals(response.getBody().asString(),"{\"limits\":{}}");

    }

    @Test(dependsOnMethods = "deleteLabel",priority = 4)
    public void deleteBoardForLabel()
    {
        Response response = Boards.delBoard(boardID);
        response.
                then().log().all().assertThat().statusCode(200)
                .body("_value",equalTo(null));
    }

    @Test(dependsOnMethods = "deleteLabel",priority = 4)
    public void invalidGetLabel()
    {
        Response response = Label.getLabel(labelID);
        response.
                then().log().all().assertThat().statusCode(404);
        Assert.assertEquals(response.getBody().asString(),"The requested resource was not found.");
    }
}