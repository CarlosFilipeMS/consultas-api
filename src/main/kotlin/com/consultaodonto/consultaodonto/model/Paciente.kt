package com.consultaodonto.consultaodonto.model

import com.consultaodonto.consultaodonto.dto.PacienteDTO
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "paciente")

data class Paciente(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Usando geração automática do UUID
    val id: UUID? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, unique = true)
    val cpf: String,

    @Column(nullable = false)
    val dataNascimento: LocalDate,

    @Column(nullable = false)
    val telefone: String
) {
    fun toDTO() = PacienteDTO(
        id = this.id, // Convertendo UUID para String no DTO
        nome = this.nome,
        cpf = this.cpf,
        dataNascimento = this.dataNascimento.toString(),
        telefone = this.telefone
    )

    fun atualizar(nome: String, cpf: String, dataNascimento: LocalDate, telefone: String) =
        this.copy(
            nome = nome,
            cpf = cpf,
            dataNascimento = dataNascimento,
            telefone = telefone
        )
}
