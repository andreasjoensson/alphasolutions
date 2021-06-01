package com.ecomcph.inc.Controllers;

import com.ecomcph.inc.Models.ProjectModel;
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
import java.util.ArrayList;

@Controller
public class ProjectController {
    private Project projectService = new Project();

    //Vis Form til at oprette projekt
    @GetMapping("/createProject")
    public String renderCreateProject() {
        return "createProject.html";
    }

    //Vis Form til at updatere projekt
    @GetMapping("/editProject")
    public String renderUpdateProject(Model model) {
        ArrayList<ProjectModel> ProjectList = projectService.showProjectInArraylist();
        model.addAttribute("projects",ProjectList);
        return "editProject.html";
    }

    //Vis Liste af igangsatte projekter
    @GetMapping("/projects")
    public String renderProjekter(Model model)
    {
        //VIRKER
        ArrayList <ProjectModel> ProjektListe = projectService.showProjectInArraylist();
        model.addAttribute("projekter",ProjektListe);
        return "projects.html";
    }

    @GetMapping(value={"/project/{id}"})
    public String renderSpecificProject(Model model, @PathVariable String id) throws SQLException {
        model.addAttribute("project",projectService.showSpecificProject(Integer.parseInt(id)));
        return "project.html";
    }

    @GetMapping(value={"/deleteProject/{id}"})
    public String deleteSpecificProject(@PathVariable String id) throws SQLException {
        projectService.deleteProject(Integer.parseInt(id));
        return "redirect:/";
    }

    // Tilføj et projekt
    @PostMapping(value={"/addProject"})
    public String createProjekt(@RequestParam("name") String projectName, @RequestParam("deadline") String deadline, @RequestParam("projectRevenue") int projectRevenue) throws SQLException {
        int id = projectService.createProject(projectName,deadline,projectRevenue);
        return  "redirect:/project/"+id;
    }

    // Opdatere et projekt
    @PostMapping(value={"/updateProject"})
    //returner projektets id fra databasen, så brugeren kan se det på siden.
    public String updateProjekt(@RequestParam("name") String projectName,@RequestParam("deadline") String deadline, @RequestParam("projectRevenue") int projectRevenue, @RequestParam("pStatus") int status, @RequestParam("projectID") int projectID) throws SQLException {
        projectService.updateProject(projectName,deadline,projectRevenue,status,projectID);
        return "redirect:/";
    }
}
