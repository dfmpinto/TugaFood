import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTestResource(RegistrationTestLifecycleManager.class)
public class RestaurantResourceIT {

    private RequestSpecification given(){
        return RestAssured.given().contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    public void testGetRestaurants() {
        given().when().get("/restaurants").then().statusCode(200);

    }

    @Test
    @Order(2)
    public void testAddRestaurant() {
        Restaurant restaurantDTO = new Restaurant("Restaurant", "Owner", "VatNumber");
        given()
                .with()
                .body(restaurantDTO)
                .when().post("/restaurants")
                .then().statusCode(201);
    }

    @Test
    @Order(3)
    public void testUpdateRestaurant() {
        Restaurant restaurantDTO = new Restaurant();
        restaurantDTO.name = "New Restaurant";
        Long parameterValue = 1L;
        given()
                .with()
                .pathParam("id", parameterValue)
                .body(restaurantDTO)
                .when().put("/restaurants/{id}")
                .then().statusCode(204);

        Restaurant findById = Restaurant.findById(parameterValue);
        Assertions.assertEquals(restaurantDTO.name, findById.name);
    }

    @Test
    @Order(4)
    public void testDeleteRestaurant() {
        Long parameterValue = 1L;
        given()
                .with()
                .pathParam("id", parameterValue)
                .when().delete("/restaurants/{id}")
                .then().statusCode(204);

        Restaurant findById = Restaurant.findById(parameterValue);
        Assertions.assertNull(findById);
    }
}