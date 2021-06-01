package com.ecomcph.inc.Controllers;
import com.ecomcph.inc.Models.Employee;
import com.ecomcph.inc.Services.Employees;
import com.ecomcph.inc.Services.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class EmployeeController {
    private Employees employees = new Employees();

    //Returnere den form man skal bruge til at oprette en ny medarbejder
    @GetMapping("/createEmployee")
    public String renderCreateEmployee() {
        return "createEmployee.html";
    }

    //Post Request til at tilføje en medarbejder.
    @PostMapping(value={"/addEmployee"})
    public String createEmployee(@RequestParam("name") String employeeName, @RequestParam("age") int age, @RequestParam("e-mail") String email, @RequestParam("position") String position, @RequestParam("salary") int salary) throws SQLException {
        employees.createEmployee(employeeName,age,email,position,salary);
        return "redirect:/";
    }

    //HTML side som viser en oversigt over alle medarbejdere
    @GetMapping("/employees")
    public String listofEmployees(Model model){
        model.addAttribute("medarbejdere",employees.showEmployeeInArraylist());
        return "employees.html";
    }
}
