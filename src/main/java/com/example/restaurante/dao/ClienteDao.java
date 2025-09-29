package com.example.restaurante.dao;

import com.example.restaurante.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteDao {

    private final JdbcTemplate jdbcTemplate;

    private Cliente mapRowToCliente(ResultSet rs, int rowNum) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCpf(rs.getString("cpf"));
        cliente.setNome(rs.getString("nome"));
        cliente.setTelefone(rs.getString("telefone"));
        return cliente;
    }

    public List<Cliente> findAllCliente() {
        return this.jdbcTemplate.query("SELECT * FROM Cliente", this::mapRowToCliente);
    }

    public List<Cliente> findClienteByCpf(String cpf) {
        return this.jdbcTemplate.query("SELECT * FROM Cliente WHERE cpf=?", this::mapRowToCliente, cpf);
    }

    public List<Cliente> findClienteByNome(String nome) {
        return this.jdbcTemplate.query("SELECT * FROM Cliente WHERE nome=?", this::mapRowToCliente, nome);
    }


    public int insertCliente(Cliente cliente) {
        return this.jdbcTemplate.update("INSERT INTO Cliente(cpf, nome, telefone) VALUES(?,?,?)", cliente.getCpf(), cliente.getNome(), cliente.getTelefone());
    }

    public int updateCliente(Cliente cliente) {
        return this.jdbcTemplate.update("UPDATE Cliente SET Nome=?, Telefone=? WHERE cpf=?", cliente.getNome(), cliente.getTelefone(), cliente.getCpf());
    }

    public int deleteCliente(String cpf) {
        return this.jdbcTemplate.update("DELETE FROM Cliente WHERE cpf=?", cpf);
    }

}

