package br.com.digitalinnovation.jacksonbinderdio.service;

import br.com.digitalinnovation.jacksonbinderdio.controller.response.SoldadoListResponse;
import br.com.digitalinnovation.jacksonbinderdio.controller.response.SoldadoResponse;
import br.com.digitalinnovation.jacksonbinderdio.dto.SoldadoDTO;
import br.com.digitalinnovation.jacksonbinderdio.controller.request.SoldadoEditRequest;
import br.com.digitalinnovation.jacksonbinderdio.model.Soldado;
import br.com.digitalinnovation.jacksonbinderdio.repository.SoldadoRepository;
import br.com.digitalinnovation.jacksonbinderdio.resource.SoldadoResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoldadoService {

    private SoldadoRepository soldadoRepository;
    private ObjectMapper objectMapper;
    private SoldadoResource soldadoResource;

    public SoldadoService(SoldadoRepository soldadoRepository, ObjectMapper objectMapper, SoldadoResource soldadoResource) {
        this.soldadoRepository = soldadoRepository;
        this.objectMapper = objectMapper;
        this.soldadoResource = soldadoResource;
    }

    public SoldadoResponse buscarSoldado(Long id){
        Soldado soldado = soldadoRepository.findById(id).orElseThrow();
        SoldadoResponse soldadoResponse = soldadoResource.criarLinkDetalhe(soldado);
        return soldadoResponse;
    }

    public void criarSoldado(SoldadoDTO soldadoDTO){
        Soldado soldado = objectMapper.convertValue(soldadoDTO, Soldado.class);
        soldadoRepository.save(soldado);
    }

    public void editarSoldado(Long id, SoldadoEditRequest soldadoEditRequest) {
        Soldado soldado = objectMapper.convertValue(soldadoEditRequest, Soldado.class);
        soldado.setId(id);
        soldadoRepository.save(soldado);
    }

    public void deletarSoldado(Long id) {
        Soldado soldado = soldadoRepository.findById(id).orElseThrow();
        soldadoRepository.delete(soldado);
    }

    public CollectionModel<SoldadoListResponse> buscarSoldados(){
        List<Soldado> all = soldadoRepository.findAll();
        List<SoldadoListResponse> soldadoListResponses = all.stream()
                .map(it -> soldadoResource.criarLink(it))
                .collect(Collectors.toList());
        return new CollectionModel<>(soldadoListResponses);
    }
}
