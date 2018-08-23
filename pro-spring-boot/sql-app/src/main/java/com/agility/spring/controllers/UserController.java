package com.agility.spring.controllers;

import com.agility.spring.models.User;
import com.agility.spring.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @RequestMapping("/create")
  @ResponseBody
  public User create(String email, String name) {
    try {
      User user = new User(email, name);
      userRepository.save(user);
      return user;
    } catch (Exception ex) {
      return null;
    }
  }

  @RequestMapping("/delete")
  @ResponseBody
  public String delete(long id) {
    try {
      User user = new User(id);
      userRepository.delete(user);
    } catch (Exception ex) {
      return "Error deleting the user: " + ex.toString();
    }
    return "User succesfully deleted";
  }

  @RequestMapping("/get-by-email")
  @ResponseBody
  public String getByEmail(String email) {
    String userId;
    try {
      User user = userRepository.findByEmail(email);
      userId = String.valueOf(user.getId());
    } catch (Exception ex) {
      return "User not found";
    }
    return "The user id is: " + userId;
  }

  @RequestMapping("/update")
  @ResponseBody
  public String updateUser(long id, String email, String name) {
    try {
      User user = userRepository.findById(id).orElse(null);
      user.setEmail(email);
      user.setLastName(name);
      userRepository.save(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  }
}
