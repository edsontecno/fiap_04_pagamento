package features;

import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.service.PaymentService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfiguration.class)
public class PaymentStepDefinition {

    @Autowired
    private MockMvc mvc;

    ResultActions action;

    @MockitoBean
    private PaymentService service;

    @When("the client calls /payment")
    public void the_client_calls_payment() throws Exception {
        action = this.mvc.perform(get("/payment/{paymentId}", "1")
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the client receives status code of {int}")
    public void the_client_receives_status_code_of(Integer int1) throws Exception {
        action
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Then("the client receives payment with status")
    public void the_client_receives_payment_with_status() throws Exception {
        action
                .andDo(print());
    }

}
