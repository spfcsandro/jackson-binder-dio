package br.com.digitalinnovation.jacksonbinderdio.resource;

import br.com.digitalinnovation.jacksonbinderdio.controller.SoldadoController;
import br.com.digitalinnovation.jacksonbinderdio.controller.response.SoldadoListResponse;
import br.com.digitalinnovation.jacksonbinderdio.controller.response.SoldadoResponse;
import br.com.digitalinnovation.jacksonbinderdio.model.Soldado;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SoldadoResource {

    private ObjectMapper objectMapper;

    public SoldadoResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SoldadoListResponse criarLink(Soldado soldado){
        SoldadoListResponse soldadoListResponse = objectMapper.convertValue(soldado, SoldadoListResponse.class);
        Link link = linkTo(SoldadoController.class).slash(soldado.getId()).withSelfRel();
        soldadoListResponse.add(link);
        return soldadoListResponse;
    }

    public SoldadoResponse criarLinkDetalhe(Soldado soldado){
        SoldadoResponse soldadoListResponse = objectMapper.convertValue(soldado, SoldadoResponse.class);
        if(soldado.getStatus().equals("morto")){
            Link link = linkTo(methodOn(SoldadoController.class).deletarSoldado(soldado.getId()))
                    .withRel("remover")
                    .withTitle("Deletar soldado")
                    .withType("delete");
            soldadoListResponse.add(link);
        }else if(soldado.getStatus().equals("vivo")){
            Link link = linkTo(methodOn(SoldadoController.class).frenteCastelo(soldado.getId()))
                    .withRel("batalhar")
                    .withTitle("Ir para frente do castelo")
                    .withType("put");
            soldadoListResponse.add(link);
        }
        return soldadoListResponse;
    }
}
