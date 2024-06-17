/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-13 5:05:13 pm 
 * 
 */

package e63c.shenghao.ga;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author simsh
 *
 */
public interface MembershipRepository extends JpaRepository<Membership, Integer>{
	
	public Membership findByUsername(String username);
	
}
