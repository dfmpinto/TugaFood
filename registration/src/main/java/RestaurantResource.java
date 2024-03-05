import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
public class RestaurantResource {

    @GET
    public List<Restaurant> getRestaurants() {
        return Restaurant.listAll();
    }

    @POST
    @Transactional
    public Response addRestaurant(Restaurant dto) {
        dto.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void updateRestaurant(@PathParam("id") Long id, Restaurant dto) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(id);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Restaurant with id " + id + " not found");
        }
        Restaurant entity = restaurant.get();
        entity.name = dto.name;
        entity.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteRestaurant(@PathParam("id") Long id) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(id);
        restaurant.ifPresentOrElse(Restaurant::delete, () -> {
            throw new NotFoundException("Restaurant with id " + id + " not found");
        });
    }

    @GET
    @Path("{restaurantId}/dishes")
    @Tag(name = "dish")
    public List<Restaurant> getDishesFromRestaurant(@PathParam("restaurantId") Long restaurantId) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " not found");
        }
        return Dish.list("restaurant", restaurant.get());
    }

    @POST
    @Path("{restaurantId}/dishes")
    @Transactional
    @Tag(name = "dish")
    public Response addDishToRestaurant(@PathParam("restaurantId") Long restaurantId, Dish dto) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " not found");
        }
        dto.restaurant = restaurant.get();
        dto.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{restaurantId}/dishes/{id}")
    @Transactional
    @Tag(name = "dish")
    public void updateDishFromRestaurant(@PathParam("restaurantId") Long restaurantId, @PathParam("id") Long id, Dish dto) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " not found");
        }
        Optional<Dish> dish = Dish.findByIdOptional(id);
        if (dish.isEmpty()) {
            throw new NotFoundException("Dish with id " + id + " not found");
        }
        Dish entity = dish.get();
        entity.price = dto.price;
        entity.persist();
    }

    @DELETE
    @Path("{restaurantId}/dishes/{id}")
    @Transactional
    @Tag(name = "dish")
    public void deleteDishFromRestaurant(@PathParam("restaurantId") Long restaurantId, @PathParam("id") Long id) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Restaurant with id " + restaurantId + " not found");
        }
        Optional<Dish> dish = Dish.findByIdOptional(id);
        dish.ifPresentOrElse(Dish::delete, () -> {
            throw new NotFoundException("Dish with id " + id + " not found");
        });
    }
}
