package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

        CarroDTO carroRecuperado = service.getById(id);
        assertNotNull(carroRecuperado);
        assertEquals("Ferrari", carroRecuperado.getNome());
        assertEquals("esportivos", carroRecuperado.getTipo());

        service.delete(id);

        try {
            assertNull(service.getById(id));
            fail("O carro não foi excluído");
        } catch (com.carros.api.exception.ObjectNotFoundException ex) {
             // ok, é o esperado
        }
    }

    @Test
    public void testLista() {
        assertEquals(30,
                service.getCarros().size());

    }

}
