package com.example.restaurante.service;

import com.example.restaurante.dao.ReservaDao;
import com.example.restaurante.model.Reserva;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservaService {

    private final ReservaDao reservaDao;

    public List<Reserva> fetchAllReserva() {
        return reservaDao.findAllReserva();
    }


    public Reserva fetchReservaById(short id_reserva) {
        List<Reserva> result = reservaDao.findReservaById(id_reserva);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public List<Reserva> fetchReservaByCpf(String cpf) {
        List<Reserva> result = reservaDao.findReservaByCpf(cpf);

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    public Reserva createReserva(Reserva reserva) {
        return reservaDao.insertReserva(reserva);
    }

    public Reserva updateReserva(Reserva reserva) {
        int ok = reservaDao.updateReserva(reserva);
        if (ok == 1) {
            reserva.setCliente_cpf(fetchReservaById(reserva.getId_reserva()).getCliente_cpf());
            return reserva;
        }
        return null;
    }

    public int deleteAllReservaByCpf(String cliente_cpf) {
        return reservaDao.deleteAllReservaByCpf(cliente_cpf);
    }

    public int deleteReservaById(short id_reserva) {
        return reservaDao.deleteReservaById(id_reserva);
    }

}
