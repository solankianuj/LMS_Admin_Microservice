package com.bridgelabz.lms_admin_service.services;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import com.bridgelabz.lms_admin_service.model.AdminModel;
import com.bridgelabz.lms_admin_service.util.Response;

public interface IAdminServices {
    Response addAdmin(AdminDTO adminDTO);
    Response getAdmin(String token);
    Response updateAdmin(String token,AdminDTO adminDTO);

    Response deleteAdmin(String token);

    Response resetPassword(String emailId,String newPwd );
    Response changePassword(String token,String newPwd);
    Response addProfile(String token,String path);
    Boolean validateUser(String token);
    Response login(String emailId, String password);
}
