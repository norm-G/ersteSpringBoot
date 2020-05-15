package ng.uebungen.erste;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ng.uebungen.erste.entity.Nutzer;

@SpringBootTest
@AutoConfigureMockMvc
public class NutzerTest {

	
	@Autowired
	private MockMvc mocki;
	
	
	@Test
	void testGetAllNutzer() throws Exception {
		
		mocki.perform(get("/nutzer").accept(MediaType.APPLICATION_JSON))
									.andDo(print())
									.andExpect(status().isOk())
									.andExpect(jsonPath("$._embedded.nutzers[0].name", is("bob")))
									.andExpect(jsonPath("$._embedded.nutzers[1].name", is("nob")));
	}
	
	@Test
	void testGetOneNutzer() throws Exception {
		
		mocki.perform(get("/nutzer/{id}",1).accept(MediaType.APPLICATION_JSON))
								.andDo(print())
								.andExpect(status().isOk())
								.andExpect(jsonPath("$.id", is(1)))
								.andExpect(jsonPath("$.name", is("bob")));
	}
	
	/* Erstellt einen neuen Nutzer als Ausgabe wird der neue Nutzer erwartet
	 * als zweites wird ueberprueft ob die Menge der Nutzer gestiegen ist
	 */
	@Test
	void testPostNutzer() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		Nutzer nutzer = new Nutzer("zob","1234");
		String jsonNutzer = mapper.writeValueAsString(nutzer);
		
			
		mocki.perform(post("/nutzer").contentType(MediaType.APPLICATION_JSON)
										.content(jsonNutzer))
										.andExpect(status().isCreated())
										.andExpect(jsonPath("$.name", is("zob")));
										
		
		mocki.perform(get("/nutzer")).andExpect(jsonPath("$._embedded.nutzers", hasSize(3)));
	}
	
	
}
