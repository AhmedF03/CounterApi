package org.example.counterapi.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest     // only tests controller
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc; // tests api without actually starting a server

    @BeforeEach
    public void setup() {
        ApiController.dictionary.clear();
        ApiController.dictionary.put("abc", 5);
        ApiController.dictionary.put("xyz", 1);
    }

    @Test
    void getCounters() throws Exception {
        mockMvc.perform(get("/Counters/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abc").value(5))
                .andExpect(jsonPath("$.xyz").value(1));
    }

    @Test
    void createCounter() throws Exception {
        String initialValue = "{\"initialValue\":10}";
        mockMvc.perform(post("/Counters")
                .contentType(MediaType.APPLICATION_JSON)    // content type will be json
                .content(initialValue)) // write in body
                .andExpect(status().isOk());


        mockMvc.perform(get("/Counters/").contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.initialValue").value(10));
    }

    @Test
    void incrementCounter() throws Exception {
        mockMvc.perform(put("/Counters/abc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abc").value(6));
        mockMvc.perform(put("/Counters/xyz").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.xyz").value(2));
    }

    @Test
    void decrementCounter() throws Exception {
        // bigger than one
        mockMvc.perform(delete("/Counters/abc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abc").value(4));
        // equals to one
        mockMvc.perform(delete("/Counters/xyz").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        // does not exist
        mockMvc.perform(delete("/Counters/whatever").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCounterValue() throws Exception {
        // get abc
        mockMvc.perform(get("/Counters/abc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abc").value(5));

        //get xyz
        mockMvc.perform(get("/Counters/xyz").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.xyz").value(1));

        // get a counter that does not exist
        mockMvc.perform(get("/Counters/whatever").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}