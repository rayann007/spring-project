package com.covoiturage.covoiturage.web_service;

import com.covoiturage.covoiturage.controller.UserController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class API_EXT_INE {

    Logger logger = LoggerFactory.getLogger(UserController.class);



    public  String getJson(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getListUsersAPI() throws MalformedURLException, JsonProcessingException {
        URL url=new URL("https://c363cd03-adfa-4394-8a07-3a0a269acdf5.mock.pstmn.io/numeroEtudiant");
        String EtudiantsAPI=this.getJson(url);

                ObjectMapper mapper = new ObjectMapper();
        List<Integer> listUsersAPI = Arrays.asList(mapper.readValue(EtudiantsAPI, Integer[].class));
        return listUsersAPI;
    }



    public  List<Double> coordinateAPI(String origine, String destionation) throws MalformedURLException, JsonProcessingException {
        //URL url=new URL("https://fr.distance24.org/route.json?stops="+origine+"|"+destionation+"");
        //String reponse= this.getJson(url);

        //Map<String, ArrayList<Map<String,Double>>>  map = new ObjectMapper().readValue(reponse, HashMap.class);


        double latutude1= 33.5;//map.get("stops").get(0).get("latitude");
        double longitude1=40.5;//map.get("stops").get(0).get("longitude");
        double latutude2=35.5;//map.get("stops").get(1).get("latitude");
        double longitude2=37.5;//map.get("stops").get(1).get("longitude");

        List<Double> coords= new ArrayList<>();

        coords.add(latutude1);
        coords.add(longitude1);
        coords.add(latutude2);
        coords.add(longitude2);


        return coords;


    }


    public String dureeAPI(double latitude1,double longitude1, double latitude2,double longitude2) throws JsonProcessingException {

//https://api.radar.io/v1/route/matrix?origins=49.119666,6.176905&destinations=49.470163,5.930146&mode=car&units=imperial

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Authorization","prj_live_pk_a3274ce3fa36801a7bda77159ad771de9536cd0a");
        //personJsonObject.put("userId",annonce.getUserId());


        String createPersonUrl = "https://api.radar.io/v1/route/matrix?origins="+latitude1+","+longitude1+"&destinations="+latitude2+","+longitude2+"&mode=car&units=metric";



        HttpEntity<String> request = new HttpEntity<String>(  headers);
        //String personResultAsJsonStr =restTemplate.exchange(createPersonUrl, request, String.class);//C'est ça qui post


        ResponseEntity<HashMap> result = restTemplate.exchange(createPersonUrl, HttpMethod.GET, request, HashMap.class);

        Map<String,ArrayList<ArrayList<Map<String,Map<String,String>>>>> list2=result.getBody();


        String duree=list2.get("matrix").get(0).get(0).get("duration").get("text");
        return  duree;


    }





}


//https://api.radar.io/v1/route/matrix?origins=49.119666,6.176905&destinations=49.470163,5.930146&mode=car&units=imperial
//AIzaSyBN4_F3cBbadQ4x1PqZf6_OCktum1dmkJg
//https://fr.distance24.org/route.json?stops=Metz|Villerupt



