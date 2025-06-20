package com.impacta.pecas.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.impacta.pecas.dto.ComponenteDto;
import com.impacta.pecas.dto.PecaDto;
import com.impacta.pecas.dto.PecaMinDto;
import com.impacta.pecas.model.Componente;
import com.impacta.pecas.model.Peca;
import com.impacta.pecas.repository.ComponenteRepository;
import com.impacta.pecas.repository.PecaRepository;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;
    private final ComponenteRepository componenteRepository;
    private final ModelMapper modelMapper;

    public PecaService(PecaRepository pecaRepository, ComponenteRepository componenteRepository,
            ModelMapper modelMapper) {
        this.pecaRepository = pecaRepository;
        this.componenteRepository = componenteRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public PecaMinDto savePeca(PecaMinDto dto) {
        if (pecaRepository.existsByCodigo(dto.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código já cadastrado");
        }

        Peca peca = modelMapper.map(dto, Peca.class);
        peca = pecaRepository.save(peca);
        return modelMapper.map(peca, PecaMinDto.class);
    }

    @Transactional
    public ComponenteDto saveComponente(String codigo, ComponenteDto dto) {

        Peca peca = pecaRepository.findByCodigo(codigo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Peça com código: " + codigo + ", não existe"));

        Componente componente = modelMapper.map(dto, Componente.class);
        componente.setPeca(peca);
        componente = componenteRepository.save(componente);

        return modelMapper.map(componente, ComponenteDto.class);
    }

    @Transactional(readOnly = true)
    public PecaDto findByCodigo(String codigo) {
        Peca peca = pecaRepository.findByCodigo(codigo).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Peça com código: " + codigo + ", não existe"));

        return modelMapper.map(peca, PecaDto.class);
    }

    @Transactional(readOnly = true)
    public List<PecaMinDto> findAll() {
        List<Peca> pecaList = pecaRepository.findAll();

        return pecaList.stream()
                .map(peca -> modelMapper.map(peca, PecaMinDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComponenteDto> findAllByCodigo(String codigo) {
        List<Componente> componenteList = componenteRepository.findAllByPeca_Codigo(codigo);

        return componenteList.stream()
                .map(componente -> modelMapper.map(componente, ComponenteDto.class))
                .toList();
    }

}
