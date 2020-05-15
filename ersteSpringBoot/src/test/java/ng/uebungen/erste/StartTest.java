package ng.uebungen.erste;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class StartTest {
	
	
	@Autowired
	private MockMvc mocki;
	
	
	
	@Test
	void testHello() throws Exception {
		mocki.perform(get("/").accept(MediaType.APPLICATION_JSON))
							.andExpect(jsonPath("$.content",is("Hallo World")));
				
		//.andExpect(content().json("Komplettes JSON"));
	}
	

	
}
