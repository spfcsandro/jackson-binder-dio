package br.com.digitalinnovation.jacksonbinderdio.config;

import br.com.digitalinnovation.jacksonbinderdio.enums.Raca;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();

        //PROPRIEDADES NÃO MAPEADAS não quebram
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // FALHA SE ALGUMA PROPRIEDADE ESTIVER VAZIA
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //SERVE Para compatibilidade de arrays, quando tem um array com um item, caso não tenha essa config ele se perde
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //Serialização data
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(racaModuleMapper());
        return objectMapper;
    }

    public SimpleModule racaModuleMapper(){
        SimpleModule dataModule = new SimpleModule("JSONRacaModule", PackageVersion.VERSION);
        dataModule.addSerializer(Raca.class, new RacaSerializer());
        dataModule.addDeserializer(Raca.class, new RacaDeserializer());
        return dataModule;
    }

    class RacaSerializer extends StdSerializer<Raca>{
        public RacaSerializer(){
            super(Raca.class);
        }

        @Override
        public void serialize(Raca raca, JsonGenerator json, SerializerProvider provider) throws IOException {
            json.writeString(raca.name());
        }
    }

    class RacaDeserializer extends StdDeserializer<Raca> {
        public RacaDeserializer(){
            super(Raca.class);
        }

        @Override
        public Raca deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return Raca.of(p.getText());
        }
    }
}
