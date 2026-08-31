package com.example.demo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.defer-datasource-initialization=true",
    "spring.sql.init.mode=always"
})
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMovie() throws Exception {
        String movieJson = """
            {
                "movieName": "Interstellar",
                "yearId": 1,
                "genreIds": [1, 2],
                "actorIds": [1, 2]
            }
            """;

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.movieName").value("Interstellar"))
            .andExpect(jsonPath("$.year").value(2014));
    }

    @Test
    void shouldGetAllMovies() throws Exception {
        String movieJson = """
            {
                "movieName": "Titanic",
                "yearId": 2,
                "genreIds": [2, 4],
                "actorIds": [3, 4]
            }
            """;

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].movieName").exists());
    }

    @Test
    void shouldGetMovieById() throws Exception {
        String movieJson = """
            {
                "movieName": "Avatar",
                "yearId": 3,
                "genreIds": [1, 3],
                "actorIds": [5, 6]
            }
            """;

        MvcResult result = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated())
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String movieId = responseBody.split("\"id\":")[1].split(",")[0];

        mockMvc.perform(get("/movies/" + movieId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(Integer.parseInt(movieId)))
            .andExpect(jsonPath("$.movieName").value("Avatar"))
            .andExpect(jsonPath("$.year").value(2009));
    }

    @Test
    void shouldUpdateMovie() throws Exception {
        String movieJson = """
            {
                "movieName": "Old Movie Name",
                "yearId": 1,
                "genreIds": [1],
                "actorIds": [1]
            }
            """;

        MvcResult result = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated())
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String movieId = responseBody.split("\"id\":")[1].split(",")[0];

        String updatedMovieJson = """
            {
                "movieName": "Updated Movie Name",
                "yearId": 2,
                "genreIds": [2, 4],
                "actorIds": [3, 4]
            }
            """;

        mockMvc.perform(put("/movies/" + movieId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedMovieJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(Integer.parseInt(movieId)))
            .andExpect(jsonPath("$.movieName").value("Updated Movie Name"))
            .andExpect(jsonPath("$.year").value(1997));
    }

    @Test
    void shouldDeleteMovie() throws Exception {
        String movieJson = """
            {
                "movieName": "Movie To Delete",
                "yearId": 1,
                "genreIds": [1],
                "actorIds": [1]
            }
            """;

        MvcResult result = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated())
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String movieId = responseBody.split("\"id\":")[1].split(",")[0];

        mockMvc.perform(delete("/movies/" + movieId))
            .andExpect(status().isNoContent());

        mockMvc.perform(get("/movies/" + movieId))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindMoviesByName() throws Exception {
        String movieJson = """
            {
                "movieName": "Inception",
                "yearId": 4,
                "genreIds": [1, 3],
                "actorIds": [3]
            }
            """;

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/movies/name/inception"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].movieName").value("Inception"));
    }

    @Test
    void shouldFindMoviesByGenre() throws Exception {
        String movieJson = """
            {
                "movieName": "Sci Fi Test Movie",
                "yearId": 1,
                "genreIds": [1],
                "actorIds": [1]
            }
            """;

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson))
            .andExpect(status().isCreated());

        mockMvc.perform(get("/movies/genre/sci-fi"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[*].movieName").value(org.hamcrest.Matchers.hasItem("Sci Fi Test Movie")));
    }
}
