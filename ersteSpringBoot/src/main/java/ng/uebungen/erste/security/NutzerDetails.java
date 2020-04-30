package ng.uebungen.erste.security;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ng.uebungen.erste.entity.Nutzer;



public class NutzerDetails implements UserDetails{

	
	
	private static final long serialVersionUID = 1L;
	
	
	private Nutzer nutzer;
	
	private Collection<? extends GrantedAuthority> autority = new HashSet<>();
	
	

	public NutzerDetails(Nutzer nutzer, Collection<? extends GrantedAuthority> autority) {
		this.nutzer = nutzer;
		this.autority = autority;
	}

	

	@Override
	public String getUsername() {
		
		return this.nutzer.getName();
	}

	@Override
	public String getPassword() {
		
		return this.nutzer.getPassword();
	}
	
	@Override
//	@Transactional(readOnly = true)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.autority;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
