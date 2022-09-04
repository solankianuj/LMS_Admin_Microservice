package com.bridgelabz.lms_admin_service.model;

import com.bridgelabz.lms_admin_service.dto.AdminDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * purpose:creating admin model
 * @author Anuj Solanki
 */
@Entity
@Table(name = "AdminData")
@Data
@AllArgsConstructor
public class AdminModel {
    @Id
    @GenericGenerator(name = "AdminData",strategy = "increment")
    @GeneratedValue(generator = "AdminData")
    private long id;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emailId;
    private String password;
    private String profilePath;
    private String status;
    private LocalDateTime creatorStamp =LocalDateTime.now();
    private LocalDateTime updatedStamp;

    public AdminModel(AdminDTO adminDTO) {
        this.firstName=adminDTO.getFirstName();
        this.lastName=adminDTO.getLastName();
        this.mobileNo=adminDTO.getMobileNo();
        this.emailId=adminDTO.getEmailId();
        this.profilePath=adminDTO.getProfilePath();
        this.status=adminDTO.getStatus();
        this.password= adminDTO.getPassword();
    }

    public AdminModel() {

    }
}
