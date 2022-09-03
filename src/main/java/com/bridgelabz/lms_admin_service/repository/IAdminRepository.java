package com.bridgelabz.lms_admin_service.repository;

import com.bridgelabz.lms_admin_service.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * purpose:creating admin repository
 */
@Repository
public interface IAdminRepository extends JpaRepository<AdminModel,Long> {
    Optional<AdminModel> findByEmailId(String emailId);
}
