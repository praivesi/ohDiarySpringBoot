package com.tutorial.Demian.dto;

import com.tutorial.Demian.model.Decade;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.tutorial.Demian.model.Decade.DECADE_JOB_DEFAULT_ID;
import static com.tutorial.Demian.model.Desire.DESIRE_DEFAULT_ID;

@Data
public class DecadeDTO {
    private Long id;
    private Long desireId;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date fromTime;
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date toTime;

    public DecadeDTO(){
        this.id = DECADE_JOB_DEFAULT_ID;
        this.desireId = DESIRE_DEFAULT_ID;
        this.title = "";
        this.content = "";
        this.fromTime = null;
        this.toTime = null;
    }

    public Decade getEntity(){
        Decade entity = new Decade();

        entity.setId(this.id);
        entity.setTitle(this.title);
        entity.setContent(this.content);
        entity.setFromTime(this.fromTime);
        entity.setToTime(this.toTime);

        return entity;
    }

    public static DecadeDTO of(Decade decade){
        DecadeDTO dto = new DecadeDTO();

        dto.id = decade.getId();
        dto.desireId = decade.getDesire().getId();
        dto.title = decade.getTitle();
        dto.content = decade.getContent();
        dto.fromTime = decade.getFromTime();
        dto.toTime = decade.getToTime();

        return dto;
    }
}
