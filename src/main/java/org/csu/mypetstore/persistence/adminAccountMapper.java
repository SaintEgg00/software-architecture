package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.adminAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface adminAccountMapper {
    adminAccount getAdminByUsername(String username);
    
    adminAccount getAdminByUsernameAndPassword(adminAccount adminAccount);

    void updateAdmin(adminAccount adminAccount);

    void insertAdmin(adminAccount adminAccount);
}
