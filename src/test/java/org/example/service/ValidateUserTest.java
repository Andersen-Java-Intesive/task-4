package org.example.service;

import org.example.dto.UserDto;
import org.example.util.ValidateUserUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateUserTest {

    @Test
    public void FirstNameNull() {
        UserDto userDto = UserDto.builder()
                .firstName(null)
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("First Name field is blank", exception.getMessage().trim());
    }

    @Test
    public void FirstNameBlank() {
        UserDto userDto = UserDto.builder()
                .firstName("    ")
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Empty First Name field", exception.getMessage().trim());
    }

    @Test
    public void FirstNameEmpty() {
        UserDto userDto = UserDto.builder()
                .firstName("")
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Empty First Name field", exception.getMessage().trim());
    }

    @Test
    public void SecondNameNull() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName(null)
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Empty Second Name field", exception.getMessage().trim());
    }

    @Test
    public void SecondNameBlank() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName("   ")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Empty Second Name field", exception.getMessage().trim());
    }

    @Test
    public void SecondNameEmpty() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName("")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Empty Second Name field", exception.getMessage().trim());
    }

    @Test
    public void FirstNamePunctuation() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold'")
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("No punctuation allowed ", exception.getMessage().trim());
    }

    @Test
    public void SecondNamePunctuation() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName("Schwarzenegger'")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("No punctuation allowed ", exception.getMessage().trim());
    }

    @Test
    public void FirstNameNonLatin() {
        UserDto userDto = UserDto.builder()
                .firstName("Арнольд")
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("No non-latin characters allowed", exception.getMessage().trim());
    }

    @Test
    public void SecondNameNonLatin() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName("Шварценеггер")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("No non-latin characters allowed", exception.getMessage().trim());
    }

    @Test
    public void FirstNameTooLong() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold".repeat(10))
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("First Name field must be no more than 50 characters long", exception.getMessage().trim());
    }

    @Test
    public void SecondNameTooLong() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName("Schwarzenegger".repeat(5))
                .age(76)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Second Name field must be no more than 50 characters long", exception.getMessage().trim());
    }

    @Test
    public void AgeTooHigh() {
        UserDto userDto = UserDto.builder()
                .firstName("Immortal")
                .secondName("Man")
                .age(1000)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Age is improbable", exception.getMessage().trim());
    }

    @Test
    public void AgeTooLow() {
        UserDto userDto = UserDto.builder()
                .firstName("The")
                .secondName("Unborn")
                .age(-1)
                .build();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ValidateUserUtils.validate(userDto);
        });
        //assertEquals("Age is improbable", exception.getMessage().trim());
    }

    @Test
    public void ValidUser() {
        UserDto userDto = UserDto.builder()
                .firstName("Arnold")
                .secondName("Schwarzenegger")
                .age(76)
                .build();
        assertTrue(ValidateUserUtils.validate(userDto));
    }

}
