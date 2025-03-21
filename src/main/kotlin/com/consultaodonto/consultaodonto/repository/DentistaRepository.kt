package com.consultaodonto.consultaodonto.repository

import com.consultaodonto.consultaodonto.model.Dentista
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DentistaRepository : JpaRepository<Dentista, UUID>{
    fun findByCro(cro: String): Dentista?
    fun findByNomeContainingIgnoreCase(nome: String): List<Dentista>
}