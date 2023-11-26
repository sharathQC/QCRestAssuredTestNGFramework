package qa.booker.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class GetBookingID {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    //String access_token = "BQC6yXj2o84Ag2VXXCAGs3Zl08lHAdyjVSmWadQUZ2vlKqzs-33kRvuKDWkwBiPzh3DCjG6k5d_fJ4h5wTFT-F-9C2g1WDuu1YJc7GE41OduqF_RiVaEdHmN53aBT3bfduer0XNbAZVRma7tToedDrsXFbWRVFfZu9bNMiBDSVfbtLApepmGHFUuVqzWHzNVeqeFQ_GUbzeCe8pSQu-w9P_E-Mu7J4FNjiZHNFbypm-_JgvD6h-ZtkLaXHIGUpXI1b05GBec_qe4Djag";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://restful-booker.herokuapp.com").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getBookingIDDetails(){
        given(requestSpecification).
                when().get("/booking/2").
                then().spec(responseSpecification).
                assertThat().statusCode(200);
    }


}
