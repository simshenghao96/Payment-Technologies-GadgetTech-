/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-16 1:35:30 pm 
 * 
 */

package e63c.shenghao.ga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author simsh
 *
 */
public class MembershipDetailsService implements UserDetailsService{
	
	@Autowired
	private MembershipRepository memberRepository;
	
	@Override
	public MembershipDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Membership member = memberRepository.findByUsername(username);
		
		if(member == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		
		return new MembershipDetails(member);
	}

}
