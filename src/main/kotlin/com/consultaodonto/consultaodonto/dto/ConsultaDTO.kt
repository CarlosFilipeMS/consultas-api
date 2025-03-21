package com.consultaodonto.consultaodonto.dto

import java.time.LocalDateTime
import java.util.UUID

data class ConsultaDTO(
    val id: UUID?,
    val pacienteCpf: String,
    val pacienteNome: String,
    val dentistaId: UUID,
    val dentistaNome: String,
    val dataHora: LocalDateTime
)

