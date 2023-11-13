package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.AddressData;

public record ResponseMedicoData(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        AddressData addressData) {
}
