package com.example.restaurante.dao;

import com.example.restaurante.model.Reserva;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class ReservaDao {
    private final JdbcTemplate jdbcTemplate;

    private Reserva mapRowToReserva(ResultSet rs, int rowNum) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setId_reserva(rs.getShort("id_reserva"));
        reserva.setCliente_cpf(rs.getString("cliente_cpf"));
        reserva.setQnt_pessoas(rs.getShort("qnt_pessoas"));
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


    public Reserva insertReserva(Reserva reserva) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Reserva(cliente_cpf, qnt_pessoas, data_hora_chegada) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reserva.getCliente_cpf());
            ps.setShort(2, reserva.getQnt_pessoas());
            ps.setObject(3, reserva.getData_hora_chegada());
            return ps;
            }, keyHolder);

        if(keyHolder.getKey() == null) {
            return null;
        }

        reserva.setId_reserva(keyHolder.getKey().shortValue());

        return reserva;
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
