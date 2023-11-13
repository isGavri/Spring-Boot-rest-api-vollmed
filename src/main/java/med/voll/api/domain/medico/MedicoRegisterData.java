package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.AddressData;

public record MedicoRegisterData (
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        @NotBlank
        @Pattern(regexp = "\\d{6,8}")
        String document,
        @NotNull
        Specialty specialty,
        @NotNull
        @Valid
        AddressData addressData
){

}
