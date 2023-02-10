package it.dinfo.stlab.dao;

import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;

import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;

@RequestScoped
public class SmartStationDao extends GenericDao<SmartStation> {

    public SmartStationDao() {
        super(SmartStation.class);
    }

    public List<SmartStation> findAllAuthorizedSSForAdmin(UserAccount user) {
        return this.entityManager.createQuery("SELECT DISTINCT ss FROM SmartStation ss, AdminAuthorization aa, " +
                "UserAccount u WHERE ss.municipality = aa.municipality AND aa.userAccount = :user AND (aa.expireDate >= :now OR aa.expireDate IS NULL)")
                .setParameter("user",user).setParameter("now",new Date()).getResultList();
    }

    public List<SmartStation> findAllSSForMunicipality(Municipality municipality) {
        return this.entityManager.createQuery("SELECT DISTINCT ss FROM SmartStation ss WHERE ss.municipality = :municipality").setParameter("municipality",municipality).getResultList();
    }


}