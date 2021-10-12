package br.com.digitalinnovation.jacksonbinderdio.enums;

import java.util.stream.Stream;

public enum Raca {

    HUMANO("humano"),
    ELFO("elfo"),
    ORC("orc"),
    ANAO("anao");

    private String descricao;

    Raca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Raca of(String value){
        return Stream.of(Raca.values())
                .filter(it -> it.getDescricao().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
