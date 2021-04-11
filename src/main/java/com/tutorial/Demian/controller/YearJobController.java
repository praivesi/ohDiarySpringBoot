package com.tutorial.Demian.controller;

import com.tutorial.Demian.dto.DecadeJobDTO;
import com.tutorial.Demian.dto.YearJobDTO;
import com.tutorial.Demian.dto.YearPageDTO;
import com.tutorial.Demian.model.DecadeJob;
import com.tutorial.Demian.model.Desire;
import com.tutorial.Demian.model.YearJob;
import com.tutorial.Demian.repository.DesireRepository;
import com.tutorial.Demian.repository.YearJobRepository;
import com.tutorial.Demian.service.YearJobService;
import com.tutorial.Demian.validator.YearJobValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/years")
public class YearJobController {
    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private YearJobRepository yearJobRepository;
    @Autowired
    private YearJobService yearJobService;
    @Autowired
    private YearJobValidator yearJobValidator;

    @GetMapping("/page")
    public String year(Model model) {
        Calendar startCal = new GregorianCalendar();
        startCal.set(Calendar.YEAR, startCal.get(Calendar.YEAR) - 2);

        List<YearPageDTO> yearPageDTOs = yearJobService.get(startCal.getTime());

        model.addAttribute("yearPageDTOs", yearPageDTOs);
        model.addAttribute("startDate", startCal.getTime());

        return "/schedule/year_page";
    }

    @GetMapping("/form")
    public String jobForm(Model model, @RequestParam(required = true) Long desireId, @RequestParam(required = false) Long jobId) {
        Optional<Desire> mayDesire = desireRepository.findById(desireId);

        if (!mayDesire.isPresent()) {
            // TODO: DO more reasonable exception handling
            return "redirect:/years/page";
        }

        if (jobId == null) {
            YearJobDTO yearDTO = new YearJobDTO();
            yearDTO.setDesireId(desireId);
            model.addAttribute("year", yearDTO);
        } else {
            YearJob year = yearJobRepository.findById(jobId).orElse(null);
            model.addAttribute("year", YearJobDTO.of(year));
        }

        model.addAttribute("desire", mayDesire.get());

        return "/schedule/year_form";
    }

    @PostMapping("/form")
    public String postJobForm(Model model, @Valid YearJobDTO yearDTO, BindingResult bindingResult,
                                    Authentication authentication) {
        Optional<Desire> mayDesire = desireRepository.findById(yearDTO.getDesireId());
        if (!mayDesire.isPresent()) {
            // TODO: DO more reasonable exception handling
            return "redirect:/years/page";
        }

        model.addAttribute("desire", mayDesire.get());

        yearJobValidator.validate(yearDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/schedule/year_form";
        }

        YearJob year = yearDTO.getEntity();
        year.setDesire(mayDesire.get());

        yearJobRepository.save(year);

        return "redirect:/years/page";
    }
}