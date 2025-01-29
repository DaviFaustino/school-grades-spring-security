package com.davifaustino.schoolgradesspringsecurity;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.davifaustino.schoolgradesspringsecurity.api.dtos.AuthResponse;
import com.davifaustino.schoolgradesspringsecurity.api.dtos.ReportCardRequest;
import com.davifaustino.schoolgradesspringsecurity.api.dtos.ReportCardResponse;
import com.davifaustino.schoolgradesspringsecurity.domain.enums.SchoolSubject;

@SuppressWarnings("null")
// @TestPropertySource(properties = {"jwt.expiration-time.access=10", "jwt.expiration-time.refresh=20"})
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReportCardsIntegrationTest {
    
    @Autowired
    private TestRestTemplate testRestTemplate;
    private static HttpHeaders headers;

    @BeforeAll
    static void beforeAll(@Autowired JdbcTemplate jdbcTemplate, @Autowired TestRestTemplate testRestTemplate) throws IOException {
        String data = Files.readString(Paths.get("./src/test/resources/data.sql"));
        jdbcTemplate.execute(data);

        HttpHeaders headersLogin = new HttpHeaders();
        headersLogin.setBasicAuth("teacher", "1234");

        HttpEntity<String> httpEntityLogin = new HttpEntity<>(null, headersLogin);
        ResponseEntity<AuthResponse> loginResponse = testRestTemplate.exchange(
            "/authentication/login",
            HttpMethod.POST,
            httpEntityLogin,
            AuthResponse.class
        );
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.getBody().accessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Save new report card successfully")
    void ReportCardPost1() {

        ReportCardRequest requestSave = new ReportCardRequest(
            "teacher",
            SchoolSubject.MATHS,
            "student",
            "2024",
            Arrays.asList("9.00", "9.00", "9.00", "9.00")
        );

        HttpEntity<ReportCardRequest> httpEntitySave = new HttpEntity<>(requestSave, headers);
        ResponseEntity<Void> responseSave = testRestTemplate.exchange(
            "/report-card",
            HttpMethod.POST,
            httpEntitySave,
            Void.class
        );

        assertEquals(HttpStatus.OK, responseSave.getStatusCode());
    }

    @Test
    @DisplayName("Get report card successfully")
    void ReportCardGet1() {

        HttpEntity<Void> httpEntityGet = new HttpEntity<>(null, headers);
        ResponseEntity<List<ReportCardResponse>> responseGet = testRestTemplate.exchange(
            "/report-card/teacher?username=teacher",
            HttpMethod.GET,
            httpEntityGet,
            new ParameterizedTypeReference<>() {}
        );
        assertEquals(HttpStatus.OK, responseGet.getStatusCode());
    }

    @Test
    @DisplayName("Get a 403 error")
    void ReportCardGet2() {

        HttpEntity<Void> httpEntityGet = new HttpEntity<>(null, headers);
        ResponseEntity<List<ReportCardResponse>> responseGet = testRestTemplate.exchange(
            "/report-card/student?username=student",
            HttpMethod.GET,
            httpEntityGet,
            new ParameterizedTypeReference<>() {}
        );
        assertEquals(HttpStatus.FORBIDDEN, responseGet.getStatusCode());
    }

    @Test
    @DisplayName("Save new report card successfully")
    void ReportCardPut1() {

        ReportCardRequest requestPut = new ReportCardRequest(
            "teacher",
            SchoolSubject.HISTORY,
            "student",
            "2024",
            Arrays.asList("9.00", "9.00", "9.00", "9.00")
        );

        HttpEntity<ReportCardRequest> httpEntityPut = new HttpEntity<>(requestPut, headers);
        ResponseEntity<Void> responsePut = testRestTemplate.exchange(
            "/report-card?id=0238ab71-7ead-4621-a962-62101e9df158",
            HttpMethod.PUT,
            httpEntityPut,
            Void.class
        );
        assertEquals(HttpStatus.ACCEPTED, responsePut.getStatusCode());
    }
}
