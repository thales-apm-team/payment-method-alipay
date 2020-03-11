package com.payline.payment.alipay.service.impl;

import com.payline.payment.alipay.MockUtils;
import com.payline.payment.alipay.utils.http.HttpClient;
import com.payline.pmapi.bean.notification.request.NotificationRequest;
import com.payline.pmapi.bean.notification.response.NotificationResponse;
import com.payline.pmapi.bean.notification.response.impl.PaymentResponseByNotificationResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationServiceImplTest {
    @InjectMocks
    private NotificationServiceImpl service = new NotificationServiceImpl();

    @Mock
    private HttpClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void parse() {
        NotificationRequest request = MockUtils.aPaylineNotificationRequest(MockUtils.notificationBodyPaid);
        NotificationResponse response = service.parse(request);
        PaymentResponseByNotificationResponse paymentResponseByNotificationResponse = (PaymentResponseByNotificationResponse) response;
        Assertions.assertEquals(PaymentResponseFailure.class, paymentResponseByNotificationResponse.getPaymentResponse().getClass());
    }

    @Test
    void parseExceptionInvalidBody() {
        NotificationRequest request = MockUtils.aPaylineNotificationRequest(MockUtils.invalidNotificationBodyTemplate);
        NotificationResponse response = service.parse(request);
        PaymentResponseByNotificationResponse paymentResponseByNotificationResponse = (PaymentResponseByNotificationResponse) response;
        Assertions.assertEquals(PaymentResponseFailure.class, paymentResponseByNotificationResponse.getPaymentResponse().getClass());
    }
}
