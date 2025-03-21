package com.consultaodonto.consultaodonto.service

import com.consultaodonto.consultaodonto.dto.DentistaDTO
import com.consultaodonto.consultaodonto.model.Dentista
import com.consultaodonto.consultaodonto.repository.DentistaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class DentistaService(
    @Autowired private val dentistaRepository: DentistaRepository
) {
    @Transactional
    fun cadastrarDentista(dentistaDTO: DentistaDTO): DentistaDTO {
        val dentista = Dentista(
            id = null,
            nome = dentistaDTO.nome,
            cro = dentistaDTO.cro,
        )

        try {
            val dentistaSalvo = dentistaRepository.save(dentista)
            return dentistaSalvo.toDTO()
        } catch (e: Exception) {
            throw IllegalArgumentException("Erro ao salvar dentista ${e.message}")
        }
    }
    
    fun listarDentistas(): List<DentistaDTO> {
        val dentistas = dentistaRepository.findAll()
        return dentistas.map {it.toDTO()}
    }

    fun buscarDentistaPorId(id: UUID): DentistaDTO{
        val dentista = dentistaRepository.findById(id).orElseThrow{
            ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum dentista encontrado com o id $id")
        }
        return dentista.toDTO()
    }
    fun buscarDentistaPorNome(nome: String): List<DentistaDTO> {
        val dentistas = dentistaRepository.findByNomeContainingIgnoreCase(nome)
        if(dentistas.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum dentista encontrado")
        }
        return dentistas.map {it.toDTO()}
    }

    fun buscarDentistaPorCro(cro: String): DentistaDTO {
        val dentista = dentistaRepository.findByCro(cro)
        if (dentista == null) {
            throw IllegalArgumentException("Nenhum dentista encontrado com o cro $cro")
        }
        return dentista.toDTO()
    }

    @Transactional
    fun atualizarDentista(id: UUID, dentistaDTO: DentistaDTO): DentistaDTO {
        val dentistaExistente = dentistaRepository.findById(id).orElseThrow{
            IllegalArgumentException("Nenhum dentista encontrado")
        }
        val dentistaAtualizado = dentistaExistente.atualizar(
            nome = dentistaDTO.nome,
            cro = dentistaDTO.cro
        )
        try {
            val dentistaSalvo = dentistaRepository.save(dentistaAtualizado)
            return dentistaSalvo.toDTO()
        } catch (e: Exception) {
            throw IllegalArgumentException("Erro ao atualizar paciente ${e.message}")
        }
    }

    fun deletarDentista(id: UUID) {
        dentistaRepository.deleteById(id)
    }

}