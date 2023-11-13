package med.voll.api.domain.direccion;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String number;
    private String district;
    private String city;

    public Address(AddressData addressData){
        this.street = addressData.street();
        this.number = addressData.number();
        this.district = addressData.district();
        this.city = addressData.city();
    }

    public Address updateAddress(AddressData addressData) {
        this.street = addressData.street();
        this.number = addressData.number();
        this.district = addressData.district();
        this.city = addressData.city();
        return this;
    }
}
