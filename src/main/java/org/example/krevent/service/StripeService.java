package org.example.krevent.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Customer getOrCreateCustomer(String email, String name) {
        try {
            CustomerListParams params = CustomerListParams.builder()
                    .setEmail(email)
                    .build();

            CustomerCollection customers = Customer.list(params);

            if (customers.getData().isEmpty()) {
                // No customer with the given email exists, create a new one
                CustomerCreateParams createParams = CustomerCreateParams.builder()
                        .setEmail(email)
                        .setName(name)
                        .build();

                return Customer.create(createParams);
            } else {
                // A customer with the given email exists, return the first one
                return customers.getData().get(0);
            }
        } catch (StripeException e) {
            log.error("Error while creating or retrieving Stripe customer for email: {}", email, e);
            throw new RuntimeException(e);
        }
    }

    public Product getProduct(String productId) {
        try {
            return Product.retrieve(productId);
        } catch (StripeException e) {
            log.error("Error while retrieving Stripe product with id: {}", productId, e);
            throw new RuntimeException(e);
        }
    }

    public Session createCheckoutSession(String customerId, List<LineItem> lineItems, String successUrl, String cancelUrl) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setCustomer(customerId)
                    .addAllLineItem(lineItems)
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(cancelUrl)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .build();

            return Session.create(params);
        } catch (StripeException e) {
            log.error("Error while creating Stripe checkout session for customer: {}", customerId, e);
            throw new RuntimeException(e);
        }
    }
}
