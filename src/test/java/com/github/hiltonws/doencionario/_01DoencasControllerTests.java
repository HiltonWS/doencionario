package com.github.hiltonws.doencionario;

import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.security.jwt.AccountCredentials;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class _01DoencasControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static Long lastId;

    @BeforeEach
    public void setUp() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/rest/v1/login";
        URI uri = new URI(baseUrl);

        AccountCredentials credentials = new AccountCredentials();
        credentials.setUsername("admin");
        credentials.setPassword("admin");
        HttpEntity<AccountCredentials> request = new HttpEntity<>(credentials);

        HttpHeaders headers = this.testRestTemplate.postForEntity(uri, request, Void.class).getHeaders();

        testRestTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((req, body, execution) -> {
                    req.getHeaders().addAll(headers);
                    return execution.execute(req, body);
                }));
    }

    @Test
    @Order(1)
    public void deveInserirADoencaERetornarOId() throws Exception {

        final String baseUrl = "http://localhost:" + port + "/rest/v1/doenca";
        URI uri = new URI(baseUrl);

        DoencaDTO dto = new DoencaDTO();
        dto.setCodigo("A00");
        dto.setDescricao("Cólera");
        HttpEntity<DoencaDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Long> result = this.testRestTemplate.postForEntity(uri, request, Long.class);
        lastId = result.getBody();
        Assert.assertNotNull(result.getBody());

    }

    @Test
    @Order(2)
    public void deveRetornoarADoencaPeloId() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/rest/v1/doenca";
        final String testeUrl = baseUrl + "/" + lastId;

        URI uri = new URI(testeUrl);

        DoencaDTO dto = new DoencaDTO();
        dto.setCodigo("A00");
        dto.setDescricao("Cólera");

        ResponseEntity<DoencaDTO> result = this.testRestTemplate.getForEntity(uri, DoencaDTO.class);

        Assert.assertEquals(dto, result.getBody());

    }
}
