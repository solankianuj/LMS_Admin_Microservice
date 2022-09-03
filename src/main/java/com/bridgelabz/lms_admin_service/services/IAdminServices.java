package com.bridgelabz.lms_admin_service.services;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import com.bridgelabz.lms_admin_service.model.AdminModel;
import com.bridgelabz.lms_admin_service.util.Response;

public interface IAdminServices {
    AdminModel addAdmin(AdminDTO adminDTO);
    AdminModel getAdmin(String token);
    AdminModel updateAdmin(String token,AdminDTO adminDTO);

    AdminModel deleteAdmin(String token);

    String resetPassword(String emailId,String newPwd );
    AdminModel changePassword(String token,String newPwd);
    AdminModel addProfile(String token,String path);
    Response login(String emailId, String password);
}
