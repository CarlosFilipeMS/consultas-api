package com.consultaodonto.consultaodonto.service

import com.consultaodonto.consultaodonto.dto.ConsultaDTO
import com.consultaodonto.consultaodonto.model.Consulta
import com.consultaodonto.consultaodonto.repository.ConsultaRepository
import com.consultaodonto.consultaodonto.repository.DentistaRepository
import com.consultaodonto.consultaodonto.repository.PacienteRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.UUID

@Service
class ConsultaService(
    private val consultaRepository: ConsultaRepository,
    private val pacienteRepository: PacienteRepository,
    private val dentistaRepository: DentistaRepository
) {
    @Transactional
    fun marcarConsulta(consultaDTO: ConsultaDTO): ConsultaDTO {
        val paciente = pacienteRepository.findByCpf(consultaDTO.pacienteCpf)
            ?: throw IllegalArgumentException("Paciente não encontrado com CPF ${consultaDTO.pacienteCpf}")

        val dentista = dentistaRepository.findById(consultaDTO.dentistaId)
            .orElseThrow { IllegalArgumentException("Dentista não encontrado") }

        val novaConsulta = Consulta(
            paciente = paciente,
            dentista = dentista,
            dataHora = consultaDTO.dataHora
        )

        val consultaSalva = consultaRepository.save(novaConsulta)
        return consultaSalva.toDTO()
    }


    fun listarConsultas(): List<ConsultaDTO> {
        val consultas = consultaRepository.findAll()
        return consultas.map { it.toDTO() }
    }

    fun buscarConsultaPorId(id: UUID): ConsultaDTO {
        val consulta = consultaRepository.findById(id).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma consulta encontrada com este id: $id")
        }
        return consulta.toDTO()
    }

    fun buscarConsultaPorPaciente(nome: String): List<ConsultaDTO> {
        val consultas = consultaRepository.findByPacienteNome(nome)
        return consultas.map { it.toDTO() }
    }

    fun buscarConsultaPorDentista(nome: String): List<ConsultaDTO> {
        val consultas = consultaRepository.findByDentistaNome(nome)
        return consultas.map { it.toDTO() }
    }

    @Transactional
    fun atualizarConsultaPorId(id: UUID, consultaDTO: ConsultaDTO): ConsultaDTO {
        val consultaExistente = consultaRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma consulta encontrada com este id $id")
        }

        val paciente = pacienteRepository.findByCpf(consultaDTO.pacienteCpf)
            ?: throw IllegalArgumentException("Paciente não encontrado com CPF ${consultaDTO.pacienteCpf}")

        val dentista = dentistaRepository.findById(consultaDTO.dentistaId)
            .orElseThrow { IllegalArgumentException("Dentista não encontrado!") }

        val consultaAtualizada = consultaExistente.copy(
            paciente = paciente,
            dentista = dentista,
            dataHora = consultaDTO.dataHora
        )

        return try {
            consultaRepository.save(consultaAtualizada).toDTO()
        } catch (e: Exception) {
            throw IllegalArgumentException("Erro ao salvar consulta $id: ${e.message}")
        }
    }


    fun deletarConsulta(id: UUID) {
        consultaRepository.deleteById(id)
    }
}
