package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    List<Account> getAccountList();

    Account getAccountByUsername(String username);

    Account getAccountByUsernameAndPassword(Account account);

    void insertAccount(Account account);

    void insertProfile(Account account);

    void insertSignon(Account account);

    void updateAccount(Account account);

    void updateProfile(Account account);

    void updateSignon(Account account);

    void deleteAccount(String id);
    void deleteSignon(String id);
    void deleteProfile(String id);

    void deleteAccountList(String[] ids);
    void deleteSignonList(String[] ids);
    void deleteProfileList(String[] ids);

}