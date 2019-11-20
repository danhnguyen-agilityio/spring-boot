package guru.springfamework.api.v1.model;

import java.util.List;

public class VendorListDTO {

    List<VendorDTO> vendors;

    public VendorListDTO(List<VendorDTO> vendors) {
        this.vendors = vendors;
    }

    public List<VendorDTO> getVendors() {
        return vendors;
    }

    public void setVendors(List<VendorDTO> vendors) {
        this.vendors = vendors;
    }
}
