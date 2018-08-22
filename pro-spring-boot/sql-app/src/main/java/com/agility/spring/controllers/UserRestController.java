package com.agility.spring.controllers;

import com.agility.spring.models.User;
import com.agility.spring.models.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

  @Autowired
  private UserDAO userDAO;

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public User create(@RequestParam("email") String email,
                       @RequestParam("name") String name) {
    User user;
    try {
      user = new User(email, name);
      userDAO.save(user);
    } catch (Exception ex) {
      return null;
    }
    return user;
  }

  @RequestMapping(value ="/{userId}", method = RequestMethod.GET)
  public User getUser(@PathVariable("userId") long id) {
    try {
      User user = userDAO.findById(id).orElse(null);
      return user;
    } catch (Exception ex) {
      return null;
    }
  }

  @RequestMapping(value ="/{userId}", method = RequestMethod.GET,
  consumes = "application/json;version=2")
  public String getUserV2(@PathVariable("userId") long id) {
    try {
      User user = userDAO.findById(id).orElse(null);
      return "User have email: " + user.getEmail();
    } catch (Exception ex) {
      return null;
    }
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public List<User> getAll() {
    List<User> users = new ArrayList<>();
    userDAO.findAll().forEach(users::add);
    return users;
  }

}
