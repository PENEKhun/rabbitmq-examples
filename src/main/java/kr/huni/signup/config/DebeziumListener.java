package kr.huni.signup.config;

import io.debezium.config.Configuration;
import io.debezium.data.Envelope;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import kr.huni.signup.domain.User;
import kr.huni.signup.repository.UserRepository;
import kr.huni.signup.service.CouponService;
import kr.huni.signup.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;

@Slf4j
@Component
public class DebeziumListener {

    private final UserRepository userRepository;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final EmailService emailService;
    private final CouponService couponService;

    public DebeziumListener(UserRepository userRepository, Configuration config, EmailService emailService, CouponService couponService) {
        this.userRepository = userRepository;
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(config.asProperties())
                .notifying(this::handleChangeEvent)
                .build();
        this.emailService = emailService;
        this.couponService = couponService;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();

        Struct sourceRecordChangeValue = (Struct) sourceRecord.value();

        if (sourceRecordChangeValue != null) {
            Envelope.Operation operation = Envelope.Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

            if (operation == Envelope.Operation.CREATE) {
                val key = sourceRecord.key();
                Struct keyStruct = (Struct) key;
                Long id = keyStruct.getInt64("id");

                // Fetch the user from the database
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found with pk: " + id));

                // Send welcome email
                log.info("Sending welcome email to user: {}", user.getUsername());
                emailService.sendWelcomeEmail(user);

                // Issue welcome coupon
                log.info("Issuing welcome coupon to user: {}", user.getUsername());
                couponService.issueWelcomeCoupon(user);
            }
        }
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}
