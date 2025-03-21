package com.consultaodonto.consultaodonto.repository

import com.consultaodonto.consultaodonto.model.Consulta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ConsultaRepository : JpaRepository<Consulta, UUID> {
    @Query("SELECT c FROM Consulta c JOIN c.paciente p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    fun findByPacienteNome(@Param("nome") nome: String): List<Consulta>

    @Query("SELECT c FROM Consulta c JOIN c.dentista d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    fun findByDentistaNome(@Param("nome") nome: String): List<Consulta>
}