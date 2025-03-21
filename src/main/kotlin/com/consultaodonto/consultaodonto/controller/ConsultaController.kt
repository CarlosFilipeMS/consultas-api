package com.consultaodonto.consultaodonto.controller

import com.consultaodonto.consultaodonto.dto.ConsultaDTO
import com.consultaodonto.consultaodonto.service.ConsultaService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@CrossOrigin(origins = ["https://frontend-consultas-odonto.vercel.app/"]) // Permite chamadas do frontend
@RestController
@RequestMapping("/consultas")
class ConsultaController(
    private val consultaService: ConsultaService
) {
    // Endpoint para marcar consulta
    @PostMapping
    fun marcarConsulta(@RequestBody consultaDTO: ConsultaDTO): ConsultaDTO {
        return consultaService.marcarConsulta(consultaDTO)
    }

    // Endpoint para listar todas as consultas
    @GetMapping
    fun listarConsultas(): List<ConsultaDTO> {
        return consultaService.listarConsultas()
    }

    // Endpoint para buscar consulta por ID
    @GetMapping("/{id}")
    fun buscarConsultaPorId(@PathVariable id: String): ConsultaDTO {
        return consultaService.buscarConsultaPorId(UUID.fromString(id))
    }

    // Endpoint para buscar consultas por nome do paciente
    @GetMapping("/paciente")
    fun buscarConsultaPorPaciente(@RequestParam nome: String): List<ConsultaDTO> {
        return consultaService.buscarConsultaPorPaciente(nome)
    }

    // Endpoint para buscar consultas por nome do dentista
    @GetMapping("/dentista")
    fun buscarConsultaPorDentista(@RequestParam nome: String): List<ConsultaDTO> {
        return consultaService.buscarConsultaPorDentista(nome)
    }

    // Endpoint para atualizar consulta por ID
    @PutMapping("/{id}")
    fun atualizarConsultaPorId(@PathVariable id: String, @RequestBody consultaDTO: ConsultaDTO): ConsultaDTO {
        return consultaService.atualizarConsultaPorId(UUID.fromString(id), consultaDTO)
    }

    // Endpoint para deletar consulta
    @DeleteMapping("/{id}")
    fun deletarConsulta(@PathVariable id: String) {
        consultaService.deletarConsulta(UUID.fromString(id))
    }
}
