package org.example.krevent.service.ticket;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import lombok.RequiredArgsConstructor;
import org.example.krevent.exception.HallSeatBookedException;
import org.example.krevent.models.Guest;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.Transaction;
import org.example.krevent.payload.request.TicketPurchaseRequest;
import org.example.krevent.repository.GuestRepository;
import org.example.krevent.repository.HallSeatRepository;
import org.example.krevent.repository.TransactionRepository;
import org.example.krevent.service.StripeService;
import org.example.krevent.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT;
import static java.util.stream.Collectors.toSet;
import static org.example.krevent.models.enums.TransactionStatus.PENDING;
import static org.example.krevent.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class PurchaseTicketService {
    private final TransactionRepository transactionRepository;
    private final HallSeatRepository hallSeatRepository;
    private final GuestRepository guestRepository;
    private final StripeService stripeService;

    @Value("${stripe.price10}")
    private String price10;

    @Value("${stripe.price15}")
    private String price15;

    @Value("${application.url.base}")
    private String baseUrl;

    /*
        public Map<String, Object> purchaseTicket(TicketPurchaseRequest data) {
            User user = findById(userRepository, data.getUserId());
            Set<HallSeat> hallSeats = data.getHallSeatIds().stream()
                    .map(id -> findById(hallSeatRepository, id))
                    .collect(toSet());

            var customer = stripeService.getOrCreateCustomer(user.getEmail(),
                    user.getFirstName() + " " + user.getLastName());

            long number10 = 0, number15 = 0;
            for (HallSeat seat : hallSeats) {
                if (seat.isBooked()) {
                    throw new HallSeatBookedException("Seat is already booked");
                } else if (seat.getPrice() == 10) {
                    number10++;
                } else if (seat.getPrice() == 15) {
                    number15++;
                }
            }

            String sessionId = UUID.randomUUID().toString();
            Transaction transaction = Transaction.builder()
                    .user(user)
                    .hallSeats(hallSeats)
                    .sessionId(sessionId)
                    .status(PENDING)
                    .timeOfCreation(DateUtil.now())
                    .build();

            transactionRepository.save(transaction);

            String paymentUrl = getPaymentUrl(customer.getId(), number10, number15, sessionId);

            return Map.of("url", paymentUrl);
        }
    */
    public Map<String, Object> purchaseTicketForGuest(TicketPurchaseRequest data) {
        Guest guest = Guest.builder()
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .build();

        guestRepository.save(guest);

        Set<HallSeat> hallSeats = data.getHallSeatIds().stream()
                .map(id -> findById(hallSeatRepository, id))
                .collect(toSet());

        var customer = stripeService.getOrCreateCustomer(guest.getEmail(),
                guest.getFirstName() + " " + guest.getLastName());

        long number10 = 0, number15 = 0;
        for (HallSeat seat : hallSeats) {
            if (seat.isBooked()) {
                throw new HallSeatBookedException("Seat is already booked");
            } else if (seat.getPrice() == 10) {
                number10++;
            } else if (seat.getPrice() == 15) {
                number15++;
            }
        }

        String sessionId = UUID.randomUUID().toString();
        Transaction transaction = Transaction.builder()
                .guest(guest)
                .hallSeats(hallSeats)
                .sessionId(sessionId)
                .status(PENDING)
                .timeOfCreation(DateUtil.now())
                .build();

        transactionRepository.save(transaction);

        String paymentUrl = getPaymentUrl(customer.getId(), number10, number15, sessionId);

        return Map.of("url", paymentUrl);
    }

    private String getPaymentUrl(String customerId, long number10, long number15, String sessionId) {
        List<LineItem> lineItems = new ArrayList<>();
        if (number10 > 0) {
            lineItems.add(LineItem.builder()
                    .setPrice(price10)
                    .setQuantity(number10)
                    .build());
        }
        if (number15 > 0) {
            lineItems.add(LineItem.builder()
                    .setPrice(price15)
                    .setQuantity(number15)
                    .build());
        }

        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setCustomer(customerId)
                    .addAllLineItem(lineItems)
                    .setMode(PAYMENT)
                    .setSuccessUrl(baseUrl + "api/v1/tickets/success?sessionId=" + sessionId)
                    .setCancelUrl(baseUrl + "api/v1/tickets/cancel?sessionId=" + sessionId)
                    .build();
            Session session = Session.create(params);

            return session.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("Error while creating Stripe session", e);
        }
    }

}
