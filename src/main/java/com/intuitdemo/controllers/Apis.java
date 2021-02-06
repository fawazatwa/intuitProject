package com.intuitdemo.controllers;

import com.intuitdemo.errorHandling.CustomerNotFoundException;
import com.intuitdemo.model.Customer;
import com.intuitdemo.model.MyUser;
import com.intuitdemo.service.DataExtractingService;
import com.intuitdemo.service.LoginService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class Apis {


    @Autowired
    LoginService loginService;

    @Autowired
    DataExtractingService dataExtractingService;

    @GetMapping("/aggregation-data")
    public ResponseEntity<Customer> getAggregationData(Principal principal, @RequestParam String url, @RequestParam String id, @RequestParam String username) throws IOException, JSONException {
        if (!username.equals(principal.getName())) {
            return new ResponseEntity<Customer>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Customer customer = dataExtractingService.getCustomerRequired(id, url);
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        } catch (CustomerNotFoundException exc) {
            throw exc;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MyUser userInfo) {
        if (loginService.isExist(userInfo.getUsername())) {

            return new ResponseEntity<String>(String.format("User %s already exists!\n please choose another name", userInfo.getUsername()), HttpStatus.CONFLICT);
        }
        loginService.register(userInfo);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<MyUser>> getAggregationData() {

        return new ResponseEntity<List<MyUser>>(loginService.getAllUsers(), HttpStatus.OK);

    }

    @DeleteMapping("/remove-user")
    public ResponseEntity remove(@RequestParam String username) {

        loginService.remove(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/remove-all")
    public ResponseEntity removeAll() {

        loginService.removeAll();
        return new ResponseEntity(HttpStatus.OK);
    }

}
