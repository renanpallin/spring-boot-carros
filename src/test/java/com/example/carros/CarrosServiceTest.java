package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CarrosServiceTest {

    @Autowired
    private CarroService service;

    @Test
    public void testSalvar() {
        Carro c = new Carro();
        c.setNome("Ferrari");
        c.setTipo("esportivos");

        Carro inserted = service.insert(c);
        assertNotNull(inserted);

        Long id = c.getId();
        assertNotNull(id);

        Optional<Carro> opCarroRecuperado = service.getById(id);
        assertTrue(opCarroRecuperado.isPresent());
        Carro carroRecuperado = opCarroRecuperado.get();
        assertEquals("Ferrari", carroRecuperado.getNome());
        assertEquals("esportivos", carroRecuperado.getTipo());
    }

    @Test
    public void testLista() {
        assertEquals(30,
                service.getCarros().size());

    }

}
