/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-03 3:21:51 pm 
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
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "home_page";
	}
	
	@GetMapping("/aboutUs")
	public String aboutUs() {
		return "aboutUs";
	}
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
}
