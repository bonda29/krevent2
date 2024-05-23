# Krevent Platform Documentation

Krevent is a comprehensive platform designed to streamline the organization and management of events. It offers the event organizers to create and manage events, event halls, and seat bookings. For attendees, it provides the ability to browse events, select seats, and purchase tickets securely through Stripe integration.

## Application Overview

### Purpose

Krevent aims to simplify the event management process by offering a centralized platform where:

- **Event Organizers** can:
    - Create and manage events, including details like name, description, date, and images.
    - Design and manage event halls, specifying details such as name, description, capacity, and seat views.
    - Manage hall seats, including seat types and pricing.

- **Attendees** can:
    - Browse events and event halls.
    - Choose specific seats based on type and price.
    - Purchase tickets securely, with transactions handled via Stripe.

### Security

Security is a top priority, with JWT used for authentication to ensure that users' information is protected. The platform allows for secure login, registration, and token refresh processes to maintain session integrity.

## Controllers Overview

### `AuthenticationController`

Handles user authentication, including registration, login, and token refresh operations.

- **`/register`**: Registers a new user.
- **`/authenticate`**: Authenticates a user and provides a JWT for access.
- **`/refresh-token`**: Refreshes the authentication token.

### `EventController`

Manages event-related operations.

- **`/` (POST)**: Creates a new event.
- **`/{id}` (GET)**: Retrieves details of a specific event.
- **`/{id}/add-images` (PATCH)**: Adds images to an event.
- **`/{id}/remove-image` (PATCH)**: Removes an image from an event.
- **`/{id}` (DELETE)**: Deletes an event.

### `EventHallController`

Handles operations related to event halls.

- **`/` (POST)**: Creates a new event hall.
- **`/{id}` (GET)**: Retrieves details of a specific event hall.
- **`/` (GET)**: Lists all event halls.
- **`/{id}/hall-seats` (GET)**: Retrieves the seats of a specific event hall.
- **`/{id}` (DELETE)**: Deletes an event hall.

### `HallSeatController`

Manages hall seat operations.

- **`/` (POST)**: Creates a new hall seat.
- **`/{id}` (GET)**: Retrieves details of a specific hall seat.
- **`/{id}/update-type` (PATCH)**: Updates the type of a hall seat.
- **`/{id}/update-price` (PATCH)**: Updates the price of a hall seat.
- **`/{id}` (DELETE)**: Deletes a hall seat.

### `TicketController`

Handles ticket purchasing and retrieval.

- **`/purchase` (POST)**: Initiates the purchase of a ticket.
- **`/success` (GET)**: Retrieves tickets based on a session ID.

## Security and Transactions

Security is managed through JWT for authentication, ensuring secure access to the platform's features. Transactions are handled via Stripe - a trustworthy method for ticket purchases.