package net.javaguides.springboot.controller;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ch.qos.logback.core.hook.DelayingShutdownHook;
import net.javaguides.springboot.model.Receiver;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);
	}

	// display list of employees
	@GetMapping("/adminPanel")
	public String vieAdminPage(Model model) {
	   return 	findPaginated(1, "firstName", "asc", model);
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		int codeLength =12;
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < codeLength; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		System.out.println(output);
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("hello", output);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		try{

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof UserDetails) {
				String username = ((UserDetails)principal).getUsername();
				employee.setUser(username);
			} else {
				String username = principal.toString();
				employee.setUser(username);

			}
			employee.setState("In Process");
			employee.setCode(employee.getCode().trim());
			LocalDateTime lt = LocalDateTime.now();
			employee.setDate(lt);
			employeeService.saveEmployee(employee);
		}
		catch(Exception e){
			e.printStackTrace();

		}
		return "redirect:/";

	}

	@PostMapping("/saveEmployee1")
	public String saveEmployee1(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		try {
			long id = employee.getId();
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username1 = ((UserDetails) principal).getUsername();
			LocalDateTime lt1 = LocalDateTime.now();
			try {
				employeeRepository.update(employee.getFirstName(),employee.getLastName(),employee.getAmount(),employee.getCurrency(),employee.getCode(),lt1, username1, id);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();

		}
		return "redirect:/";

	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {

		int pageSize = 10;

		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("fdds",page.getSize());
		model.addAttribute("totalAmount",employeeRepository.findAmountUSD());
		model.addAttribute("totalAmount1",employeeRepository.findAmountKGS());
		model.addAttribute("totalAmount2",employeeRepository.findAmountEURO());


		model.addAttribute("findAmountofInprocess",employeeRepository.findAmountofInprocess());


		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
	@GetMapping("/takeMoney")
	public String takeMoney(Model model){
		model.addAttribute("receiver",new Receiver());
		return "receiver";
	}

	@GetMapping("/logout")
	public String getLogoutPage(HttpServletRequest request, HttpServletResponse response){

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null)
			new SecurityContextLogoutHandler().logout(request, response, authentication);

		return "redirect:/";
	}
	@PostMapping("/takeMoney")
	public String takeMoneySubmit(@ModelAttribute Receiver receiver,Model model,RedirectAttributes redirectAttributes){
		try {
			String boom = employeeRepository.selectCode(receiver.getCode());
			employeeRepository.takeMoney(receiver.getName(), receiver.getSurname(), receiver.getCode());
			if(receiver.getCode().equals(boom)){
			model.addAttribute("correct","Successfully taken");}
			else {
				model.addAttribute("incorrect","Incorrect id or code");
			}

		} catch (Exception e) {
			model.addAttribute("incorrect","Incorrect id or code");
			e.printStackTrace();
		}
		return "receiver";

	}
	@RequestMapping("/hm")
	public String viewHomePage(Model model, @Param("keyword") String keyword) {
		try {
			List<Employee> listProducts = employeeService.listAll(keyword);
			model.addAttribute("listEmployees", listProducts);
			model.addAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "index";
	}

}
