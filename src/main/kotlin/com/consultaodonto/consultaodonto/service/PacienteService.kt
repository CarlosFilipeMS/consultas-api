package com.consultaodonto.consultaodonto.service

import com.consultaodonto.consultaodonto.dto.PacienteDTO
import com.consultaodonto.consultaodonto.model.Paciente
import com.consultaodonto.consultaodonto.repository.PacienteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate
import java.util.*

@Service
class PacienteService(
    @Autowired private val pacienteRepository: PacienteRepository
) {

    @Transactional
    fun cadastrarPaciente(pacienteDTO: PacienteDTO): PacienteDTO {
        val paciente = Paciente(
            id = null, // O UUID será gerado automaticamente pelo banco de dados
            nome = pacienteDTO.nome,
            cpf = pacienteDTO.cpf,
            dataNascimento = LocalDate.parse(pacienteDTO.dataNascimento),
            telefone = pacienteDTO.telefone
        )

        try {
            val pacienteSalvo = pacienteRepository.save(paciente)
            return pacienteSalvo.toDTO()
        } catch (e: Exception) {
            throw IllegalStateException("Erro ao salvar o paciente: ${e.message}")
        }
    }

    @Transactional
    fun atualizarPaciente(id: UUID, pacienteDTO: PacienteDTO): PacienteDTO {
        val pacienteExistente = pacienteRepository.findById(id).orElseThrow {
            IllegalArgumentException("Paciente não encontrado")
        }

        val pacienteAtualizado = pacienteExistente.atualizar(
            nome = pacienteDTO.nome,
            cpf = pacienteDTO.cpf,
            dataNascimento = LocalDate.parse(pacienteDTO.dataNascimento),
            telefone = pacienteDTO.telefone
        )

        try {
            val pacienteSalvo = pacienteRepository.save(pacienteAtualizado)
            return pacienteSalvo.toDTO()
        } catch (e: Exception) {
            throw IllegalStateException("Erro ao atualizar o paciente: ${e.message}")
        }
    }

    fun buscarPacientePorId(id: UUID): PacienteDTO {
        val paciente = pacienteRepository.findById(id).orElseThrow {
            IllegalArgumentException("Paciente não encontrado")
        }
        return paciente.toDTO()
    }

    fun listarPacientes(): List<PacienteDTO> {
        val pacientes = pacienteRepository.findAll()
        return pacientes.map { it.toDTO() }
    }

    fun deletarPaciente(id: UUID) {
        pacienteRepository.deleteById(id)
    }

    fun buscarPacientePorNome(nome: String): List<PacienteDTO> {
        val pacientes = pacienteRepository.findByNomeContainingIgnoreCase(nome)  // Busca parcial insensível a maiúsculas/minúsculas
        if (pacientes.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum paciente encontrado com o nome: $nome")
        }
        return pacientes.map { it.toDTO() }
    }

    fun buscarPorCPF(cpf: String): PacienteDTO {
        val cpfSanitizado = cpf.replace("[^0-9]".toRegex(), "")
        println("Buscando paciente com CPF: $cpfSanitizado")  // Adicione o log para ver o CPF que está sendo buscado
        val paciente = pacienteRepository.findByCpf(cpfSanitizado)
        if (paciente == null) {
            throw IllegalArgumentException("Paciente não encontrado com o CPF: $cpfSanitizado")
        }
        return paciente.toDTO()
    }


}