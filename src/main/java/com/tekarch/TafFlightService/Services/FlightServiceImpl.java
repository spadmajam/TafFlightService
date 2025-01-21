package com.tekarch.TafFlightService.Services;

import com.tekarch.TafFlightService.DTO.FlightsDTO;
import com.tekarch.TafFlightService.Exceptions.FlightNotFoundException;
import com.tekarch.TafFlightService.Services.Interface.FlightService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FlightServiceImpl implements FlightService {
    private static final Logger logger = LogManager.getLogger(FlightServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Value("${tafdatastorems.url}")
    private String dataStoreMsUrl;

    //Get flight by ID
    @Override
    public FlightsDTO getFlightById(Long flight_id) {
        String url = dataStoreMsUrl + "/flights/" + flight_id;
        ResponseEntity<FlightsDTO> response = restTemplate.getForEntity(url, FlightsDTO.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new FlightNotFoundException("Flight not found with ID: " + flight_id);
        }
    }

    //Get all flights
    public List<FlightsDTO> getAllFlights() {
        String url = dataStoreMsUrl + "/flights";
        ResponseEntity<FlightsDTO[]> response = restTemplate.getForEntity(url, FlightsDTO[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return List.of(response.getBody());
        } else {
            throw new RuntimeException("Failed to fetch flights");
        }
    }

    // Create a new flight
    public FlightsDTO addFlight(FlightsDTO flightDTO) {
        String url = dataStoreMsUrl + "/flights";
        ResponseEntity<FlightsDTO> response = restTemplate.postForEntity(url, flightDTO, FlightsDTO.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to create flight");
        }
    }

    // Update an existing flight by ID
    public FlightsDTO updateFlightById(Long flight_id, FlightsDTO flightDTO) {
        String url = dataStoreMsUrl + "/flights/" + flight_id;
        restTemplate.put(url, flightDTO);
        return getFlightById(flight_id);
    }

    // Delete a flight by ID
    public void deleteFlight(Long flight_id) {
        String url = dataStoreMsUrl + "/flights/" + flight_id;
        restTemplate.delete(url);
    }
}
