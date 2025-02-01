package br.com.fiap.microservice_payment.repository;

import br.com.fiap.microservice_payment.entity.PaymentEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
public interface PaymentRepository extends CrudRepository<PaymentEntity, String> {
    @Override
    Optional<PaymentEntity> findById(String id);
}
