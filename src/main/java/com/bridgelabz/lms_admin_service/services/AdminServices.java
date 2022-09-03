package com.bridgelabz.lms_admin_service.services;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import com.bridgelabz.lms_admin_service.exception.AdminNotFound;
import com.bridgelabz.lms_admin_service.model.AdminModel;
import com.bridgelabz.lms_admin_service.repository.IAdminRepository;
import com.bridgelabz.lms_admin_service.util.Response;
import com.bridgelabz.lms_admin_service.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * purpose:creating different admin services
 */
@Service
public class AdminServices implements IAdminServices{

    @Autowired
    IAdminRepository iAdminRepository;
    @Autowired
    Token tokenutil;
//    @Autowired
//    MailServices mailServices;

    /**
     * purpose:Adding admin
     * @param  adminDTO
     * @return admin
     */
    @Override
    public AdminModel addAdmin(AdminDTO adminDTO) {
        AdminModel adminModel=new AdminModel(adminDTO);
        iAdminRepository.save(adminModel);
        return adminModel;
    }

    /**
     * Purpose:get admin details
     * @param token
     * @return admin details
     */
    @Override
    public AdminModel getAdmin(String token) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        if (adminModel.isPresent()){
            return adminModel.get();
        }
        throw new AdminNotFound(200,"Admin Not Found !");
    }

    /**
     *  Purpose:updating admin details
     * @param token
     * @param adminDTO
     * @return admin updated admin details
     */

    @Override
    public AdminModel updateAdmin(String token, AdminDTO adminDTO) {
        AdminModel adminModel=this.getAdmin(token);
        adminModel.setFirstName(adminDTO.getFirstName());
        adminModel.setLastName(adminDTO.getLastName());
        adminModel.setEmailId(adminDTO.getEmailId());
        adminModel.setMobileNo(adminDTO.getMobileNo());
        adminModel.setUpdatedStamp(LocalDateTime.now());
        adminModel.setStatus(adminDTO.getStatus());
        adminModel.setProfilePath(adminDTO.getProfilePath());
        adminModel.setPassword(adminDTO.getPassword());
        iAdminRepository.save(adminModel);
        return adminModel;
    }
    /**
     *  Purpose:deleting admin
     * @param token
     * @return deleted admin
     */
    @Override
    public AdminModel deleteAdmin(String token) {
        Long id= tokenutil.decodeToken(token);
        Optional<AdminModel> adminModel=iAdminRepository.findById(id);
        iAdminRepository.delete(adminModel.get());
        return adminModel.get();
    }
    /**
     *  Purpose:generate change password link
     * @param emailId
     * @param newPwd
     * @return change password link
     */
    @Override
    public String  resetPassword(String emailId,String newPwd) {
        Optional<AdminModel> adminModel=iAdminRepository.findByEmailId(emailId);
        if (adminModel.isPresent()){
            if (adminModel.get().getEmailId().equals(emailId)){
                String token=tokenutil.createToken(adminModel.get().getId());
                String link=System.getenv("resetPwdLink");
                String URL="click here "+link+newPwd+"/"+token;
                String subject="Reset Password ..";
//                mailServices.send(emailId,subject,URL);
            }
        }
        else {
            throw new AdminNotFound(200,"Invalid Email ID !");
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
    public AdminModel changePassword(String token, String newPwd) {
        AdminModel adminModel=this.getAdmin(token);
        adminModel.setPassword(newPwd);
        iAdminRepository.save(adminModel);
        return adminModel;
    }
    /**
     *  Purpose:adding profile image to admin
     * @param token
     * @param path
     * @return
     */
    @Override
    public AdminModel addProfile(String token, String path) {
        AdminModel adminModel=this.getAdmin(token);
        adminModel.setProfilePath(path);
        iAdminRepository.save(adminModel);
        return adminModel;
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
