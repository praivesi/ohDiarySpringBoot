package com.tutorial.ohDiaraySpringBoot.controller;

import com.tutorial.ohDiaraySpringBoot.dto.JobDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ScheduleApiControllerTest {

    ScheduleApiController scheduleApiController = new ScheduleApiController();

    @Test
    public void testNewJob(){
        // TODO: Refactor test codes to test REST Controller
    /*
        String uri = "/products";
        Product product = new Product();
        product.setId("3");
        product.setName("Ginger");

        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Product is created successfully");
      */

        // given
        JobDTO recvJobDTO = new JobDTO();
        recvJobDTO.setParentId(1L);
        recvJobDTO.setJobType(1); // Year Type
        recvJobDTO.setTitle("testTitle");
        recvJobDTO.setContent("testContent");
        recvJobDTO.setFromTime(new GregorianCalendar(2021, Calendar.JANUARY, 1).getTime());
        recvJobDTO.setToTime(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());

        // when
        JobDTO savedJobDTO = scheduleApiController.newJob(recvJobDTO);

        // then
        JobDTO dto = scheduleApiController.getJob(savedJobDTO.getId(), recvJobDTO.getJobType());
        Assertions.assertThat(dto.getTitle()).isEqualTo(recvJobDTO.getTitle());
    }

}
