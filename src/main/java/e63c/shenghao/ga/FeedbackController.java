/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2024-Jan-16 11:49:27 pm 
 * 
 */

package e63c.shenghao.ga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepo;
    
    @GetMapping("/contactUs")
    public String showContactUs(Model model) {
    	
    	List<Feedback> listFeedbacks = feedbackRepo.findAll();

		model.addAttribute("listFeedbacks" ,listFeedbacks);
        return "contactUs"; 
    }

    @PostMapping("/contactUs/save")
    public String saveFeedback(@ModelAttribute("feedback") Feedback feedback) {
        feedbackRepo.save(feedback);
        return "redirect:/contactUs"; 
    }
    
    
    @GetMapping("/feedbacks")
    public String viewFeedbacks(Model model) {
    	List<Feedback> listFeedbacks = feedbackRepo.findAll();
    	
    	model.addAttribute("listFeedbacks", listFeedbacks);
    	return "view_feedbacks";
    }
    
    // For adding feedback via the admin side
    @GetMapping("/feedbacks/add")
    public String addFeedback(Model model) {
    	model.addAttribute("feedback", new Feedback());
    	return "add_feedback";
    }
    
    @PostMapping("/feedbacks/save")
	public String saveAddedFeedback(Feedback feedback) {
		feedbackRepo.save(feedback);
		return "redirect:/feedbacks";
	}
    
    // For viewing each individual feedback
    @GetMapping("/feedbacks/{id}") 
    public String viewSingleFeedback(@PathVariable("id") Integer id, Model model) {
    	Feedback feedback = feedbackRepo.getReferenceById(id);
    	model.addAttribute("feedback", feedback);
    	
    	return "view_single_feedback";
    }
    
    // For editing any feedback (Via the admin side)
    @GetMapping("/feedbacks/edit/{id}")
    public String editFeedback(@PathVariable("id") Integer id, Model model) {
    	Feedback feedback = feedbackRepo.getReferenceById(id);
    	model.addAttribute("feedback", feedback);
    	
    	return "edit_feedback";
    }
    
    @PostMapping("/feedbacks/edit/{id}")
    public String saveEditedFeedback(@PathVariable("id") Integer id, Feedback feedback) {
    	feedbackRepo.save(feedback);
    	return "redirect:/feedbacks";
    }
    
    // For deleting any feedback (Via the admin side)
    @GetMapping("/feedbacks/delete/{id}")
    public String deleteFeedback(@PathVariable("id") Integer id) {
    	feedbackRepo.deleteById(id);
    	
    	return "redirect:/feedbacks";
    }

}