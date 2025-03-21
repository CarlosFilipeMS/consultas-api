package com.consultaodonto.consultaodonto.controller

import com.consultaodonto.consultaodonto.dto.DentistaDTO
import com.consultaodonto.consultaodonto.service.DentistaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.UUID

@CrossOrigin(origins = ["https://frontend-consultas-odonto.vercel.app/"]) // Permite chamadas do frontend
@RestController
@RequestMapping("/dentistas")
class DentistaController(
    @Autowired private val dentistaService: DentistaService
) {
    @PostMapping
    fun cadastrarDentista(@RequestBody dentistaDTO: DentistaDTO): DentistaDTO{
        return dentistaService.cadastrarDentista(dentistaDTO)
    }

    @GetMapping
    fun listarDentistas(): List<DentistaDTO>{
        return dentistaService.listarDentistas()
    }

    @GetMapping("/{id}")
    fun buscarDentistaPorId(@PathVariable id: String): DentistaDTO{
        return dentistaService.buscarDentistaPorId(UUID.fromString(id))
    }

    @GetMapping("/nome/{nome}")
    fun buscarDentistaPorNome(@PathVariable nome: String): List<DentistaDTO>{
        return  dentistaService.buscarDentistaPorNome(nome)
    }

    @GetMapping("/cro")
    fun buscarDentistaPorCro(@RequestParam cro: String): DentistaDTO{
        return dentistaService.buscarDentistaPorCro(cro)
    }

    @PutMapping("/{id}")
    fun atualizarDentista(@PathVariable id: String, @RequestBody dentistaDTO: DentistaDTO): DentistaDTO{
        return dentistaService.atualizarDentista(UUID.fromString(id), dentistaDTO)
    }

    @DeleteMapping("/{id}")
    fun deletarDentista(@PathVariable id: String){
        return dentistaService.deletarDentista(UUID.fromString(id))
    }
}