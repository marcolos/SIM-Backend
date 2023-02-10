package it.dinfo.stlab.mappers;

import it.dinfo.stlab.dao.MunicipalityDao;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.UserAccount;
import it.dinfo.stlab.dto.UserAccountDTO;
import it.dinfo.stlab.model.UserRole;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;


//@Dependent //serve se non è presente il file beans.xml
public class UserAccountMapper {

    //@Inject MunicipalityDao municipalityDao;
    //@Inject MunicipalityMapper municipalityMapper;

    public UserAccountMapper(){}

    /* conversione Entity -> DTO */
    public UserAccountDTO convert(UserAccount u){
        if(u == null)
            return null;

        UserAccountDTO dto = new UserAccountDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setSurname(u.getSurname());
        dto.setEmail(u.getEmail());
        dto.setUserRole(u.getUserRole().name());
        //dto.setMunicipality(municipalityMapper.convert(u.getMunicipality()));

        return dto;
    }

    /* conversione DTO -> Entity */
    public UserAccount transfer(UserAccountDTO dto, UserAccount u){
        if(dto == null)
            return null;
        if(u == null)
            return null;

        u.setId(dto.getId());
        u.setName(dto.getName());
        u.setSurname(dto.getSurname());
        u.setEmail(dto.getEmail());
        u.setUserRole(UserRole.valueOf(dto.getUserRole()));
        u.setPassword(dto.getPassword());
        //Municipality m = municipalityDao.findById(dto.getMunicipality().getId());
        //u.setMunicipality(m);

        return u;
    }
}
