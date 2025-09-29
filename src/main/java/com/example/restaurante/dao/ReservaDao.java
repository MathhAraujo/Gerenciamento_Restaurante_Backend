package com.example.restaurante.dao;

import com.example.restaurante.dto.Occupation_DayDTO;
import com.example.restaurante.dto.ReservaFuturaDTO;
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
        return new Reserva(
                rs.getShort("id_reserva"),
                rs.getString("cliente_cpf"),
                rs.getShort("qnt_pessoas"),
                rs.getObject("data_hora_chegada", LocalDateTime.class)
        );
    }

    private ReservaFuturaDTO mapRowToReservaFutura(ResultSet rs, int rowNum) throws SQLException {
        return new ReservaFuturaDTO(
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getShort("id_reserva"),
                rs.getShort("qnt_pessoas"),
                rs.getObject("data_hora_chegada", LocalDateTime.class)
        );
    }

    private Occupation_DayDTO mapRowToOccupation_DayDTO(ResultSet rs, int rowNum) throws SQLException {
        return new Occupation_DayDTO(
                rs.getString("day"),
                rs.getInt("num_reserva"),
                rs.getInt("total_ppl")
        );
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

    public List<ReservaFuturaDTO> findAllClienteWithReservaInPeriod(LocalDateTime init, LocalDateTime end) {
        return this.jdbcTemplate.query("SELECT nome, telefone, id_reserva, qnt_pessoas, data_hora_chegada FROM Cliente INNER JOIN Reserva ON cpf = cliente_cpf WHERE data_hora_chegada BETWEEN ? AND ? ORDER BY data_hora_chegada",
                this::mapRowToReservaFutura, init, end);
    }

    public List<Reserva> findAllFutureReserva(String clienteCpf) {
        return this.jdbcTemplate.query("SELECT * FROM Reserva WHERE cliente_cpf=? AND data_hora_chegada > NOW() ORDER BY data_hora_chegada", this::mapRowToReserva, clienteCpf);
    }

    public List<ReservaFuturaDTO> findAllFutureBiggerThan(short qntPessoas) {
        return this.jdbcTemplate.query("SELECT nome, telefone, id_reserva, qnt_pessoas, data_hora_chegada FROM Cliente INNER JOIN Reserva ON cpf = cliente_cpf WHERE data_hora_chegada > NOW() AND qnt_pessoas >= ? ORDER BY data_hora_chegada",
                this::mapRowToReservaFutura, qntPessoas);
    }

    public List<Occupation_DayDTO> findOccupationPerDay() {
        return this.jdbcTemplate.query("SELECT DAYNAME(data_hora_chegada) AS day, COUNT(id_reserva) AS num_reserva, SUM(qnt_pessoas) AS total_ppl FROM Reserva GROUP BY day ORDER BY total_ppl",  this::mapRowToOccupation_DayDTO);
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

        if (keyHolder.getKey() == null) {
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
