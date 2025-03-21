package com.consultaodonto.consultaodonto.controller

import com.consultaodonto.consultaodonto.dto.PacienteDTO
import com.consultaodonto.consultaodonto.service.PacienteService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@CrossOrigin(origins = ["https://frontend-consultas-odonto.vercel.app/"]) // Permite chamadas do frontend
@RestController
@RequestMapping("/pacientes")
class PacienteController(
    @Autowired private val pacienteService: PacienteService
) {

    @PostMapping
    fun cadastrarPaciente(@RequestBody pacienteDTO: PacienteDTO): PacienteDTO {
        return pacienteService.cadastrarPaciente(pacienteDTO)
    }

    @PutMapping("/{id}")
    fun atualizarPaciente(@PathVariable id: String, @RequestBody pacienteDTO: PacienteDTO): PacienteDTO {
        return pacienteService.atualizarPaciente(UUID.fromString(id), pacienteDTO)
    }

    @GetMapping("/{id}")
    fun buscarPacientePorId(@PathVariable id: String): PacienteDTO {
        return pacienteService.buscarPacientePorId(UUID.fromString(id))
    }

    @GetMapping("/nome/{nome}") // Usa PathVariable para evitar conflitos
    fun buscarPacientePorNome(@PathVariable nome: String): List<PacienteDTO> {
        return pacienteService.buscarPacientePorNome(nome)
    }

    @GetMapping("/cpf")
    fun buscarPorCPF(@RequestParam cpf: String): ResponseEntity<PacienteDTO> {
        return try {
            val pacienteDTO = pacienteService.buscarPorCPF(cpf)
            ResponseEntity.ok(pacienteDTO)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @GetMapping
    fun listarPacientes(): List<PacienteDTO> {
        return pacienteService.listarPacientes()
    }

    @DeleteMapping("/{id}")
    fun deletarPaciente(@PathVariable id: String) {
        pacienteService.deletarPaciente(UUID.fromString(id))
    }
}
