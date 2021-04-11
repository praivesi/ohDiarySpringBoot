package com.tutorial.Demian.service;

import com.tutorial.Demian.dto.JobDTO;
import com.tutorial.Demian.model.Desire;
import com.tutorial.Demian.model.WeekJob;
import com.tutorial.Demian.repository.DesireRepository;
import com.tutorial.Demian.repository.WeekJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeekJobService {
    @Autowired
    private DesireRepository desireRepository;
    @Autowired
    private WeekJobRepository weekJobRepository;

    public JobDTO save(JobDTO jobDTO) {
        if (jobDTO.getJobType() != 3) return null;

        Optional<Desire> maybeParentJob = desireRepository.findById(jobDTO.getParentId());
        if (maybeParentJob.isPresent()) {
            WeekJob newWeekJob = new WeekJob(jobDTO.getTitle(), jobDTO.getContent(), jobDTO.getFromTime(), jobDTO.getToTime(), maybeParentJob.get());
            WeekJob entity = weekJobRepository.save(newWeekJob);
            jobDTO.setId(entity.getId());
        } else {
            jobDTO = new JobDTO();
        }

        return jobDTO;
    }

    public JobDTO update(JobDTO dto, Long id) {
        Optional<WeekJob> maybeEntity = weekJobRepository.findById(id);

        if (maybeEntity.isPresent()) {
            WeekJob entity = maybeEntity.get();

            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());
            entity.setFromTime(dto.getFromTime());
            entity.setToTime(dto.getToTime());

            weekJobRepository.save(entity);
            dto.setId(id);
        } else {
            dto = new JobDTO();
        }

        return dto;
    }

    public JobDTO get(Long id) {
        JobDTO dto = new JobDTO();
        Optional<WeekJob> maybeWeekJob = weekJobRepository.findById(id);

        if (maybeWeekJob.isPresent()) {
            WeekJob entity = maybeWeekJob.get();

            dto.setJobType(1);
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setContent(entity.getContent());
            dto.setFromTime(entity.getFromTime());
            dto.setToTime(entity.getToTime());
            dto.setParentId(entity.getDesire().getId());
        }

        return dto;
    }

    public Long delete(Long id) {
        try {
            weekJobRepository.deleteById(id);
        } catch (Exception e) {
            id = -1l;

        }

        return id;
    }
}
