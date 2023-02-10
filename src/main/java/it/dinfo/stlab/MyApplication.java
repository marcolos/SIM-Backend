/*
package it.dinfo.stlab;

import it.dinfo.stlab.model.*;

import javax.annotation.PostConstruct;
import javax.persistence.*;

import javax.ejb.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


@Startup
@Singleton
public class MyApplication {
    @PostConstruct
    public void init() throws ParseException {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sim-backend");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // MUNICIPALITY
        Municipality m1 = new Municipality();
        m1.setId(UUID.randomUUID().toString());
        m1.setName("Firenze");
        m1.setLat(43.7792500);
        m1.setLon(11.2462600);

        Municipality m2 = new Municipality();
        m2.setId(UUID.randomUUID().toString());
        m2.setName("Prato");
        m2.setLat(43.8852);
        m2.setLon(11.0919);

        Municipality m3 = new Municipality();
        m3.setId(UUID.randomUUID().toString());
        m3.setName("Milano");
        m3.setLat(45.4773);
        m3.setLon(9.1815);

        Municipality m4 = new Municipality();
        m4.setId(UUID.randomUUID().toString());
        m4.setName("Napoli");
        m4.setLat(40.863);
        m4.setLon(14.2767);

        // USER ACCOUNT
        UserAccount ua1 = new UserAccount ();
        ua1.setId(UUID.randomUUID().toString());
        ua1.setName("Marco");
        ua1.setSurname("Loschiavo");
        ua1.setEmail("marco@libero.it");
        ua1.password = "password";
        ua1.setUserRole(UserRole.SUPER_ADMIN);

        UserAccount ua2 = new UserAccount ();
        ua2.setId(UUID.randomUUID().toString());
        ua2.setName("Giulio");
        ua2.setSurname("Calamaio");
        ua2.setEmail("giulio@libero.it");
        ua2.password = "password";
        ua2.setUserRole(UserRole.ADMIN);


        //SERVICE PROVIDER INFO
        ServiceProviderInfo spi1 = new ServiceProviderInfo();
        spi1.setServiceProviderType(ServiceProviderType.ApiServiceProvider);
        spi1.setServiceProviderTypeContent("content of API service provider");

        ServiceProviderInfo spi2 = new ServiceProviderInfo();
        spi2.setServiceProviderType(ServiceProviderType.EmbeddableViewServiceProvider);
        spi2.setServiceProviderTypeContent("content of embeddable view service provider");

        ServiceProviderInfo spi3 = new ServiceProviderInfo();
        spi3.setServiceProviderType(ServiceProviderType.RedirectableServiceProvider);
        spi3.setServiceProviderTypeContent("redirectable service provider");


        //INFOMOBILITY SERVICE PROVIDER
        InfomobilityServiceProvider isp1 = new InfomobilityServiceProvider();
        isp1.setId(UUID.randomUUID().toString());
        isp1.setName("Car2Go");
        isp1.setEnabled(true);
        isp1.getMobilityTypes().add(MobilityType.CAR);
        isp1.setServiceProviderInfo(spi1);

        InfomobilityServiceProvider isp2 = new InfomobilityServiceProvider();
        isp2.setId(UUID.randomUUID().toString());
        isp2.setName("Mobike");
        isp2.setEnabled(true);
        isp2.getMobilityTypes().add(MobilityType.BIKE);
        isp2.setServiceProviderInfo(spi2);

        InfomobilityServiceProvider isp3 = new InfomobilityServiceProvider();
        isp3.setId(UUID.randomUUID().toString());
        isp3.setName("Trenitalia");
        isp3.setEnabled(true);
        isp3.getMobilityTypes().add(MobilityType.TRAIN);
        isp3.setServiceProviderInfo(spi3);

        InfomobilityServiceProvider isp4 = new InfomobilityServiceProvider();
        isp4.setId(UUID.randomUUID().toString());
        isp4.setName("PratoBike");
        isp4.setEnabled(true);
        isp4.getMobilityTypes().add(MobilityType.BIKE);
        isp4.setServiceProviderInfo(spi3);


        //SMART STATION
        SmartStation sm1 = new SmartStation();
        sm1.setId(UUID.randomUUID().toString());
        sm1.setName("FirenzeSMN SmartStation");
        sm1.setEnabled(true);
        sm1.setMunicipality(m1);
        sm1.setExternalPlaceId("1325");
        sm1.getInfomobilityServiceProviders().add(isp2);
        sm1.getInfomobilityServiceProviders().add(isp3);

        SmartStation sm2 = new SmartStation();
        sm2.setId(UUID.randomUUID().toString());
        sm2.setName("MilanoCity SmartStation");
        sm2.setEnabled(true);
        sm2.setMunicipality(m3);
        sm2.getInfomobilityServiceProviders().add(isp1);
        sm2.getInfomobilityServiceProviders().add(isp3);

        SmartStation sm3 = new SmartStation();
        sm3.setId(UUID.randomUUID().toString());
        sm3.setName("Napoli SmartStation");
        sm3.setEnabled(true);
        sm3.setMunicipality(m4);
        sm3.getInfomobilityServiceProviders().add(isp3);

        SmartStation sm4 = new SmartStation();
        sm4.setId(UUID.randomUUID().toString());
        sm4.setName("Prato SmartStation");
        sm4.setEnabled(true);
        sm4.setMunicipality(m2);
        sm4.getInfomobilityServiceProviders().add(isp4);


        //ADMIN AUTHORIZATION
        AdminAuthorization aa1 = new AdminAuthorization();
        aa1.setId(UUID.randomUUID().toString());
        aa1.setUserAccount(ua2);
        aa1.setMunicipality(m1);  //firenze
        Date date1 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY).parse("15/09/2020");
        aa1.setExpireDate(date1);
        aa1.setInfomobilityServiceProvider(isp2);


        AdminAuthorization aa2 = new AdminAuthorization();
        aa2.setId(UUID.randomUUID().toString());
        aa2.setUserAccount(ua2);
        aa2.setMunicipality(m3); // milano
        Date date2 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY).parse("15/02/2020");
        aa2.setExpireDate(date2);
        aa2.setInfomobilityServiceProvider(isp1);


        AdminAuthorization aa3 = new AdminAuthorization();
        aa3.setId(UUID.randomUUID().toString());
        aa3.setUserAccount(ua2);
        aa3.setMunicipality(m4); // napoli
        Date date3 = null;
        aa3.setExpireDate(date3);
        aa3.setInfomobilityServiceProvider(isp3);



        entityManager.getTransaction().begin();
        entityManager.persist(m1);
        entityManager.persist(m2);
        entityManager.persist(m3);
        entityManager.persist(m4);
        entityManager.persist(ua1);
        entityManager.persist(ua2);
        entityManager.persist(aa1);
        entityManager.persist(aa2);
        entityManager.persist(aa3);
        entityManager.persist(isp1);
        entityManager.persist(isp2);
        entityManager.persist(isp3);
        entityManager.persist(isp4);
        entityManager.persist(sm1);
        entityManager.persist(sm2);
        entityManager.persist(sm3);
        entityManager.persist(sm4);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
*/
