package org.example.krevent.service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import lombok.RequiredArgsConstructor;
import org.example.krevent.mapper.TicketMapper;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.User;
import org.example.krevent.payload.request.TicketPurchaseRequest;
import org.example.krevent.repository.HallSeatRepository;
import org.example.krevent.repository.TicketRepository;
import org.example.krevent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT;
import static org.example.krevent.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final HallSeatRepository hallSeatRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final StripeService stripeService;
    private final TicketMapper ticketMapper;
    private final String price10 = "price_1PHpRFJBrDD3W9P8xU8VBApF";
    private final String price15 = "price_1PHpQGJBrDD3W9P8gXxFB30e";

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public Map<String, Object> purchaseTicket(TicketPurchaseRequest data) {
        User user = findById(userRepository, data.getUserId());
        List<HallSeat> hallSeat = data.getHallSeatIds().stream()
                .map(id -> findById(hallSeatRepository, id))
                .toList();

        var customer = stripeService.getOrCreateCustomer(user.getEmail(),
                user.getFirstName() + " " + user.getLastName());

        long numberOfSeatsPrice10 = 0;
        long numberOfSeatsPrice15 = 0;

        for (HallSeat seat : hallSeat) {
            if (seat.getPrice() == 10) {
                numberOfSeatsPrice10++;
            } else if (seat.getPrice() == 15) {
                numberOfSeatsPrice15++;
            }
        }

        List<LineItem> lineItems = new ArrayList<>();
        if (numberOfSeatsPrice10 > 0) {
            lineItems.add(LineItem.builder()
                    .setPrice(price10)
                    .setQuantity(numberOfSeatsPrice10)
                    .build());
        }
        if (numberOfSeatsPrice15 > 0) {
            lineItems.add(LineItem.builder()
                    .setPrice(price15)
                    .setQuantity(numberOfSeatsPrice15)
                    .build());
        }

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setCustomer(customer.getId())
                    .addAllLineItem(lineItems)
                    .setMode(PAYMENT)
                    .setSuccessUrl("http://localhost:3000/success")
                    .setCancelUrl("http://localhost:3000/cancel")
                    .build();
            Session session = Session.create(params);
            return Map.of("url", session.getUrl());
        } catch (Exception e) {
            throw new RuntimeException("Error while creating Stripe session", e);
        }

    }


}
