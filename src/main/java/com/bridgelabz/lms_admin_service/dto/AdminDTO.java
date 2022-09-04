package com.bridgelabz.lms_admin_service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *  Purpose:creating admin dto and adding validation on admin dto
 * @author Anuj Solanki
 */
@Data
public class AdminDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-z]{2,}$", message = " First Name is invalid")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-z]{2,}$", message = " Last Name is invalid")
    private String lastName;
    @Pattern(regexp = "^[6-9]{1}[0-9]{9}$",message = "Mobile Number is Invalid !")
    private String mobileNo;
    //    @Pattern(regexp = "^[a-z]{1}[@]{1}[a-z 0-9]{2,}[.]{1}[a-z 0-9]{3,}",message = "invalid")
    private String emailId;
    @NotNull(message = "password can not be Null")
    private String password;
    @NotNull(message = "Profile Path Can't be Null.")
    private String profilePath;
    @NotNull(message = "Status Can't be Null.")
    private String status;

}
