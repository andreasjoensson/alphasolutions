package com.ecomcph.inc.Controllers;

import com.ecomcph.inc.Services.Employees;
import com.ecomcph.inc.Services.Project;
import com.ecomcph.inc.Services.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class TaskController {
    private Task task = new Task();
    private Project projectService = new Project();
    private Employees employees = new Employees();

    @GetMapping(value={"/deleteTask/{id}"})
    public String deleteSpecificTask(@PathVariable String id) throws SQLException {
        task.deleteSpecificTask(Integer.parseInt(id));
        return "redirect:/";
    }

    //Vis Form til at oprette Opgave
    @GetMapping("/createTask")
    public String renderTask(Model model){
        model.addAttribute("projects",projectService.showProjectInArraylist());
        model.addAttribute("medarbejderList",employees.showEmployeeInArraylist());
        return "AddTask.html";
    }

    // Tilføj en del opgave til et specifikt projekt
    @PostMapping(value={"/addtask"})

    // returner delopgavens id fra databasen, så brugeren kan se det på siden.
    public String addTask(@RequestParam("navn") String navn, @RequestParam("deadline") String deadline, @RequestParam("employees") String employees, @RequestParam(name = "projektId", required = false) String projektID, @RequestParam("taskDescription") String taskDescription) throws SQLException {
        String[] employeesListe = employees.split(",");
        int taskID = task.addTask(navn,deadline,employeesListe,taskDescription);
        task.assignTaskToEmployee(taskID, employeesListe);
        projectService.assignTaskToProject(taskID,Integer.parseInt(projektID));
        return "redirect:/";
    }

    @GetMapping(value={"/seeTasks/{id}"})
    public String seeTasks(Model model, @PathVariable String id){
        model.addAttribute("tasks", task.printTasksFromProject(Integer.parseInt(id)));
        model.addAttribute("taskID", Integer.parseInt(id));
        return "tasks.html";
    }

    @GetMapping(value={"/seeTask/{id}"})
    public String seeTask(Model model, @PathVariable String id){
        model.addAttribute("taskEmployees", employees.getEmployeesFromTasks(Integer.parseInt(id)));
        return "task.html";
    }
}

