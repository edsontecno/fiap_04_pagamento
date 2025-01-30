package br.com.fiap.microservice_payment.repository;

import br.com.fiap.microservice_payment.entity.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {

}
