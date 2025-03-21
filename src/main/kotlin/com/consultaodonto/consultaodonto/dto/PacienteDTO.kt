package com.consultaodonto.consultaodonto.dto

import java.util.UUID


data class PacienteDTO(
    val id: UUID?, // Agora o ID é uma String representando o UUID
    val nome: String,
    val cpf: String,
    val dataNascimento: String,
    val telefone: String
)
