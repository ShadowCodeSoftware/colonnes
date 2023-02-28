package com.cassandra.library.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cassandra.library.models.User;
import com.cassandra.library.repositories.UserRepository;
import com.cassandra.library.services.UserService;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

                                /* ********************
                                * CREER UN UTILISATEUR *
                                ************************/  

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            try {
                return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
                              /* ********************************
                              * RECUPERER TOUS LES UTILISATEURS * 
                              * OU LES UTILISATEUR CONTENANT    *
                              *     'NAME' DANS LEURS NOM       *
                              **********************************/ 
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
      try {
        List<User> users = new ArrayList<User>();

        if (name == null)
          userRepository.findAll().forEach(users::add);
        else
          userRepository.findByNameContaining(name).forEach(users::add);

        if (users.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }   
        return new ResponseEntity<>(users, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
  
    }

                        /* ********************************
                        * RECUPERER L'UTILISATEUR AYANT   * 
                        *           POUR MAIL 'mail'      *
                        **********************************/ 
 
    @GetMapping("/users/{mail}")
    public ResponseEntity<User> geUserById(@PathVariable("mail") String mail) {
      Optional<User> userData = userRepository.findByEmail(mail);

  if (userData.isPresent()) {
    return new ResponseEntity<>(userData.get(), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
    }
                        /* ********************************
                        * METTRE A JOUR LES INFORMATIONS  *
                        * D'UN UTILISATEUR AYANT POUR MAIL*
                        *             'mail'              *
                        **********************************/ 
                 
  @PutMapping("/users/{mail}")
  public ResponseEntity<User> updateUser(@PathVariable("mail") String mail, @RequestBody User user) {
       Optional<User> userData = userRepository.findByEmail(mail);
       if (userData.isPresent()) {
          User _user = userData.get();
          _user.setEmail(user.getEmail());
          _user.setName(user.getName());
          _user.setPhoneNumber(user.getPhoneNumber());
          if(!(userRepository.existsByEmail(_user.getEmail()))){
              return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
          }
          else{
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
        } else {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
  }

                        /* ********************************
                        * SUPRIMER L'UTILISATEUR AYANT    * 
                        *           POUR MAIL 'mail'      *
                        **********************************/ 

  @DeleteMapping("/users/{mail}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("mail") String mail) {
    try {
      userRepository.deleteByEmail(mail);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
                        /*********************************
                        * SUPRIMER TOUT LES UTILISATEURS * 
                        **********************************/ 
  @DeleteMapping("/users")
  public ResponseEntity<HttpStatus> deleteAllUsers() {
    try {
      userRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
