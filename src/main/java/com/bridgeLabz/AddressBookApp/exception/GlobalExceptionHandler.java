//package com.bridgeLabz.AddressBookApp.exception;
//
//import com.bridgeLabz.AddressBookApp.dto.ResponseDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // ✅ Handling validation errors (e.g., invalid name format)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.put(error.getField(), error.getDefaultMessage());
//        }
//        ResponseDTO responseDTO = new ResponseDTO("Validation Failed", errors);
//        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
//    }
//
//    // ✅ Handling ID Not Found errors
//    @ExceptionHandler(AddressBookException.class)
//    public ResponseEntity<ResponseDTO> handleAddressBookException(AddressBookException ex) {
//        ResponseDTO responseDTO = new ResponseDTO("Error: " + ex.getMessage(), null);
//        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
//    }
//}
