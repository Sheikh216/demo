package com.samiul.demo.controller;


import com.samiul.demo.exception.UserNotFoundException;
import com.samiul.demo.model.User;
import com.samiul.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;



    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    } //newuser = {username: samir,username, email, password:123}

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();

    }


//    @GetMapping("/user/{id}")
//    public User getUserById(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//    }







//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody User loginUser) {
//        Optional<User> userOptional = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
//
//        if (userOptional.isPresent()) {
//            // User is authenticated, you can generate a JWT token or set a session here
//            return ResponseEntity.ok("Login successful!");
//
//        } else {
//            // Invalid credentials
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//
//        }
//    }
// loginuser-->username,name,password

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginUser) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
// user--> username,password
        if (userOptional.isPresent()) {
            // User is authenticated, you can generate a JWT token or set a session here
            User authenticatedUser = userOptional.get();



            authenticatedUser.setLogin(true); // Set login property to true
            userRepository.save(authenticatedUser); // Save the updated user object back to the database
            return ResponseEntity.ok(authenticatedUser); // Return the user object
        } else {
            // Invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }






//    @PostMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestBody User loginUser) {
//        Optional<User> userOptional = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setLogin(true);
//
//
//
//            // Save the updated user object back to the database
//            userRepository.save(user);
//
//            return ResponseEntity.ok(user); // Return the user object without the password
//        } else {
//            // Invalid credentials
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }


//        @PostMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestBody User loginUser) {
//        Optional<User> student = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());
//
//        if (student.isPresent()) {
//
//            userRepository.save(loginUser);
//
//            return ResponseEntity.ok(loginUser);
//
//        } else {
//            // Invalid credentials
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }
//


    //
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null); // Returns null if user not found
    }


//    @PutMapping("/users/{id}")
//    public User updateUser(@RequestBody User newUser, @PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setUsername(newUser.getUsername());
//                    user.setName(newUser.getName());
//                    user.setEmail(newUser.getEmail());
//                    // If you have other fields to update, add them here
//                    return userRepository.save(user);
//                })
//                .orElseGet(() -> {
//                    // Handle the case where the user with the given ID is not found
//                    newUser.setId(id);
//                    return userRepository.save(newUser);
//                });
//    }




    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            // User not found, return a 404 Not Found status code
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

}