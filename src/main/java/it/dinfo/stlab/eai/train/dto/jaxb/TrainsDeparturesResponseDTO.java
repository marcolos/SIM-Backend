package it.dinfo.stlab.eai.train.dto.jaxb;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "DeparturesResponse",
        namespace = "http://schemas.datacontract.org/2004/07/PIC.IeC.SV.UsrSvcs.FrontEnd.Model") // specifies the root element for the XML document.
@XmlAccessorType(XmlAccessType.FIELD)
public class TrainsDeparturesResponseDTO {

    @XmlElementWrapper(name = "Departures")
    @XmlElement(name = "Departure") //specifies the sub-element for the root element.
    private List<Departure> departure;

    public List<Departure> getDeparture() {
        return departure;
    }

    public void setDeparture(List<Departure> departure) {
        this.departure = departure;
    }



    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Departure {

        @XmlElement(name = "Cancelled")
        private Boolean cancelled;

        @XmlElement(name = "Delay")
        private String delay;

        @XmlElement(name = "Platform")
        private String platform;

        @XmlElement(name = "Time")
        private String time;

        @XmlElement(name = "TrainNumber")
        private String trainNumber;

        @XmlElement(name = "TrainHeader")
        private TrainHeader trainheader;

        public Boolean getCancelled() {
            return cancelled;
        }

        public void setCancelled(Boolean cancelled) {
            this.cancelled = cancelled;
        }

        public String getDelay() {
            return delay;
        }

        public void setDelay(String delay) {
            this.delay = delay;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(String trainNumber) {
            this.trainNumber = trainNumber;
        }

        public TrainHeader getTrainheader() {
            return trainheader;
        }

        public void setTrainheader(TrainHeader trainheader) {
            this.trainheader = trainheader;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TrainHeader {

        @XmlElement(name = "Id")
        private String id;

        @XmlElement(name = "BrandCustomer")
        private String brandCustomer;

        @XmlElement(name = "BrandCategory")
        private String brandCategory;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrandCustomer() {
            return brandCustomer;
        }

        public void setBrandCustomer(String brandCustomer) {
            this.brandCustomer = brandCustomer;
        }

        public String getBrandCategory() {
            return brandCategory;
        }

        public void setBrandCategory(String brandCategory) {
            this.brandCategory = brandCategory;
        }
    }

}