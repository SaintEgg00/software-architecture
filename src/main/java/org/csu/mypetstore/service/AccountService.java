package org.csu.mypetstore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;
    public PageInfo<Account> getAccountList(int page, int limit){
        PageHelper.startPage(page,limit);
        List<Account> accountList = accountMapper.getAccountList();
        PageInfo<Account> pageInfo = new PageInfo<Account>(accountList);
        return pageInfo;
    }

    public Account getAccount(String username){
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password){
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    /*
        声明式事务处理
     */
    @Transactional
    public void insertAccount(Account account){
        accountMapper.insertAccount(account);
        accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    public void updateAccount(Account account){
        accountMapper.updateAccount(account);
        accountMapper.updateProfile(account);

        if(account.getPassword() != null && account.getPassword().length() > 0){
            accountMapper.updateSignon(account);
        }
    }
    public void updateSignon(Account account){
        accountMapper.updateSignon(account);
    }

    @Transactional
    public void deleteAccount(String id) {
        accountMapper.deleteAccount(id);   //删除account信息
        accountMapper.deleteProfile(id);    //删除profile信息
        accountMapper.deleteSignon(id);     //删除signon信息
    }
    @Transactional
    public void deleteAccountList(String[] ids) {
        accountMapper.deleteAccountList(ids);   //删除account信息
        accountMapper.deleteProfileList(ids);    //删除profile信息
        accountMapper.deleteSignonList(ids);     //删除signon信息
    }
}