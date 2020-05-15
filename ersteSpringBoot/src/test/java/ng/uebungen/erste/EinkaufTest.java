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

import ng.uebungen.erste.entity.Einkauf;
import ng.uebungen.erste.entity.Nutzer;



@SpringBootTest
@AutoConfigureMockMvc
public class EinkaufTest {
	
	
	@Autowired
	private MockMvc mocki;
	
	
	@Test
	void testGetAllEinkauf() throws Exception {
		
		mocki.perform(get("/einkauf").accept(MediaType.APPLICATION_JSON))
									.andDo(print())
									.andExpect(status().isOk())
									.andExpect(jsonPath("$._embedded.einkaufs[0].id", is(1)));
	}
	
	@Test
	void testGetOneEinkauf() throws Exception {
		
		mocki.perform(get("/einkauf/{id}",1).accept(MediaType.APPLICATION_JSON))
								.andDo(print())
								.andExpect(status().isOk())
								.andExpect(jsonPath("$.id", is(1)));
	}
	
	/* Erstellt einen neuen Einkauf als Ausgabe wird der neue Einkauf erwartet
	 * als zweites wird ueberprueft ob die Menge der Einkaeufe gestiegen ist
	 */
	@Test
	void testPostEinkauf() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		Einkauf einkauf = new Einkauf(new Nutzer("bob","blub"));
		String jsonEinkauf = mapper.writeValueAsString(einkauf);
		
			
		mocki.perform(post("/einkauf").contentType(MediaType.APPLICATION_JSON)
										.content(jsonEinkauf))
										.andExpect(status().isCreated())
										.andExpect(jsonPath("$.id", is(2)));
										
		
		mocki.perform(get("/einkauf")).andExpect(jsonPath("$._embedded.einkaufs", hasSize(2)));
	}
	
}
