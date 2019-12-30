package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public List<CarroDTO> get(){
        return carroService.getCarros();
    }

    @GetMapping("/{id}")
    public Optional<CarroDTO> getById(@PathVariable Long id) {
        return carroService.getById(id).map(CarroDTO::create);
    }

    @GetMapping("/tipo/{tipo}")
    public List<CarroDTO> getByTipo(@PathVariable String tipo) {
        return carroService.findByTipo(tipo);
    }

    @PostMapping
    public ResponseEntity<CarroDTO> post(@RequestBody Carro carro) {
        try {
            Carro c = carroService.insert(carro);
            return ResponseEntity.created(getUri(c.getId())).build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable Long id,  @RequestBody Carro carro) {
        return carroService.update(carro, id).map(
                updatedCarro -> ResponseEntity.ok(CarroDTO.create(updatedCarro))
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return carroService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();

    }
}
