package com.example.librarybackend.dto;

import com.example.librarybackend.entity.Checkout;
import lombok.Data;

@Data
public class CheckoutDTO {
    private String userEmail;
    private String checkoutDate;
    private String returnDate;
    private long bookId;

    public CheckoutDTO(Checkout checkout) {
        this.userEmail = checkout.getUserEmail();
        this.checkoutDate = checkout.getCheckoutDate();
        this.returnDate = checkout.getReturnDate();
        this.bookId = checkout.getBookId();
    }
}
