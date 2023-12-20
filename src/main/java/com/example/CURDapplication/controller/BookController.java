package com.example.CURDapplication.controller;

import com.example.CURDapplication.model.Book;
import com.example.CURDapplication.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
        @Autowired
        private BookRepo bookrepo;

        @GetMapping("/getAllBooks")
        public ResponseEntity<List<Book>> getAllBooks() {
                try {
                        List<Book> bookList = new ArrayList<>();
                        bookrepo.findAll().forEach(bookList::add);
                        if (bookList.isEmpty()) {
                                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                        }
                        return new ResponseEntity<>(bookList,HttpStatus.OK);
                }
                catch(Exception er){
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }


        @GetMapping("/getBookById/{id}")
        public  ResponseEntity<Book> getBookById(@PathVariable Long id) {
                Optional<Book> bookData = bookrepo.findById(id);
                if (bookData.isPresent()) {
                        return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PostMapping("/addBook")
        public ResponseEntity<Book>  addBook(@RequestBody Book book){
                Book save = bookrepo.save(book);
                return new ResponseEntity<>(save,HttpStatus.OK);
        }
        @DeleteMapping("/deleteBookById/{id}")
        public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id){
         bookrepo.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);

        }


        @PostMapping("/updateBookById/{id}")
        public ResponseEntity<Book> updateBookById(@PathVariable Long id,@RequestBody Book newBookData){

        Optional<Book> oldBookData= bookrepo.findById(id);

        if(oldBookData.isPresent()){
               Book UpdateBookData= oldBookData.get();
                UpdateBookData.setTitle(newBookData.getTitle());
                UpdateBookData.setAuthor(newBookData.getAuthor());

             Book save =  bookrepo.save(UpdateBookData);
             return new ResponseEntity<>(save,HttpStatus.OK);
        }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

}
