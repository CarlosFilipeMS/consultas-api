package com.consultaodonto.consultaodonto.model

import com.consultaodonto.consultaodonto.dto.ConsultaDTO
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "consulta")
data class Consulta(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    val paciente: Paciente,

    @ManyToOne
    @JoinColumn(name = "dentista_id", nullable = false)
    val dentista: Dentista,

    @Column(nullable = false)
    val dataHora: LocalDateTime
) {
    fun toDTO() = ConsultaDTO(
        id = this.id,
        pacienteCpf = this.paciente.cpf,
        pacienteNome = this.paciente.nome, // Adicionando o nome do paciente
        dentistaId = this.dentista.id!!,
        dentistaNome = this.dentista.nome, // Adicionando o nome do dentista
        dataHora = this.dataHora
    )



    fun atualizar(dataHora: LocalDateTime) =
        this.copy(
            dataHora = dataHora
        )
}
