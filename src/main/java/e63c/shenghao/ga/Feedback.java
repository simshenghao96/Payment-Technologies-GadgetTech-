/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2024-Jan-16 11:28:37 pm 
 * 
 */

package e63c.shenghao.ga;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotEmpty(message="Name cannot be empty!")
    @Size(min=5, max=50, message="Gadget name length must be between 5 and 50 characters!")
    private String name;
    
    @NotEmpty(message="Name cannot be empty!")
    @Pattern(regexp=".+@.+\\..+", message="Please enter a valid email address")
    private String email;
    
    @NotEmpty(message="Name cannot be empty!")
    @Size(min=25, max=500, message="Gadget name length must be between 25 and 500 characters!")
    private String message;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
