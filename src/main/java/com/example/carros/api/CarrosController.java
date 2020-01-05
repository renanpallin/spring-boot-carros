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

@RestController
@RequestMapping("/carros")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public List<CarroDTO> get() {
        return carroService.getCarros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carroService.getById(id));
    }

    @GetMapping("/tipo/{tipo}")
    public List<CarroDTO> getByTipo(@PathVariable String tipo) {
        return carroService.findByTipo(tipo);
    }

    @PostMapping
    public ResponseEntity<CarroDTO> post(@RequestBody Carro carro) {
        Carro c = carroService.insert(carro);
        return ResponseEntity.created(getUri(c.getId())).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable Long id, @RequestBody Carro carro) {
        return carroService.update(carro, id).map(
                updatedCarro -> ResponseEntity.ok(updatedCarro)
        ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }
}
