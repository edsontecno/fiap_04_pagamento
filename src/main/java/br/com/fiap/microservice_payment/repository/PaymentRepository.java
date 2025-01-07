package br.com.fiap.microservice_payment.repository;

import br.com.fiap.microservice_payment.entity.PaymentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentEntity, Long> {

}
