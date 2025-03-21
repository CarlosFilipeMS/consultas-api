package com.consultaodonto.consultaodonto.dto

import java.util.UUID

data class DentistaDTO(
    val id: UUID?,
    val nome: String,
    val cro: String
)
