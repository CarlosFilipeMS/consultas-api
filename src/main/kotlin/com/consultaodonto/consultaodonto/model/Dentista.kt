package com.consultaodonto.consultaodonto.model

import com.consultaodonto.consultaodonto.dto.DentistaDTO
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "dentista")
data class Dentista(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, unique = true)
    val cro: String
) {

    fun toDTO() = DentistaDTO(
        id = this.id,
        nome = this.nome,
        cro = this.cro
    )

    fun atualizar(nome: String, cro: String) =
        this.copy(
            nome = nome,
            cro = cro
        )
}