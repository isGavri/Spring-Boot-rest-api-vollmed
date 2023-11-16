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
        Medico medico = medicoRepository.save(new Medico(medicoRegisterData)); // We instance a new Medico with the given data from the Request Body and save it through the medicoRepository in the DB
        ResponseMedicoData responseMedicoData = new ResponseMedicoData(medico.getId(), medico.getName(),
                medico.getEmail(), medico.getPhoneNumber(), medico.getSpecialty().toString(), new AddressData(medico.getAddress().getStreet(),
                medico.getAddress().getDistrict(), medico.getAddress().getCity(), medico.getAddress().getNumber())); // We build a data record of the new saved medico to return in the ResponseEntity
        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); // Used to create a link (url or uri) for the created instance of the Medico. Where we can locate it through the api

        return ResponseEntity.created(uri).body(responseMedicoData); // We return the created code (201) with the uri to access the new Medico as an argument and the data of the New medico as body.
    }

    @GetMapping
    public ResponseEntity<Page<ListMedicoData>> medicosList(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(medicoRepository.findByActiveTrue(pageable).map(ListMedicoData::new)); //through the MedicoRepository we access the DB and we return the active Medicos
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateMedico(@RequestBody @Valid UpdateMedicoData updateMedicoData){
        Medico medico = medicoRepository.getReferenceById(updateMedicoData.id()); // We use the id given in the data received from the RequestBody to, through the medicoRepository access the desired Medico in the database
        medico.updateData(updateMedicoData); // Update it in the DB with the new data
        return ResponseEntity.ok(new ResponseMedicoData(medico.getId(), medico.getName(),
                medico.getEmail(), medico.getPhoneNumber(), medico.getSpecialty().toString(), new AddressData(medico.getAddress().getStreet(),
                medico.getAddress().getDistrict(), medico.getAddress().getCity(), medico.getAddress().getNumber()))); // Return 200 ok and the new Response for the created instance.
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id); // Selects the Medico in the database with the given id in the path
        medico.deactivateMedico(); // Sets the active to false
        return ResponseEntity.noContent().build(); // Returns no content because its no longer accessible
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMedicoData> medicoData(@PathVariable Long id){ // A combination of the medico register and list. Uses the URI created in the registerMedico to access the desired Medico and returns the data of only that Medico
        Medico medico = medicoRepository.getReferenceById(id);
        var medicoData = new ResponseMedicoData(medico.getId(), medico.getName(),
                medico.getEmail(), medico.getPhoneNumber(), medico.getSpecialty().toString(), new AddressData(medico.getAddress().getStreet(),
                medico.getAddress().getDistrict(), medico.getAddress().getCity(), medico.getAddress().getNumber()));
        return ResponseEntity.ok(medicoData);
    }
}
