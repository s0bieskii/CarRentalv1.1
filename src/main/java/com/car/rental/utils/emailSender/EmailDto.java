package com.car.rental.utils.emailSender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class EmailDto {


    private String userName;
    private String to;
    @NotBlank(message = "Please enter your email")
    @Email(message = "Please enter your email")
    private String from;
    @Length(min = 10, message = "Message length min 10 characters")
    @Length(max = 1500, message = "Message is to long! max 1500 characters")
    @NotBlank(message = "Please enter message")
    private String text;
    private boolean isHtmlContent;
}
