package com.fanxing.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fanxing.dto.AppointExecution;
import com.fanxing.dto.Result;
import com.fanxing.entity.Book;
import com.fanxing.enums.AppointStateEnum;
import com.fanxing.exception.NoNumberException;
import com.fanxing.exception.RepeatAppointException;
import com.fanxing.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BookService bookService;

	@GetMapping(value = "/list")
	private ResponseEntity<List<Book>> list() {
		List<Book> list = bookService.getList();
		return new ResponseEntity<List<Book>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping(value = "/{bookId}/detail")
	private ResponseEntity<Book> detail(@PathVariable("bookId") Long bookId, Model model) {
		if (bookId == null) {
			return null;
		}
		Book book = bookService.getById(bookId);
		if (book == null) {
			return null;
		}
		return new ResponseEntity<Book>(book,new HttpHeaders(),HttpStatus.OK);
	}

	// ajax json
	@RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId) {
		if (studentId == null || studentId.equals("")) {
			return new Result<>(false, "学号不能为空");
		}
		AppointExecution execution = null;
		try {
			execution = bookService.appoint(bookId, studentId);
		} catch (NoNumberException e1) {
			execution = new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
		} catch (RepeatAppointException e2) {
			execution = new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
		} catch (Exception e) {
			execution = new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
		}
		return new Result<AppointExecution>(true, execution);
	}

}
