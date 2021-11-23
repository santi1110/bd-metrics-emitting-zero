package com.amazon.ata.metrics.prework.emittingzero;


import com.amazonaws.services.cloudwatch.model.StandardUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderProcessorTest {

    private static final String ORDER_ID = "orderId-1234";
    private static final String CUSTOMER_ID =  "customerId-58483";
    private static final String PAYMENT_ID = "paymentId-13424";
    private static final double TOTAL_PRICE = 13.99;
    private static final String METRIC_NAME = "ORDER_FAILURES";

    @Mock
    private MetricsPublisher metricsPublisher;

    @Mock
    private PaymentProcessor paymentProcessor;

    @InjectMocks
    private OrderProcessor orderProcessor;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }


    @Test
    public void processOrder_successfulOrder_logsZeroValueErrorMetric() throws Exception {
        // GIVEN
        Order order = new Order(ORDER_ID, CUSTOMER_ID, PAYMENT_ID,TOTAL_PRICE);
        when(paymentProcessor.processPayment(order)).thenReturn(true);

        // WHEN
        orderProcessor.processOrder(order);

        // THEN
        verify(metricsPublisher, times(1)).addMetric(METRIC_NAME, 0, StandardUnit.Count);
        verifyNoMoreInteractions(metricsPublisher);
    }


    @Test
    public void processOrder_failedOrder_logsOneValueErrorMetric() throws Exception {
        // GIVEN
        Order order = new Order(ORDER_ID, CUSTOMER_ID, PAYMENT_ID,TOTAL_PRICE);
        when(paymentProcessor.processPayment(order)).thenThrow(new RuntimeException());

        // WHEN
        orderProcessor.processOrder(order);

        // THEN
        verify(metricsPublisher, times(1)).addMetric(METRIC_NAME, 1, StandardUnit.Count);
        verifyNoMoreInteractions(metricsPublisher);
    }
}
