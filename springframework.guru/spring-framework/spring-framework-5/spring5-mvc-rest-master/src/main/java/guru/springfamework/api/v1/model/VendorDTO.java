package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class VendorDTO {

    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;

    public VendorDTO() {
    }

    public VendorDTO(String name, String vendorUrl) {
        this.name = name;
        this.vendorUrl = vendorUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorUrl() {
        return vendorUrl;
    }

    public void setVendorUrl(String vendorUrl) {
        this.vendorUrl = vendorUrl;
    }
}
