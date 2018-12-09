package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job jobForGivenId = jobData.findById(id);
        model.addAttribute("jobForGivenId", jobForGivenId);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid JobForm jobForm, Errors errors, Model model) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";
        }
        else {

            Employer employerForId = null;

            for( Employer employer: jobForm.getEmployers()){
                if(employer.getId() == jobForm.getEmployerId()){
                    employerForId = employer;
                }
            }

            Location locationForId = null;

            for( Location location: jobForm.getLocations()) {
                if (location.getId() == jobForm.getLocationId()) {
                    locationForId = location;
                }
            }
            PositionType positionTypeForId = null;

            for(PositionType positionType: jobForm.getPositionTypes()) {
                if(positionType.getId() == jobForm.getPositionTypeId()) {
                    positionTypeForId = positionType;
                }
            }

            CoreCompetency coreCompetencyForId = null;

            for(CoreCompetency coreCompetency: jobForm.getCoreCompetencies()){
                if(coreCompetency.getId() == jobForm.getCoreCompetencyId()) {
                    coreCompetencyForId = coreCompetency;
                }
            }

            Job newJob = new Job(jobForm.getName(), employerForId, locationForId, positionTypeForId, coreCompetencyForId);

            jobData.add(newJob);
            model.addAttribute("jobForGivenId", newJob);

            return "job-detail";
        }
    }
}
