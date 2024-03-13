package com.example.test.CRUDWebAPI.controllers;

import com.example.test.CRUDWebAPI.dto.BookDTO;
import com.example.test.CRUDWebAPI.models.Book;
import com.example.test.CRUDWebAPI.services.BookService;
import com.example.test.CRUDWebAPI.exceptions.BookErrorResponse;
import com.example.test.CRUDWebAPI.exceptions.BookNotCreatedException;
import com.example.test.CRUDWebAPI.exceptions.BookNotFoundException;
import com.example.test.CRUDWebAPI.utils.BuilderError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Tag(name = "BookController", description = "Controller for working with an entity Book")
public class BookController {

    private final BookService bookService;
    private final BuilderError builderError;
    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, BuilderError builderError, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.builderError = builderError;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/findAll")
    @Operation(summary = "Getting a list of all books")
    public List<BookDTO> getAllBook() {
        return bookService.findAll().stream().map(this::convertToBookDTO).collect(Collectors.toList());
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Receiving a book by id")
    public BookDTO getBook( @Parameter(description = "unique id")@PathVariable("id") int id) {
        return convertToBookDTO(bookService.findOne(id));
    }

    @PostMapping("deleteById/{id}")
    @Operation(summary = "Deleting a book by id")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") int id) {
        bookService.deleteOne(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/findByIsbn/{isbn}")
    @Operation(summary = "Receiving a book by Isbn")
    public List<BookDTO> findByIsbn(@PathVariable("isbn") int isbn) {
        return bookService.findByIsbn(isbn).stream().map(this::convertToBookDTO).collect(Collectors.toList());
    }

    @PostMapping("/createBook")
    @Operation(summary = "Creating books and sending id books to a second server (service)")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid BookDTO book, BindingResult bindingResult) {
        builderError.handleBindingErrors(bindingResult);
        Book newBook = bookService.createBook(convertToBook(book));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8081/freeBook/createAccountingOfBooks", newBook.getId(), String.class);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("updateBook/{id}")
    @Operation(summary = "Updating a book by id")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid BookDTO book, BindingResult bindingResult) {
        bookService.findOne(id);
        builderError.handleBindingErrors(bindingResult);
        bookService.updateBook(id, convertToBook(book));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/takeBook/{id}")
    @Operation(summary = "Taking a book by id - Sending a POST-request and an object representing bookId, the acquisition time and return time to the second server (service)")
    public ResponseEntity<HttpStatus> takeBook(@PathVariable("id") int id) {
        Book book = bookService.findOne(id);
        RestTemplate restTemplate = new RestTemplate();
        LocalDateTime localDateTimeTake = LocalDateTime.now();
        LocalDateTime localDateTimeReturn = LocalDateTime.now().plusMonths(1);

        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("bookId", book.getId());
        requestData.put("takenBook", localDateTimeTake);
        requestData.put("returnBook", localDateTimeReturn);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        restTemplate.postForEntity("http://localhost:8081/freeBook/updateStatusBook", request, String.class);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/returnBook/{id}")
    @Operation(summary = "Returning a book by id - Sending a POST request and an object representing an bookId that will be free")
    public ResponseEntity<HttpStatus> returnBook(@PathVariable("id") int id) {
        Book book = bookService.findOne(id);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("bookId", book.getId());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        restTemplate.postForEntity("http://localhost:8081/freeBook/resetTimeBook", request, String.class);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handlerException(BookNotFoundException e) {
        BookErrorResponse response = new BookErrorResponse(
                "Book not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<BookErrorResponse> handlerException(BookNotCreatedException e) {
        BookErrorResponse response = new BookErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    private Book convertToBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }
}