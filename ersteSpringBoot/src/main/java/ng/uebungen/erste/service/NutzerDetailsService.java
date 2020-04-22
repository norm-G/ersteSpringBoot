package ng.uebungen.erste.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ng.uebungen.erste.entity.Nutzer;
import ng.uebungen.erste.entity.NutzerRolle;
import ng.uebungen.erste.repository.NutzerRepository;


@Service
@Transactional(readOnly = true)
public class NutzerDetailsService implements UserDetailsService{
	
	@Autowired
	private NutzerRepository repository; 
		
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Nutzer nutzer= repository.findByName(username);
	
		NutzerDetails details = new NutzerDetails(nutzer,getRollen(nutzer));

		return details;		
	}
	
	
	
	
	//wandelt die bezeichnung der Rollen in Authoritys um
	private Collection<GrantedAuthority> getRollen(Nutzer nutzer){
		
		List<String> authority = new ArrayList<>();
		
		for(NutzerRolle rolle : nutzer.getRollen()) {
			authority.add(rolle.getBezeichnung());
		}
		
		String[] stringArray = new String[authority.size()];
		
		authority.toArray(stringArray);
		
		Collection<GrantedAuthority> auth = AuthorityUtils.createAuthorityList(stringArray);
		
		
		
		return auth;
	}

}
