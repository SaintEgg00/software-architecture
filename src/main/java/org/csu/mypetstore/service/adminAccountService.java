package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.adminAccount;
import org.csu.mypetstore.persistence.adminAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminAccountService {
    @Autowired
    private adminAccountMapper adminAccountMapper;

    public adminAccount getAdmin(String username){
        return adminAccountMapper.getAdminByUsername(username);
    }

    public adminAccount getAdmin(String username,String password){
        adminAccount adminAccount = new adminAccount();
        adminAccount.setUsername(username);
        adminAccount.setPassword(password);
        return adminAccountMapper.getAdminByUsernameAndPassword(adminAccount);
    }
    public void updateAdmin(adminAccount adminAccount){
        if(adminAccount.getPassword() != null && adminAccount.getPassword().length() > 0 &&adminAccount.getPhone() != null){
            adminAccountMapper.updateAdmin(adminAccount);
        }
    }
    public void insertAdmin(adminAccount adminAccount){
        if(adminAccount.getPassword() != null && adminAccount.getPassword().length() > 0 &&adminAccount.getPhone() != null){
            adminAccountMapper.insertAdmin(adminAccount);
        }
    }

}
