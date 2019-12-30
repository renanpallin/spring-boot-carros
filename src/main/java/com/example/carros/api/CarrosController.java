package com.example.carros.api;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public Iterable<Carro> get(){
        return carroService.getCarros();
    }

    @GetMapping("/{id}")
    public Optional<Carro> getById(@PathVariable Long id) {
        return carroService.getById(id);
    }

    @GetMapping("/tipo/{tipo}")
    public Iterable<Carro> getByTipo(@PathVariable String tipo) {
        return carroService.findByTipo(tipo);
    }

    @PostMapping
    public String post(@RequestBody Carro carro) {
        Carro c = carroService.insert(carro);
        return "¢arro salvo com sucesso " + c.getId();
    }

    @PutMapping("/{id}")
    public String put(@PathVariable Long id,  @RequestBody Carro carro) {
        Carro c = carroService.update(carro, id);
        return "¢arro atualizado com sucesso " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        carroService.delete(id);
        return "Carro " + id + " deletado com sucesso";
    }
}
