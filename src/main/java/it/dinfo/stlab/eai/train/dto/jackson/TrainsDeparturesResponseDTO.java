package it.dinfo.stlab.eai.train.dto.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "DeparturesResponse") //Define root element name in XML. localName is the name of the XML root element
public class TrainsDeparturesResponseDTO {

    @JacksonXmlElementWrapper(localName = "Departures") //Define wrapper to use for collection types. localName is the name of the XML wrapper element
    @JacksonXmlProperty(localName = "Departure") //Define XML property, can be attribute or element. localName is the name of the XML element
    @JsonProperty("departures") //Nome di uscita in json. Altrimenti il nome sarebbe quello della variabile (departures)
    private List<Departure> departures;
    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

    static class Departure {

        @JacksonXmlProperty(localName = "Cancelled")
        @JsonProperty("cancelled")
        private Boolean cancelled;

        @JacksonXmlProperty(localName = "Delay")
        @JsonProperty("delay")
        private String delay;

        @JacksonXmlProperty(localName = "Platform")
        @JsonProperty("platform")
        private String platform;

        @JacksonXmlProperty(localName = "Time")
        @JsonProperty("time")
        private String time;

        @JacksonXmlProperty(localName = "TrainNumber")
        @JsonProperty("trainNumber")
        private String trainNumber;

        @JacksonXmlProperty(localName = "TrainHeader")
        @JsonProperty("trainHeader")
        private TrainHeader trainHeaders;


        @JacksonXmlProperty(localName = "Messages")
        @JsonProperty("messages")
        private Messages messages;

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

        public TrainHeader getTrainHeaders() {
            return trainHeaders;
        }

        public void setTrainHeaders(TrainHeader trainHeaders) {
            this.trainHeaders = trainHeaders;
        }

        public Messages getMessages() {
            return messages;
        }

        public void setMessages(Messages messages) {
            this.messages = messages;
        }
    }



    static class TrainHeader {

        @JacksonXmlProperty(localName = "Id")
        @JsonProperty("id")
        private String id;

        @JacksonXmlProperty(localName = "BrandCustomer")
        @JsonProperty("brandCustomer")
        private String brandCustomer;

        @JacksonXmlProperty(localName = "BrandCategory")
        @JsonProperty("brandCategory")
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


    static class Messages {

        @JacksonXmlProperty(localName = "AdditionalData")
        @JsonProperty("additionalData")
        private AdditionalData additionalData;

        public AdditionalData getAdditionalData() {
            return additionalData;
        }

        public void setAdditionalData(AdditionalData additionalData) {
            this.additionalData = additionalData;
        }
    }


    static class AdditionalData {

        @JacksonXmlProperty(localName = "Content")
        @JsonProperty("content")
        private String content;


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

}
