package com.ylizma.kafkaoperationsanalytic.services;

import com.ylizma.kafkaoperationsanalytic.entities.Operation;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class OperationService {

//    @Bean
//    public Supplier<Operation> pageEventSupplier() {
//        return () -> new PageEvent("name", (Math.random() > 0.5) ? "u1" : "u2", new Date(), new Random().nextInt(20000));
//    }

    @Bean
    public Function<KStream<String, Operation>, KStream<String, Long>> operationKStream() {
        return input ->
                input
                .map((s, operation) -> new KeyValue<>(operation.getAccount()+"",0L))
                .groupBy((s, aLong) -> s, Grouped.with(Serdes.String(), Serdes.Long()))
                .windowedBy(TimeWindows.of(5000))
                .count()
                .toStream()
                .map((stringWindowed, aLong) -> new KeyValue<>(stringWindowed.key(), aLong));
//                .map((stringWindowed, aLong) -> new KeyValue<>(stringWindowed.key(), aLong));
//                        .map(() -> new KeyValue<>(pageEvent.getName(), 0L))
//                        .groupBy((s, aLong) -> s, Grouped.with(Serdes.String(), Serdes.Long()))
//                        .windowedBy(TimeWindows.of(5000))
//                        .count()
//                        .toStream()
//                        .map((stringWindowed, aLong) -> new KeyValue<>(stringWindowed.key(), aLong));
    }
}
