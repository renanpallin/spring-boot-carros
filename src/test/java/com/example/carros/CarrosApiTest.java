package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosApiTest {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<Carro> getCarro(String url) {
        return rest.getForEntity(url, Carro.class);
    }

    private ResponseEntity<List<CarroDTO>> getCarros(String url) {
        return rest.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarroDTO>>() {
                });
    }

    @Test
    public void testLista() {
        List<CarroDTO> body = getCarros("/api/v1/carros").getBody();
        assertNotNull(body);
        assertEquals(30, body.size());
    }

    @Test
    public void testListaPorTipo() {
        List<CarroDTO> classicos = getCarros("/api/v1/carros/tipo/classicos").getBody();
        assertNotNull(classicos);
        assertEquals(10, classicos.size());

        List<CarroDTO> esportivos = getCarros("/api/v1/carros/tipo/esportivos").getBody();
        assertNotNull(esportivos);
        assertEquals(10, esportivos.size());

        List<CarroDTO> luxo = getCarros("/api/v1/carros/tipo/luxo").getBody();
        assertNotNull(luxo);
        assertEquals(10, luxo.size());


        List<CarroDTO> none = getCarros("/api/v1/carros/tipo/xxx").getBody();
        assertNotNull(none);
        assertEquals(0, none.size());
    }

    @Test
    public void testGetOk() {
        ResponseEntity<Carro> response = getCarro("/api/v1/carros/11");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Carro body = response.getBody();
        assertEquals(body.getNome(), "Ferrari FF");
    }

    @Test
    public void testNotFound() {
        ResponseEntity<Carro> response = getCarro("/api/v1/carros/1100");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSave() {
        Carro c = new Carro();
        c.setNome("Porshe");
        c.setTipo("esportivos");

        // Insert
        ResponseEntity<Object> response = rest.postForEntity("/api/v1/carros", c, null);
        System.out.println(response);

        // Verifica se criou pelo status code
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String novoCarroLocation = response.getHeaders().get("location").get(0);
        Carro novoCarro = getCarro(novoCarroLocation).getBody();
        assertNotNull(novoCarro);
        assertEquals("Porshe", novoCarro.getNome());
        assertEquals("esportivos", novoCarro.getTipo());

        // Deletar o objeto
        rest.delete(novoCarroLocation);

        // Verifica se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarro(novoCarroLocation).getStatusCode());
    }
}
