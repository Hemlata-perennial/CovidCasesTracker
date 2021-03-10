package com.hemlata.corona;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Controller
public class CovidController {
    DataFetcher df=new DataFetcher();

    @GetMapping("/")
    public ModelAndView covidhome(ModelAndView modelAndView) throws IOException, InterruptedException
    {
        //call datafecher method to get world data
        String responseJsonString=df.worldData();
        ObjectMapper mapper = new ObjectMapper();

        //parse JSON to display All cases

        Map<String,Object> map=mapper.readValue(responseJsonString, Map[].class)[0];
        System.out.println("JSON parsed to map");
        String ActiveCases,TotalDeath,TotalCases,TotalRecovered;
        TotalCases= (String) map.get("Total Cases_text");
        ActiveCases= (String) map.get("Active Cases_text");
        TotalDeath= (String) map.get("Total Deaths_text");
        TotalRecovered= (String) map.get("Total Recovered_text");
        modelAndView.addObject("total",TotalCases);
        modelAndView.addObject("active",ActiveCases);
        modelAndView.addObject("recovered",TotalRecovered);
        modelAndView.addObject("deaths",TotalDeath);

        //Parse JSON Data to display CountryWise Data
        String cntryData=df.CountryWiseData();
        cntryData=cntryData.replace("null","0");
        Map<String,Object> cntryMap=mapper.readValue(cntryData,Map.class);
        Map<String, Object> resultmap= (Map<String, Object>) cntryMap.get("data");

        ArrayList<String> data=(ArrayList<String>) resultmap.get("covid19Stats");


        Iterator itr=data.iterator();

        Iterable<Object> iterable = (Iterable<Object>) StreamSupport.stream(Spliterators.spliteratorUnknownSize(itr, 0),false).collect(Collectors.toList());
        modelAndView.addObject("cntryData",iterable);
        modelAndView.setViewName("CovidHome");
        return modelAndView;
    }
    @GetMapping("/RestTest")
    public ResponseEntity<Object> JsonOp() throws IOException, InterruptedException {
        String cntryData=df.CountryWiseData();
        ObjectMapper mapper=new ObjectMapper();
        cntryData=cntryData.replace("null","0");
        Map<String,Object> cntryMap=mapper.readValue(cntryData,Map.class);
        Map<String, Object> resultmap= (Map<String, Object>) cntryMap.get("data");

        return new ResponseEntity<Object>(resultmap, HttpStatus.OK);
    }
}