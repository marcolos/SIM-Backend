package it.dinfo.stlab.mappers;

import it.dinfo.stlab.dao.InfomobilityServiceDao;
import it.dinfo.stlab.dao.MunicipalityDao;
import it.dinfo.stlab.dao.UserAccountDao;
import it.dinfo.stlab.dto.AdminAuthorizationDTO;
import it.dinfo.stlab.dto.InfomobilityServiceDTOLight;
import it.dinfo.stlab.model.AdminAuthorization;

import javax.inject.Inject;


//@Dependent //serve se non è presente il file beans.xml
public class AdminAuthorizationMapper {

    @Inject
    UserAccountDao userAccountDao;
    @Inject
    MunicipalityDao municipalityDao;
    @Inject
    InfomobilityServiceDao ispDao;
    @Inject UserAccountMapper userAccountMapper;
    @Inject MunicipalityMapper municipalityMapper;

    /* conversione Entity -> DTO */
    public AdminAuthorizationDTO convert(AdminAuthorization aa){
        if(aa == null)
            return null;

        AdminAuthorizationDTO dto = new AdminAuthorizationDTO();
        dto.setId(aa.getId());
        dto.setExpire_date(aa.getExpireDate());
        dto.setUserAccount(userAccountMapper.convert(aa.getUserAccount()));
        dto.setMunicipality(municipalityMapper.convert(aa.getMunicipality()));
        dto.setIsp(new InfomobilityServiceDTOLight(aa.getInfomobilityServiceProvider()));
        /*dto.setId_admin(aa.getId());
        dto.setId_isp(aa.getInfomobilityServiceProvider().getId());
        dto.setId_municipality(aa.getMunicipality().getId());*/


        return dto;
    }

    /* conversione DTO -> Entity */
    public AdminAuthorization transfer(AdminAuthorizationDTO dto, AdminAuthorization aa){
        if(dto == null)
            return null;
        if(aa == null)
            return null;

        aa.setId(dto.getId());
        aa.setExpireDate(dto.getExpire_date());
        aa.setUserAccount(userAccountDao.findById(dto.getUserAccount().getId()));
        aa.setMunicipality(municipalityDao.findById(dto.getMunicipality().getId()));
        aa.setInfomobilityServiceProvider(ispDao.findById(dto.getIsp().getId()));

        /*aa.setUserAccount(userAccountDao.findById(dto.getId_admin()));
        aa.setInfomobilityServiceProvider(ispDao.findById(dto.getId_isp()));
        aa.setMunicipality(municipalityDao.findById(dto.getId_municipality()));*/


        return aa;
    }
}

