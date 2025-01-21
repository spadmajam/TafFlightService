package com.tekarch.TafFlightService.Services.Interface;

import com.tekarch.TafFlightService.DTO.FlightsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {
    FlightsDTO addFlight(FlightsDTO flight);
    FlightsDTO getFlightById(Long flight_id);
    FlightsDTO updateFlightById(Long flight_id, FlightsDTO flightDTO);
    List<FlightsDTO> getAllFlights();
    ResponseEntity<String> deleteFlight(Long flight_id);
}
