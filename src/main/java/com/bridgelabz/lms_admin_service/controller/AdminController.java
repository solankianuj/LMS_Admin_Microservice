package com.bridgelabz.lms_admin_service.controller;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import com.bridgelabz.lms_admin_service.services.IAdminServices;
import com.bridgelabz.lms_admin_service.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * purpose:Admin Operation Controller
 * @author Anuj Solanki
 * @date 3/09/2022
 */

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    IAdminServices adminServices;

    /**
     * purpose:Adding admin
     * @param  adminDTO
     * @return admin
     */
    @PostMapping("/addAdmin")
    public ResponseEntity<Response> addAdmin(@Valid @RequestBody AdminDTO adminDTO){
        Response response= adminServices.addAdmin(adminDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Purpose:get admin details
     * @param token
     * @return admin details
     */
    @GetMapping("/getAdmin")
    public ResponseEntity<Response> getAdmin(@RequestHeader String token){
        Response response=adminServices.getAdmin(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *  Purpose:updating admin details
     * @param token
     * @param adminDTO
     * @return admin updated admin details
     */

    @PutMapping("/updateAdmin")
    public ResponseEntity<Response> updateAdmin( @RequestHeader String token,@Valid @RequestBody AdminDTO adminDTO){
        Response response= adminServices.updateAdmin(token, adminDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     *  Purpose:deleting admin
     * @param token
     * @return deleted admin
     */
    @DeleteMapping("/deleteAdmin")
    public ResponseEntity<Response>  deleteAdmin(@RequestHeader String token){
        Response response = adminServices.deleteAdmin(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *  Purpose:generate change password link
     * @param emailId
     * @param newPwd
     * @return change password link
     */
    @GetMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword( @RequestParam String emailId,String newPwd){
        Response response = adminServices.resetPassword(emailId,newPwd);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     *  Purpose:changing password
     * @param token
     * @param newPwd
     * @return
     */
    @PutMapping("/changePassword/{newPwd}")
    public ResponseEntity<Response> changePassword( @RequestHeader String token,@PathVariable String newPwd){
        Response response = adminServices.changePassword(token,newPwd);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     *  Purpose:adding profile image to admin
     * @param token
     * @param path
     * @return
     */
    @PutMapping("/addProfile")
    public ResponseEntity<Response> addProfile(@RequestHeader String token,@RequestParam String path){
        Response  response = adminServices.addProfile(token, path);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     *  Purpose:admin login,generating token
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String email, @RequestParam String password){
        Response response= adminServices.login(email, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * purpose:validating user
     * @param token
     * @return
     */
    @GetMapping("/validatingUser/{token}")
    Boolean validatingUser(@PathVariable String token){
        return adminServices.validateUser(token);
    }

}
