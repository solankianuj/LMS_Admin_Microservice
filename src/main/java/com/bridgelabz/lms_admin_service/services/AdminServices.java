package com.bridgelabz.lms_admin_service.services;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import com.bridgelabz.lms_admin_service.exception.AdminNotFound;
import com.bridgelabz.lms_admin_service.model.AdminModel;
import com.bridgelabz.lms_admin_service.repository.IAdminRepository;
import com.bridgelabz.lms_admin_service.services.mailService.MailServices;
import com.bridgelabz.lms_admin_service.util.Response;
import com.bridgelabz.lms_admin_service.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * purpose:creating different admin services
 * @author Anuj solanki
 * @date 3/09/2022
 */
@Service
public class AdminServices implements IAdminServices{

    @Autowired
    IAdminRepository iAdminRepository;
    @Autowired
    Token tokenutil;
    @Autowired
    MailServices mailServices;

    /**
     * purpose:Adding admin
     * @param  adminDTO
     * @return admin
     */
    @Override
    public Response addAdmin( AdminDTO adminDTO) {
        AdminModel adminModel=new AdminModel(adminDTO);
        iAdminRepository.save(adminModel);
        return new Response("Admin added successfully",200,adminModel);

    }

    /**
     * purpose:Fetching admin details
     * @param token
     * @return
     */
    @Override
    public Response getAdmin(String token) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        if (adminModel.isPresent()){
            return new Response("Getting Admin Details",200,adminModel.get());
        }
        throw new AdminNotFound(400,"Admin Not Found !");
    }

    /**
     * purpose:updating admin details
     * @param token
     * @param adminDTO
     * @return
     */
    @Override
    public Response updateAdmin(String token, AdminDTO adminDTO) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        if (adminModel.isPresent()) {
            adminModel.get().setFirstName(adminDTO.getFirstName());
            adminModel.get().setLastName(adminDTO.getLastName());
            adminModel.get().setEmailId(adminDTO.getEmailId());
            adminModel.get().setMobileNo(adminDTO.getMobileNo());
            adminModel.get().setUpdatedStamp(LocalDateTime.now());
            adminModel.get().setStatus(adminDTO.getStatus());
            adminModel.get().setProfilePath(adminDTO.getProfilePath());
            adminModel.get().setPassword(adminDTO.getPassword());
            iAdminRepository.save(adminModel.get());
            return new Response("Updating Admin Details", 200, adminModel.get());
        }
        throw new AdminNotFound(400,"Admin Not Found !");
        }


    /**
     *  Purpose:deleting admin
     * @param token
     * @return deleted admin
     */
    @Override
    public Response deleteAdmin(String token) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        iAdminRepository.delete(adminModel.get());
        return new Response("Deleting Admin ", 200, adminModel.get());
    }
    /**
     *  Purpose:generate change password link
     * @param emailId
     * @param newPwd
     * @return change password link
     */
    @Override
    public Response resetPassword(String emailId,String newPwd) {
        Optional<AdminModel> adminModel=iAdminRepository.findByEmailId(emailId);
        if (adminModel.isPresent()){
            if (adminModel.get().getEmailId().equals(emailId)){
                String token=tokenutil.createToken(adminModel.get().getId());
                String link=System.getenv("resetPwdLink");
                String URL="click here  : "+link+newPwd+"" +
                        " token-: "+token;
                String subject="Reset Password ..";
                mailServices.send(emailId,subject,URL);
                return new Response("Generating Reset Password link ", 200, URL);
            }
            else {
                throw new AdminNotFound(200,"Invalid Email ID !");

            }
        }
        return null;
    }
    /**
     *  Purpose:changing password
     * @param token
     * @param newPwd
     * @return
     */
    @Override
    public Response changePassword(String token, String newPwd) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        if (adminModel.isPresent()) {
            adminModel.get().setPassword(newPwd);
            iAdminRepository.save(adminModel.get());
            return new Response("Password Change Successfully ", 200, adminModel.get());
        }
        throw new AdminNotFound(200,"Admin Not Found !");
    }
    /**
     *  Purpose:adding profile image to admin
     * @param token
     * @param path
     * @return
     */
    @Override
    public Response addProfile(String token, String path) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        if (adminModel.isPresent()) {
            adminModel.get().setProfilePath(path);
            iAdminRepository.save(adminModel.get());
            return new Response("Profile Added Successfully ", 200, adminModel.get());

        }
        throw new AdminNotFound(200,"Admin Not Found !");
    }

    /**
     * purpose:validating user
     * @param token
     * @return
     */
    @Override
    public Boolean validateUser(String token) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        if (adminModel.isPresent()){
            return true;
        }
        throw new AdminNotFound(200,"Admin Not Found !");
    }


    /**
     *  Purpose:admin login,generating token
     * @param emailId
     * @param password
     * @return
     */
    @Override
    public Response login(String emailId, String password) {
        Optional<AdminModel> adminModel=iAdminRepository.findByEmailId(emailId);
        if (adminModel.isPresent()){
            if (adminModel.get().getPassword().equals(password)){

                String tokenobj= tokenutil.createToken(adminModel.get().getId());
                return new Response("Login Success Full",200,tokenobj);
            }
            throw new AdminNotFound(200,"Invalid Credential !");
        }
        throw new AdminNotFound(200,"Admin Not Found !");
    }
}
