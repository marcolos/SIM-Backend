package it.dinfo.stlab.dao;

import it.dinfo.stlab.model.InfomobilityServiceProvider;
import it.dinfo.stlab.model.Municipality;
import it.dinfo.stlab.model.UserAccount;

import javax.enterprise.context.RequestScoped;
import java.util.Date;
import java.util.List;


@RequestScoped
public class MunicipalityDao extends GenericDao<Municipality> {

    public MunicipalityDao(){
        super(Municipality.class);
    }

    public Municipality findByName(String name){
        return (Municipality) this.entityManager.createQuery("SELECT m FROM Municipality m WHERE m.name = :name")
                .setParameter("name", name).getSingleResult();
    }

    // ritorno tutte le municipality alle quali un admin è autorizzato per una isp. Ad esempio un admin puo puo essere autorizzato all'isp Car2Go sia su Milano e su Firenze
    public List<Municipality> findAllMunicipalityForAdminAuth(UserAccount user, InfomobilityServiceProvider isp){
        return this.entityManager.createQuery("SELECT DISTINCT m FROM Municipality m, AdminAuthorization a " +
                "WHERE a.userAccount = :user AND a.infomobilityServiceProvider = :isp AND a.municipality.id = m.id AND (a.expireDate >= :now OR a.expireDate IS NULL)")
                .setParameter("user",user).setParameter("isp",isp).setParameter("now",new Date()).getResultList();
    }

    public List<Municipality> findAllForAdmin(UserAccount user){
        return this.entityManager.createQuery("SELECT DISTINCT m FROM Municipality m, AdminAuthorization aa WHERE " +
                "m.id = aa.municipality.id AND aa.userAccount = :user AND (aa.expireDate >= :now OR aa.expireDate IS NULL)")
                .setParameter("user",user).setParameter("now",new Date()).getResultList();
    }
}
