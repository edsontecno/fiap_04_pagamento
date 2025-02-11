Feature: Verify the payment service

  Scenario: client makes call to GET payment
    When the client calls /payment
    Then the client receives status code of 200
    And the client receives payment with status