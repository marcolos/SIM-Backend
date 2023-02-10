package it.dinfo.stlab.model;

import javax.persistence.*;

@Embeddable
public class ServiceProviderInfo {

    @Enumerated(EnumType.STRING)
    private ServiceProviderType serviceProviderType;

    /*Se ServiceProviderType ha valore:
        ApiServiceProvider allora la stringa content sarà una URI
        EmbeddableViewServiceProvider allora la stringa content sarà un content(pezzo di html o altro)
        RedirectableServiceProvider allora la stringa content sarà una URI*/
    @Column(name = "service_provider_type_content")
    private String serviceProviderTypeContent;

    public ServiceProviderInfo(){}

    public ServiceProviderInfo(ServiceProviderType type, String content){
        this.serviceProviderType = type;
        this.serviceProviderTypeContent = content;
    }

    public ServiceProviderType getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(ServiceProviderType serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }

    public String getServiceProviderTypeContent() {
        return serviceProviderTypeContent;
    }

    public void setServiceProviderTypeContent(String content) {
        this.serviceProviderTypeContent = content;
    }
}

