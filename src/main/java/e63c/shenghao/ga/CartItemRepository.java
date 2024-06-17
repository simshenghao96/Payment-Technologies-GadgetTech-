/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2024-Jan-23 11:10:40 am 
 * 
 */

package e63c.shenghao.ga;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author simsh
 *
 */
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	
	public List<CartItem> findByMembershipId(int membershipId);
	
	public CartItem findByMembershipIdAndGadgetId(int membershipId, int gadgetId);
}
