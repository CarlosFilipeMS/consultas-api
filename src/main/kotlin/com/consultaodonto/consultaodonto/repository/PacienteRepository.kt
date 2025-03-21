package com.consultaodonto.consultaodonto.repository

import com.consultaodonto.consultaodonto.model.Paciente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PacienteRepository : JpaRepository<Paciente, UUID> {
    fun findByCpf(cpf: String): Paciente?
    fun findByNomeContainingIgnoreCase(nome: String): List<Paciente>
}
