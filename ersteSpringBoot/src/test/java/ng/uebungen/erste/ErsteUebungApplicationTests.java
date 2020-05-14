package ng.uebungen.erste;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ng.uebungen.erste.entity.Artikel;








@SpringBootTest
@AutoConfigureMockMvc
class ErsteUebungApplicationTests {
	
	
	@Autowired
	private MockMvc mocki;
	
	
	
	@Test
	void testHello() throws Exception {
		mocki.perform(get("/").accept(MediaType.APPLICATION_JSON))
							.andExpect(jsonPath("$.content",is("Hallo World")));
				
		//.andExpect(content().json("Komplettes JSON"));
	}
	
	
	
	@Test
	void testGetAllArtikel() throws Exception {
		
		mocki.perform(get("/artikel").accept(MediaType.APPLICATION_JSON))
									.andDo(print())
									.andExpect(status().isOk())
									.andExpect(jsonPath("$._embedded.artikels[0].name", is("Stuhl")))
									.andExpect(jsonPath("$._embedded.artikels[1].name", is("tisch")));
	}
	
	
	@Test
	void testGetEinArtikel() throws Exception {
		
		mocki.perform(get("/artikel/{id}",1).accept(MediaType.APPLICATION_JSON))
								.andDo(print())
								.andExpect(status().isOk())
								.andExpect(jsonPath("$.id", is(1)));
	}
	
	/* Erstellt einen neuen Artikel als Ausgabe wird der neue Artikel erwartet
	 * als zweites wird ueberprueft ob die Menge der Artikel gestiegen ist
	 */
	@Test
	void testPostArtikel() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		Artikel artikel = new Artikel("Sofa",20);
		
		String jsonArtikel = mapper.writeValueAsString(artikel);
		//erstellt einen neuen Artikel	
		mocki.perform(post("/artikel").contentType(MediaType.APPLICATION_JSON)
										.content(jsonArtikel))
										.andExpect(status().isCreated())
										.andExpect(jsonPath("$.name", is("Sofa")));
		mocki.perform(get("/artikel")).andExpect(jsonPath("$._embedded.artikels", hasSize(3)));
	}
	
	
	
	
}
