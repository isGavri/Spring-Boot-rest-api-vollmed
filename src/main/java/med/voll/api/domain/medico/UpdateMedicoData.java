package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.Address;
import med.voll.api.domain.direccion.AddressData;

public record UpdateMedicoData(
        @NotNull
        Long id,
        String name,
        String document,
        AddressData address) {
}
