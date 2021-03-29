package com.tutorial.ohDiaraySpringBoot.service;

import com.tutorial.ohDiaraySpringBoot.dto.JobDTO;
import com.tutorial.ohDiaraySpringBoot.model.MonthJob;
import com.tutorial.ohDiaraySpringBoot.model.YearJob;
import com.tutorial.ohDiaraySpringBoot.repository.MonthJobRepository;
import com.tutorial.ohDiaraySpringBoot.repository.YearJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthJobService {
    @Autowired
    private YearJobRepository yearJobRepository;
    @Autowired
    private MonthJobRepository monthJobRepository;

    public JobDTO save(JobDTO jobDTO) {
        if (jobDTO.getJobType() != 2) return null;

        Optional<YearJob> maybeParentJob = yearJobRepository.findById(jobDTO.getParentId());
        if (maybeParentJob.isPresent()) {
            MonthJob newMonthJob = new MonthJob(jobDTO.getTitle(), jobDTO.getContent(), jobDTO.getFromTime(), jobDTO.getToTime(), maybeParentJob.get());
            MonthJob entity = monthJobRepository.save(newMonthJob);
            jobDTO.setId(entity.getId());
        } else {
            jobDTO = new JobDTO();
        }

        return jobDTO;
    }

    public JobDTO get(Long id) {
        JobDTO dto = new JobDTO();
        Optional<MonthJob> maybeMonthJob = monthJobRepository.findById(id);

        if (maybeMonthJob.isPresent()) {
            MonthJob entity = maybeMonthJob.get();

            dto.setJobType(1);
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setContent(entity.getContent());
            dto.setFromTime(entity.getFromTime());
            dto.setToTime(entity.getToTime());
            dto.setParentId(entity.getYearJob().getId());
        }

        return dto;
    }

    public Long delete(Long id) {
        try {
            monthJobRepository.deleteById(id);
        } catch (Exception e) {
            id = -1l;
        }

        return id;
    }
}
