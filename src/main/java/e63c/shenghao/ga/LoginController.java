/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-17 6:56:34 pm 
 * 
 */

package e63c.shenghao.ga;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author simsh
 *
 */
@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
