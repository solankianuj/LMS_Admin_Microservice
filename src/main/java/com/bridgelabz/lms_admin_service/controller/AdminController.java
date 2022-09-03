package com.bridgelabz.lms_admin_service.controller;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import com.bridgelabz.lms_admin_service.model.AdminModel;
import com.bridgelabz.lms_admin_service.services.IAdminServices;
import com.bridgelabz.lms_admin_service.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * purpose:Admin Operation Controller
 * @author Anuj Solanki
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
    public AdminModel addAdmin(@Valid @RequestBody AdminDTO adminDTO){
        return adminServices.addAdmin(adminDTO);
    }

    /**
     * Purpose:get admin details
     * @param token
     * @return admin details
     */
    @GetMapping("/getAdmin")
    public AdminModel getAdmin(@RequestHeader String token){
      return   adminServices.getAdmin(token);
    }

    /**
     *  Purpose:updating admin details
     * @param token
     * @param adminDTO
     * @return admin updated admin details
     */

    @PutMapping("/updateAdmin")
    public AdminModel updateAdmin( @RequestHeader String token,@Valid @RequestBody AdminDTO adminDTO){
        return adminServices.updateAdmin(token, adminDTO);
    }

    /**
     *  Purpose:deleting admin
     * @param token
     * @return deleted admin
     */
    @DeleteMapping("/deleteAdmin")
    public AdminModel deleteAdmin(@RequestHeader String token){
        return adminServices.deleteAdmin(token);
    }

    /**
     *  Purpose:generate change password link
     * @param emailId
     * @param newPwd
     * @return change password link
     */
    @GetMapping("/resetPassword")
    public String resetPassword( @RequestParam String emailId,String newPwd){
        return adminServices.resetPassword(emailId,newPwd);
    }

    /**
     *  Purpose:changing password
     * @param token
     * @param newPwd
     * @return
     */
    @PutMapping("/changePassword/{newPwd}")
    public AdminModel changePassword( @RequestHeader String token,@PathVariable String newPwd){
        return adminServices.changePassword(token,newPwd);
    }

    /**
     *  Purpose:adding profile image to admin
     * @param token
     * @param path
     * @return
     */
    @PutMapping("/addProfile")
    public AdminModel addProfile(@RequestHeader String token,@RequestParam String path){
        return adminServices.addProfile(token, path);
    }

    /**
     *  Purpose:admin login,generating token
     * @param email
     * @param password
     * @return
     */
    @GetMapping("/login")
    public Response login(@RequestParam String email, @RequestParam String password){
        return adminServices.login(email, password);
    }

}
