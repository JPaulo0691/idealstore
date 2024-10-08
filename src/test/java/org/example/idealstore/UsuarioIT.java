package org.example.idealstore;

import org.assertj.core.api.Assertions;
import org.example.idealstore.dto.request.UsuarioRequest;
import org.example.idealstore.dto.response.UsuarioResponse;
import org.example.idealstore.exception.web.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Tomcat será executado em uma porta randômica
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUserNameEPasswordValidos_RetonarUsuarioCriadoComStatus201() {
        UsuarioResponse responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioRequest("joao@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getUsername()).isEqualTo("joao@email.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void createUsuario_UsuarioComEmailJaCadastrado_RetornarErroComStatus409() {

        testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioRequest("joao@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated();

        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioRequest("joao@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void createUsuario_UsernameInvalido_RetonarErroComStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioRequest("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUsuario_UsernameInvalido_ErroEmail_RetonarErroComStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioRequest("joao@test", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void BuscarUsuario_ComIdExistente_RetornarUsuarioComStatus200() {

        UsuarioResponse response = testClient
                .get()
                .uri("api/v1/usuarios/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(100);
        Assertions.assertThat(response.getUsername()).isEqualTo("neymar@email.com");
        Assertions.assertThat(response.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void ListarTodosUsuario_ComIdExistente_RetornarUsuarioComStatus200() {

        List<UsuarioResponse> response = testClient
                .get()
                .uri("api/v1/usuarios")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponse.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response).isNotEmpty();
    }
}