package com.example.restaurante.dao;

import com.example.restaurante.model.Reserva;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class ReservaDao {
    private final JdbcTemplate jdbcTemplate;

    private Reserva mapRowToReserva(ResultSet rs, int rowNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setId_reserva(rs.getShort("id_reserva"));
        reserva.setCliente_cpf(rs.getString("cpf"));
        reserva.setQnt_pessoas(rs.getShort("nome"));
        reserva.setData_hora_chegada(rs.getObject("data_hora_chegada", LocalDateTime.class));
        return reserva;
    }

    public List<Reserva> findAllReserva() {
        return this.jdbcTemplate.query("SELECT * FROM Reserva", this::mapRowToReserva);
    }

    public List<Reserva> findReservaById(short id_reserva) {
        return this.jdbcTemplate.query("SELECT * FROM Reserva WHERE id_reserva=?", this::mapRowToReserva, id_reserva);
    }

    public List<Reserva> findReservaByCpf(String cliente_cpf) {
        return this.jdbcTemplate.query("SELECT * FROM Reserva WHERE cliente_cpf=?", this::mapRowToReserva, cliente_cpf);
    }


    public int insertReserva(Reserva reserva) {
        return this.jdbcTemplate.update("INSERT INTO Reserva(cliente_cpf, qnt_pessoas, data_hora_chegada) VALUES(?,?,?)", reserva.getCliente_cpf(), reserva.getQnt_pessoas(), reserva.getData_hora_chegada());
    }

    public int updateReserva(Reserva reserva) {
        return this.jdbcTemplate.update("UPDATE Reserva SET qnt_pessoas=?, data_hora_chegada=? WHERE id_reserva=?", reserva.getQnt_pessoas(), reserva.getData_hora_chegada(), reserva.getId_reserva());
    }

    public int deleteAllReservaByCpf(String cliente_cpf) {
        return this.jdbcTemplate.update("DELETE FROM Reserva WHERE cliente_cpf=?", cliente_cpf);
    }

    public int deleteReservaById(short id_reserva) {
        return this.jdbcTemplate.update("DELETE FROM Reserva WHERE id_reserva=?", id_reserva);
    }

}
