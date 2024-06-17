/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-05 4:32:56 pm 
 * 
 */

package e63c.shenghao.ga;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author simsh
 *
 */
@Configuration
public class Config implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
	}

}
