/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-13 5:10:37 pm 
 * 
 */

package e63c.shenghao.ga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

/**
 * @author simsh
 *
 */
@Controller
public class MembershipController {
	
	@Autowired
	private MembershipRepository memberRepository;
	
	@GetMapping("/members")
	public String viewMembers(Model model) {
		List<Membership> listMembers = memberRepository.findAll();

		model.addAttribute("listMembers" ,listMembers);
		return "view_members";
	}
	
	// Method to serve the registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    	model.addAttribute("member", new Membership());
        return "register";
    }
	
	// Method to handle the registration form submission
    @PostMapping("/register/save")
    public String registerMember(@Valid @ModelAttribute("member") Membership member, BindingResult bindingResult, RedirectAttributes redirectAttribute, Model model) {
    	
    	if (bindingResult.hasErrors()) {
    		List<Membership> listMembers = memberRepository.findAll();
    		model.addAttribute("listMembers" ,listMembers);
            return "register";
        }
    	
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		member.setPassword(encodedPassword);
		member.setRole("ROLE_USER");
		
		memberRepository.save(member);
		
		redirectAttribute.addFlashAttribute("success", "Member registered!");
        return "redirect:/login";
    }
	
	@GetMapping("/members/add")
	public String addMember(Model model) {
		model.addAttribute("member", new Membership());
		
		return "add_member";
	}
	
	@PostMapping("/members/save")
	public String saveMember(@Valid @ModelAttribute("member") Membership member, BindingResult bindingResult, RedirectAttributes redirectAttribute, Model model) {
		
		if(bindingResult.hasErrors()) {
			List<Membership> listMembers = memberRepository.findAll();
			model.addAttribute("listMembers" ,listMembers);
			return "add_member";
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		member.setPassword(encodedPassword);
		member.setRole("ROLE_USER");
		
		memberRepository.save(member);
		
		redirectAttribute.addFlashAttribute("success", "Member registered!");
		return "redirect:/members";
	}
	
	// Edit Member + Save
	@GetMapping("/members/edit/{id}")
	public String editMember(@PathVariable("id") Integer id, Model model) {
		Membership member = memberRepository.getReferenceById(id);
		model.addAttribute("member", member);
		
		return "edit_member";
	}
	
	@PostMapping("/members/edit/{id}")
	public String saveUpdatedMember(@Valid @ModelAttribute("member") Membership member, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "edit_member";
		}
		
		member.setRole("ROLE_USER");
		memberRepository.save(member);
		return "redirect:/members";
	}
	
	
	// Delete Member
	@GetMapping("/members/delete/{id}")
	public String deleteMember(@PathVariable("id") Integer id) {
		memberRepository.deleteById(id);
		return "redirect:/members";
	}
	
}
