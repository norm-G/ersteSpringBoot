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

import ng.uebungen.erste.entity.NutzerRolle;



@SpringBootTest
@AutoConfigureMockMvc
public class RollenTest {
	
	@Autowired
	MockMvc mocki;
	
	@Test
	void testGetAllRollen() throws Exception {
		
		mocki.perform(get("/rollen").accept(MediaType.APPLICATION_JSON))
									.andDo(print())
									.andExpect(status().isOk())
									.andExpect(jsonPath("$._embedded.nutzerRolles[0].bezeichnung", is("Admin")))
									.andExpect(jsonPath("$._embedded.nutzerRolles[1].bezeichnung", is("User")));
	}
	
	@Test
	void testGetOneRolle() throws Exception {
		
		mocki.perform(get("/rollen/{id}",1).accept(MediaType.APPLICATION_JSON))
								.andDo(print())
								.andExpect(status().isOk())
								.andExpect(jsonPath("$.id", is(1)))
								.andExpect(jsonPath("$.bezeichnung", is("Admin")));
	}
	
	/* Erstellt einen neue Rolle als Ausgabe wird der neue Rolle erwartet
	 * als zweites wird ueberprueft ob die Menge der Rollen gestiegen ist
	 */
	@Test
	void testPostRolle() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		NutzerRolle rolle = new NutzerRolle("Azubi");
		String jsonRolle = mapper.writeValueAsString(rolle);
		
			
		mocki.perform(post("/rollen").contentType(MediaType.APPLICATION_JSON)
										.content(jsonRolle))
										.andExpect(status().isCreated())
										.andExpect(jsonPath("$.bezeichnung", is("Azubi")));
		
		mocki.perform(get("/rollen")).andExpect(jsonPath("$._embedded.nutzerRolles", hasSize(3)));
	}
	
	
}
