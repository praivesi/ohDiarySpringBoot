package com.tutorial.ohDiaraySpringBoot.service;

import com.tutorial.ohDiaraySpringBoot.dto.JobDTO;
import com.tutorial.ohDiaraySpringBoot.model.DayJob;
import com.tutorial.ohDiaraySpringBoot.model.DecadeJob;
import com.tutorial.ohDiaraySpringBoot.model.WeekJob;
import com.tutorial.ohDiaraySpringBoot.repository.DayJobRepository;
import com.tutorial.ohDiaraySpringBoot.repository.WeekJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DayJobService {
    @Autowired
    private WeekJobRepository weekJobRepository;
    @Autowired
    private DayJobRepository dayJobRepository;

    public JobDTO save(JobDTO jobDTO) {
        if (jobDTO.getJobType() != 4) return null;

        Optional<WeekJob> maybeParentJob = weekJobRepository.findById(jobDTO.getParentId());
        if (maybeParentJob.isPresent()) {
            DayJob newDayJob = new DayJob(jobDTO.getTitle(), jobDTO.getContent(), jobDTO.getFromTime(), jobDTO.getToTime(), maybeParentJob.get());
            DayJob entity = dayJobRepository.save(newDayJob);
            jobDTO.setId(entity.getId());
        } else {
            jobDTO = new JobDTO();
        }

        return jobDTO;
    }

    public JobDTO update(JobDTO dto, Long id) {
        Optional<DayJob> maybeEntity = dayJobRepository.findById(id);

        if (maybeEntity.isPresent()) {
            DayJob entity = maybeEntity.get();

            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());
            entity.setFromTime(dto.getFromTime());
            entity.setToTime(dto.getToTime());

            dayJobRepository.save(entity);
            dto.setId(id);
        } else {
            dto = new JobDTO();
        }

        return dto;
    }

    public JobDTO get(Long id) {
        JobDTO dto = new JobDTO();
        Optional<DayJob> maybeDayJob = dayJobRepository.findById(id);

        if (maybeDayJob.isPresent()) {
            DayJob entity = maybeDayJob.get();

            dto.setJobType(1);
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setContent(entity.getContent());
            dto.setFromTime(entity.getFromTime());
            dto.setToTime(entity.getToTime());
            dto.setParentId(entity.getWeekJob().getId());
        }

        return dto;
    }

    public Long delete(Long id) {
        try {
            dayJobRepository.deleteById(id);
        } catch (Exception e) {
            id = -1l;
        }

        return id;
    }
}
