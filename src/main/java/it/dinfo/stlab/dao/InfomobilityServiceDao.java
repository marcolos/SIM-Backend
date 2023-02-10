package it.dinfo.stlab.dao;

import it.dinfo.stlab.model.InfomobilityServiceProvider;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.SmartStation;
import it.dinfo.stlab.model.UserAccount;


import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@RequestScoped
public class InfomobilityServiceDao extends GenericDao<InfomobilityServiceProvider> {

    public InfomobilityServiceDao() {
        super(InfomobilityServiceProvider.class);
    }

    public List<String> findAvailableVehicles(String ispId, String ssId){
        String query = "SELECT DISTINCT ispmt.mobilityTypes FROM InfomobilityServiceProvider_MobilityType ispmt, SmartStation_InfomobilityServiceProvider ssisp WHERE InfomobilityServiceProvider_id = :ispId AND smart_station_id = :ssId AND InfomobilityServiceProvider_id = infomobility_service_provider_id";
        Query q = this.entityManager.createNativeQuery(query).setParameter("ispId",ispId).setParameter("ssId",ssId);
        List<String> availableVehicles = q.getResultList();
        //PROVA CON JDBC
        //this.entityManager.createQuery("SELECT DISTINCT isp.mobilityTypes FROM SmartStation ss , InfomobilityServiceProvider isp WHERE ss.infomobilityServiceProviders.id =  = ")
        return availableVehicles;
    }

    public List<InfomobilityServiceProvider> findAllAuthorizedIspForAdmin(UserAccount user) {
        return this.entityManager.createQuery("SELECT DISTINCT isp FROM InfomobilityServiceProvider isp, AdminAuthorization aa, " +
                "UserAccount u WHERE isp.id = aa.infomobilityServiceProvider.id AND aa.userAccount = :user AND (aa.expireDate >= :now OR aa.expireDate IS NULL)")
                .setParameter("user",user).setParameter("now",new Date()).getResultList();
    }

    public List<InfomobilityServiceProvider> findAllIspForMunicipalityAuthorizationForAdmin(UserAccount user, Municipality municipality) {
        return this.entityManager.createQuery("SELECT DISTINCT isp FROM InfomobilityServiceProvider isp, AdminAuthorization aa " +
                "WHERE aa.municipality = :municipality AND aa.userAccount = :user AND (aa.expireDate >= :now OR aa.expireDate IS NULL)" +
                "AND aa.infomobilityServiceProvider.id = isp.id").setParameter("municipality",municipality).setParameter("user",user)
                .setParameter("now",new Date()).getResultList();
    }

}