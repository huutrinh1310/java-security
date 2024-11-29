package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.model.responses.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest(classes = SareetaApplication.class)
public class SareetaApplicationTests {
    @Autowired
    private CartController cartController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private OrderController orderController;

    @Autowired
    private UserController userController;

    private ModifyCartRequest createModifyCartRequest(String username, Long itemId, Integer quantity) {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername(username);
        modifyCartRequest.setItemId(itemId);
        modifyCartRequest.setQuantity(quantity);
        return modifyCartRequest;
    }

    public User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Username");
        user.setPassword("Password");

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        user.setCart(cart);


        return user;
    }

    public Item createItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Created Item");
        item.setDescription("This is fake item.");
        item.setPrice(BigDecimal.valueOf(10.0));
        return item;
    }

    //============================= Cart Controller ===================
    @Test
    public void testAddToCartNoUserError() {
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("", 1L, 2);
        ResponseEntity<Cart> responseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testRemoveFromCartNoUserError() {
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("", 1L, 2);
        ResponseEntity<Cart> responseEntity = cartController.removeFromCart(modifyCartRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testAddToCartNoItemError() {
        User user = createUser();


        ModifyCartRequest modifyCartRequest = createModifyCartRequest(user.getUsername(), 1L, 1);
        ResponseEntity<Cart> responseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertNotNull(responseEntity);

        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testAddToCartTest() {
        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);


        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1L, 1);
        ResponseEntity<Cart> responseEntity = cartController.addToCart(modifyCartRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        Cart responseCart = responseEntity.getBody();

        Assertions.assertNotNull(responseCart);

        List<Item> items = responseCart.getItems();
        Assertions.assertNotNull(items);

        Assertions.assertEquals("Username", responseCart.getUser().getUsername());
    }

    @Test
    public void testRemoveFromCartNoItemError() {

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1L, 1);
        ResponseEntity<Cart> responseEntity = cartController.removeFromCart(modifyCartRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testRemoveFromCartTest() {

        User user = createUser();
        Item item = createItem();
        Cart cart = user.getCart();
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);

        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Username", 1L, 1);
        ResponseEntity<Cart> responseEntity = cartController.removeFromCart(modifyCartRequest);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        Cart responseCart = responseEntity.getBody();

        Assertions.assertNotNull(responseCart);
        List<Item> items = responseCart.getItems();
        Assertions.assertNotNull(items);
        Assertions.assertEquals(0, items.size());
        Assertions.assertEquals("Username", responseCart.getUser().getUsername());


    }

    //    ============================== Item Controller ===================
    @Test
    public void getItemByIdTest() {

        ResponseEntity<Item> response = itemController.getItemById(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Item item = response.getBody();
        Assertions.assertNotNull(item);
    }

    @Test
    public void getItemsTest() {
        ResponseEntity<List<Item>> response = itemController.getItems();

        List<Item> itemList = response.getBody();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(itemList);
    }

    @Test
    public void getItemByNameTest() {
        List<Item> items = new ArrayList<>();

        items.add(createItem());
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Created Item");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(items, response.getBody());
    }


    //   ============================== User Controller ===================
    @Test
    public void createUserTest() {
        CreateUserRequest user = new CreateUserRequest();
        user.setUsername("Username");
        user.setPassword("Password");

        ResponseEntity<UserDTO> response = userController.createUser(user);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }

    @Test
    public void createUserTestError() {
        CreateUserRequest user = new CreateUserRequest();
        user.setUsername("Username");
        user.setPassword("Pard");

        ResponseEntity<UserDTO> response = userController.createUser(user);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(user, response.getBody());
    }


}
