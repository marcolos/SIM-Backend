package it.dinfo.stlab.controllers;

import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.dto.UserAccountDTO;
import it.dinfo.stlab.mappers.UserAccountMapper;
import it.dinfo.stlab.model.UserAccount;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Dependent //serve se non è presente il file beans.xml
public class UserAccountController {

    public UserAccountController() {}

    @Inject private UserAccountDao userAccountDao;
    @Inject private UserAccountMapper userAccountMapper;
    //@Context private SecurityContext securityContext;

    public List<UserAccountDTO> getAll(){

        List<UserAccountDTO> userAccountDTOs = new ArrayList<>();
        List<UserAccount> userAccounts = userAccountDao.findAll();

        for(int i = 0; i < userAccounts.size(); i++ ){
            UserAccountDTO dto = userAccountMapper.convert(userAccounts.get(i));
            userAccountDTOs.add(dto);
        }
        return userAccountDTOs;
    }

    public UserAccountDTO getById(String uuid){
        UserAccount userAccount = userAccountDao.findById(uuid);
        UserAccountDTO dto = userAccountMapper.convert(userAccount);
        return dto;
    }

    public String create(UserAccountDTO dtoReceived){
        UserAccount userAccount = userAccountMapper.transfer(dtoReceived,new UserAccount());
        userAccount.setId(UUID.randomUUID().toString());
        userAccountDao.save(userAccount);
        return userAccount.getId();
    }

    public void update(String uuid, UserAccountDTO dtoReceived){
        UserAccount userAccount = userAccountMapper.transfer(dtoReceived,new UserAccount());
        userAccount.setId(uuid);
        if(userAccount.getPassword() == null || userAccount.getPassword().equals(""))
            userAccount.setPassword(userAccountDao.findById(uuid).getPassword());
        userAccountDao.update(userAccount);
    }

    public void delete(String uuid){
        userAccountDao.delete(uuid);
    }


}
