package med.voll.api.domain.medico;

public record ListMedicoData(Long id, String name, String specialty, String document, String email) {
    public ListMedicoData(Medico medico){
        this(medico.getId(), medico.getName(), medico.getSpecialty().toString(), medico.getDocument(), medico.getEmail());
    }
}
