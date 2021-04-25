package com.tutorial.Demian.service;

import com.tutorial.Demian.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private DesireService desireService;
    @Autowired
    private DecadeJobService decadeJobService;
    @Autowired
    private YearJobService yearJobService;
    @Autowired
    private MonthJobService monthJobService;

    public JobDTO save(JobDTO dto) {
        JobDTO response = new JobDTO();

        switch (dto.getJobType()) {
            case 0: // Decade
                response = decadeJobService.save(dto);
                break;

            case 1: // Year
                response = yearJobService.save(dto);
                break;

            case 2: // Month
                response = monthJobService.save(dto);
                break;
        }

        return response;
    }

    public JobDTO update(JobDTO dto, Long id, int jobType) {
        JobDTO response = new JobDTO();

        switch (jobType) {
            case 0: // Decade
                response = decadeJobService.update(dto, id);
                break;

            case 1: // Year
                response = yearJobService.update(dto, id);
                break;

            case 2: // Month
                response = monthJobService.update(dto, id);
                break;
        }

        return response;
    }

    public JobDTO get(Long id, int jobType) {
        JobDTO response = null;

        switch (jobType) {
            case 0: // Decade
                response = decadeJobService.get(id);
                break;

            case 1: // Year
                response = yearJobService.get(id);
                break;

            case 2: // Month
                response = monthJobService.get(id);
                break;
        }

        return response;
    }

    public Long delete(Long id, int jobType) {
        Long response = -1l;

        switch (jobType) {
            case 0: // Decade
                response = decadeJobService.delete(id);
                break;

            case 1: // Year
                response = yearJobService.delete(id);
                break;

            case 2: // Month
                response = monthJobService.delete(id);
                break;
        }

        return response;
    }
}
























