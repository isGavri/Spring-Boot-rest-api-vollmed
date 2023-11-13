package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.AddressData;
import med.voll.api.domain.medico.*;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<ResponseMedicoData> registerMedico(@RequestBody @Valid MedicoRegisterData medicoRegisterData, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(medicoRegisterData));
        ResponseMedicoData responseMedicoData = new ResponseMedicoData(medico.getId(), medico.getName(),
                medico.getEmail(), medico.getPhoneNumber(), medico.getSpecialty().toString(), new AddressData(medico.getAddress().getStreet(),
                medico.getAddress().getDistrict(), medico.getAddress().getCity(), medico.getAddress().getNumber()));
        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(responseMedicoData);
    }

    @GetMapping
    public ResponseEntity<Page<ListMedicoData>> medicosList(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(medicoRepository.findByActiveTrue(pageable).map(ListMedicoData::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateMedico(@RequestBody @Valid UpdateMedicoData updateMedicoData){
        Medico medico = medicoRepository.getReferenceById(updateMedicoData.id());
        medico.updateData(updateMedicoData);
        return ResponseEntity.ok(new ResponseMedicoData(medico.getId(), medico.getName(),
                medico.getEmail(), medico.getPhoneNumber(), medico.getSpecialty().toString(), new AddressData(medico.getAddress().getStreet(),
                medico.getAddress().getDistrict(), medico.getAddress().getCity(), medico.getAddress().getNumber())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.deactivateMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMedicoData> medicoData(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var medicoData = new ResponseMedicoData(medico.getId(), medico.getName(),
                medico.getEmail(), medico.getPhoneNumber(), medico.getSpecialty().toString(), new AddressData(medico.getAddress().getStreet(),
                medico.getAddress().getDistrict(), medico.getAddress().getCity(), medico.getAddress().getNumber()));
        return ResponseEntity.ok(medicoData);
    }
}
