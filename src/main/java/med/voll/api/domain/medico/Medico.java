package med.voll.api.domain.medico;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Address;
import med.voll.api.domain.direccion.AddressData;

@Table(name = "medicos")
@Entity(name ="medico")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String document;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @Embedded
    private Address address;

    public Medico(MedicoRegisterData medicoRegisterData){
        this.active = true;
        this.address = new Address(medicoRegisterData.addressData());
        this.specialty = medicoRegisterData.specialty();
        this.document = medicoRegisterData.document();
        this.phoneNumber = medicoRegisterData.phoneNumber();
        this.email = medicoRegisterData.email();
        this.name = medicoRegisterData.name();
    }

    public void updateData(UpdateMedicoData updateMedicoData){
        if (updateMedicoData.name() != null) {
            this.name = updateMedicoData.name();
        }
        if (updateMedicoData.document() != null) {
            this.document = updateMedicoData.document();
        }
        if (updateMedicoData.address() != null) {
            this.address = address.updateAddress(updateMedicoData.address());
        }
    }

    public void deactivateMedico(){
        this.active = false;
    }

}
