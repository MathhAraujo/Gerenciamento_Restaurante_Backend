package com.example.restaurante.controller;

import com.example.restaurante.model.Reserva;
import com.example.restaurante.service.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping("/find/all")
    public ResponseEntity<List<Reserva>> getAllReserva(){
        List<Reserva> reservas = reservaService.fetchAllReserva();
        if(reservas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas,HttpStatus.OK);
    }

    @GetMapping("/find/{id_reserva}")
    public ResponseEntity<Reserva> fetchReservaById(@PathVariable("id_reserva") short id_reserva){
        Reserva reserva = reservaService.fetchReservaById(id_reserva);
        if(reserva == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @GetMapping("/find/{cliente_cpf}")
    public ResponseEntity<List<Reserva>> fetchReservaByCpf(@PathVariable("cliente_cpf") String cliente_cpf){
        List<Reserva> reserva = reservaService.fetchReservaByCpf(cliente_cpf);
        if(reserva == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reserva, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Reserva> addReserva(@RequestBody Reserva reserva){
        Reserva newReserva = reservaService.createReserva(reserva);
        if(newReserva == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newReserva, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Reserva> updateReserva(@RequestBody Reserva reserva){
        Reserva reservaUpdate =  reservaService.updateReserva(reserva);

        if(reservaUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservaUpdate, HttpStatus.OK);
    }

    @DeleteMapping("delete/{cliente_cpf}")
    public ResponseEntity<?> deleteAllReservaByCpf(@PathVariable("cliente_cpf") String cliente_cpf){
        int ok = reservaService.deleteAllReservaByCpf(cliente_cpf);

        if(ok == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete/{id_reserva}")
    public ResponseEntity<?> deleteReservaById(@PathVariable("id_reserva") short id_reserva){
        int ok = reservaService.deleteReservaById(id_reserva);

        if(ok == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
