package com.github.hiltonws.doencionario;

import com.github.hiltonws.doencionario.dto.BeneFiciarioDoencaDTO;
import com.github.hiltonws.doencionario.dto.BeneficiarioDTO;
import com.github.hiltonws.doencionario.dto.DoencaDTO;
import com.github.hiltonws.doencionario.security.jwt.AccountCredentials;
import org.junit.Assert;
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

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class _02BeneficiarioControllerTests {

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
    public void deveInserirOBenefeciarioERetornarOId() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/rest/v1/beneficiario";

        URI uri = new URI(baseUrl);

        BeneficiarioDTO dto = new BeneficiarioDTO();
        dto.setNome("JOÃO DA SILVA");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");
        dto.setDtNascimento(simpleDateFormat.parse("01/01/2018"));
        HttpEntity<BeneficiarioDTO> request = new HttpEntity<>(dto);

        ResponseEntity<Long> result = this.testRestTemplate.postForEntity(uri, request, Long.class);
        lastId = result.getBody();
        Assert.assertNotNull(result.getBody());

    }


    @Test
    @Order(2)
    public void deveRetornoarOBeneficiarioPeloId() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/rest/v1/beneficiario";
        final String testeUrl = baseUrl + "/" + lastId;

        URI uri = new URI(testeUrl);

        BeneficiarioDTO dto = new BeneficiarioDTO();
        dto.setNome("JOÃO DA SILVA");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD/MM/YYYY");
        dto.setDtNascimento(simpleDateFormat.parse("01/01/2018"));

        ResponseEntity<BeneficiarioDTO> result = this.testRestTemplate.getForEntity(uri, BeneficiarioDTO.class);

        Assert.assertEquals(dto, result.getBody());

    }

    @Test
    @Order(3)
    public void deveVincularADoencaAoBeneficiario() throws Exception {

        final String baseUrl = "http://localhost:" + port + "/rest/v1/beneficiario/doenca";
        URI uri = new URI(baseUrl);

        BeneFiciarioDoencaDTO dto = new BeneFiciarioDoencaDTO();

        dto.setIdDoenca(1L);
        dto.setIdBeneficiario(lastId);
        HttpEntity<BeneFiciarioDoencaDTO> request = new HttpEntity<>(dto);

        ResponseEntity<String> result = this.testRestTemplate.postForEntity(uri, request, String.class);

        Assert.assertEquals("Beneficiário JOÃO DA SILVA vinculado à doença Cólera com sucesso.", result.getBody());

    }

    @Test
    @Order(4)
    public void deveRetornarAsDoencasDoBeneficiarioPeloIDdoBeneficiario() throws Exception {

        final String baseUrl = "http://localhost:" + port + "/rest/v1/beneficiario/" + lastId + "/doencas";
        URI uri = new URI(baseUrl);

        List<DoencaDTO> doencas = new ArrayList<>();
        DoencaDTO dto1 = new DoencaDTO();
        dto1.setCodigo("A00");
        dto1.setDescricao("Cólera");
        doencas.add(dto1);
        ResponseEntity<DoencaDTO[]> result = this.testRestTemplate.getForEntity(uri, DoencaDTO[].class);

        Assert.assertArrayEquals(doencas.toArray(new DoencaDTO[0]), result.getBody());

    }
}
